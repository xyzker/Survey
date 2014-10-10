package survey.action;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.model.Page;
import survey.model.Question;
import survey.service.IQuestionService;
import survey.service.ISurveyIntegratedService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class QuestionAction extends ActionSupport{

	private static final long serialVersionUID = 2969589822888935445L;
	
	private Page page;
	private Question question;
	
	@Resource
	private ISurveyIntegratedService surveyIntegratedService;
	
	@Resource
	private IQuestionService questionService;
	
	@Action(value="/question/selectQuestionType")
	public String selectQuestionType(){
		return SUCCESS;
	}
	
	public String designQuestion(){
		return Integer.toString(question.getQuestionType());
	}
	
	public String editQuestion(){
		question = questionService.get(question.getId());
		return Integer.toString(question.getQuestionType());
	}
	
	@Action(value="/question/saveOrUpdateQuestion", results={@Result(type="redirectAction", 
			params={"actionName","designSurvey","namespace","/survey","survey.id",
	"${page.survey.id}"})})
	public String saveOrUpdateQuestion(){
		if(page.getId() != null && page.getId().toString() != ""){
			page = surveyIntegratedService.getPage(page.getId());
			question.setPage(page);
		}
		else{
			page = surveyIntegratedService.getPage(question.getPage().getId());
		}
		
		surveyIntegratedService.saveOrUpdateQuestion(question);
		return SUCCESS;
	}
	
	@Action(value="/question/deleteQuestion", results={@Result(type="redirectAction", 
			params={"actionName","designSurvey","namespace","/survey","survey.id",
	"${question.page.survey.id}"})})
	public String deleteQuestion(){
		question = questionService.get(question.getId());
		questionService.delete(question);
		return SUCCESS;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setSurveyIntegratedService(ISurveyIntegratedService surveyIntegratedService) {
		this.surveyIntegratedService = surveyIntegratedService;
	}

	public ISurveyIntegratedService getSurveyIntegratedService() {
		return surveyIntegratedService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}

}
