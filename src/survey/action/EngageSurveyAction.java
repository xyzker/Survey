package survey.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ParameterAware;

import survey.model.Answer;
import survey.model.Page;
import survey.model.Survey;
import survey.service.IQuestionService;
import survey.service.ISurveyIntegratedService;
import survey.service.ISurveyService;
import survey.util.StringUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 *参与调查Action 
 */
@ParentPackage("survey")
public class EngageSurveyAction extends ActionSupport implements ParameterAware {
	private static final long serialVersionUID = -8005172646775543329L;
	
	//所有参赛的map
	private static final String ALL_PARAMS_MAP = "all_params_map";
	
	private List<Survey> surveys = new ArrayList<Survey>();
	private Survey survey;
	private Page currentPage;
	
	@Resource
	private ISurveyService surveyService;
	@Resource
	private ISurveyIntegratedService surveyIntegratedService;
	@Resource
	private IQuestionService questionService;

	//接收所有参赛的map
	private Map<String, String[]> parasMap;
	
	@Action(value="/engageSurvey/engageSurveyList")
	public String findAllAvailableSurveys(){
		setSurveys(surveyService.findAllAvailableSurveys());
		return SUCCESS;
	}
	
	@Action(value="/engageSurvey/enterSurvey", results={@Result(location="/WEB-INF/content/" +
			"engageSurvey/doSurvey.jsp")})
	public String enterSurvey(){
		survey = surveyService.get(survey.getId());
		ActionContext.getContext().getSession().put("current_survey", survey);
		//将存放所有参数的大map -->session中(用户保存答案和回显).
		ActionContext.getContext().getSession().put(ALL_PARAMS_MAP, 
				new HashMap<Integer, HashMap<String, String[]>>());
		currentPage = surveyIntegratedService.getFirstPage(survey.getId());
		return SUCCESS;
	}
	
	@Action(value="/engageSurvey/doSurvey", results={@Result(name="engageSurveyList", 
			type="redirectAction", location="engageSurveyList")})
	public String doSurvey(){
		String submitName = getSubmitName();
		//上一步
		if(submitName.endsWith("pre")){
			mergeParamsIntoSession();
			currentPage = surveyIntegratedService.getPage(currentPage.getId());
			currentPage = surveyIntegratedService.getPrePageWithQuestions(currentPage);
			return SUCCESS;
		}
		//下一步
		else if(submitName.endsWith("next")){
			mergeParamsIntoSession();
			currentPage = surveyIntegratedService.getPage(currentPage.getId());
			currentPage = surveyIntegratedService.getNextPageWithQuestions(currentPage);
			return SUCCESS;
		}
		//完成
		else if(submitName.endsWith("done")){
			mergeParamsIntoSession();
			//绑定token到当前线程,暂时还是没有用数据路由功能（问题多且没必要）
			//SurveyToken.bindToken(getCurrentSurvey());
			//答案入库
			surveyIntegratedService.saveAnswers(processAnswers());
			clearSessionData();
			return "engageSurveyList";
		}
		//取消
		else if(submitName.endsWith("exit")){
			clearSessionData();
			return "engageSurveyList";
		}
		return INPUT;
	}
	
	/*
	 * 处理答案
	 */
	private List<Answer> processAnswers() {
		//矩阵式单选按钮的map
		Map<Integer, String> matrixRadioMap = new HashMap<Integer,String>();
		//所有答案的集合
		List<Answer> answers = new ArrayList<Answer>();
		Answer a = null;
		String key = null;
		String[] value = null;
		Map<Integer, Map<String, String[]>> allParamsMap =  getAllParamsMap();
		for(Map<String, String[]> map : allParamsMap.values()){
			for(Entry<String, String[]> entry : map.entrySet()){
				key = entry.getKey();
				value = entry.getValue();
				//处理q开头的参数
				if(key.startsWith("q")){
					//常规参数
					if(!key.contains("other") && !key.contains("_")){
						a = new Answer();
						a.setAnswersId(StringUtil.arr2Str(value));
						a.setQuestion(questionService.get(getQid(key)));
						a.setSurveyId(getCurrentSurvey().getId());
						a.setOtherAnswer(StringUtil.arr2Str(map.get(key + "other")));
						answers.add(a);
					}
					//矩阵式单选按钮
					else if(key.contains("_")){
						int radioQid = getMatrixRadioQid(key);
						String oldValue = matrixRadioMap.get(radioQid);
						if(oldValue == null){
							matrixRadioMap.put(radioQid, StringUtil.arr2Str(value));
						}
						else{
							matrixRadioMap.put(radioQid, oldValue + "," + StringUtil.arr2Str(value));
						}
					}
				}
				
			}
		}
		//单独处理矩阵式单选按钮
		processMatrixRadioMap(matrixRadioMap, answers);
		return answers;
	}
	
	/*
	 * 单独处理矩阵式单选按钮
	 */
	private void processMatrixRadioMap(Map<Integer, String> matrixRadioMap,
			List<Answer> answers) {
		Answer a = null;
		Integer key = null;
		String value = null;
		for(Entry<Integer, String> entry : matrixRadioMap.entrySet()){
			key = entry.getKey();
			value = entry.getValue();
			a = new Answer();
			a.setAnswersId(value);
			a.setQuestion(questionService.get(key));
			a.setSurveyId(getCurrentSurvey().getId());
			answers.add(a);
		}
	}

	/**
	 * 获取矩阵式问题id:q12_0 ---> 12
	 */
	private int getMatrixRadioQid(String key) {
		return Integer.parseInt(key.substring(1, key.lastIndexOf("_")));
	}

	/*
	 * 提取问题id
	 */
	private int getQid(String key) {
		return Integer.parseInt(key.substring(1));
	}

	private void clearSessionData() {
		ActionContext.getContext().getSession().remove("current_survey");
		ActionContext.getContext().getSession().remove(ALL_PARAMS_MAP);
	}

	/**
	 * 合并参数到session中
	 */
	private void mergeParamsIntoSession() {
		Map<Integer, Map<String, String[]>> allParamsMap =  getAllParamsMap();
		allParamsMap.put(currentPage.getId(), parasMap);
	}

	/**
	 * 获得提交按钮的名称
	 */
	private String getSubmitName() {
		for(String key : parasMap.keySet()){
			if(key.startsWith("submit_")){
				return key;
			}
		}
		return null;
	}
	
	/**
	 * 设置标记,用于答案回显,主要用于radio|checkbox|select的选中标记
	 */
	public String setTag(String name, String value, String selTag){
		Map <String, String[]> map = getAllParamsMap().get(currentPage.getId());
		String[] values = map.get(name);
		if(StringUtil.contains(values,value)){
			return selTag;
		}
		return "";
	}
	
	/**
	 * 设置标记,用于答案回显,设置文本框回显
	 */
	public String setText(String name){
		Map<String, String[]> map = getAllParamsMap().get(currentPage.getId());
		String[] values = map.get(name);
		return "value='"+values[0]+"'" ;
	}


	public void setSurveyService(ISurveyService surveyService) {
		this.surveyService = surveyService;
	}

	public ISurveyService getSurveyService() {
		return surveyService;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurveyIntegratedService(ISurveyIntegratedService surveyIntegratedService) {
		this.surveyIntegratedService = surveyIntegratedService;
	}

	public ISurveyIntegratedService getSurveyIntegratedService() {
		return surveyIntegratedService;
	}

	public void setCurrentPage(Page currentPage) {
		this.currentPage = currentPage;
	}

	public Page getCurrentPage() {
		return currentPage;
	}

	public void setParameters(Map<String, String[]> parameters) {
		this.parasMap = parameters;
	}
	
	/**
	 * 获取session存放所有参数的map
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<String, String[]>> getAllParamsMap() {
		return (Map<Integer, Map<String, String[]>>)ActionContext.getContext()
						.getSession().get(ALL_PARAMS_MAP);
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}
	
	public Survey getCurrentSurvey(){
		return (Survey)ActionContext.getContext().getSession().get("current_survey");
	}

}
