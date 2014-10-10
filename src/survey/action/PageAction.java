package survey.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.interceptor.UserAware;
import survey.model.Page;
import survey.model.Survey;
import survey.model.User;
import survey.service.IPageService;
import survey.service.ISurveyIntegratedService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class PageAction extends ActionSupport implements UserAware{

	private static final long serialVersionUID = 6656604123490789325L;
	
	@Resource
	private ISurveyIntegratedService surveyIntegratedService;
	
	@Resource
	private IPageService pageService;
	
	private Survey survey;
	private Page page;
	private Page targetPage;
	private User user;
	private int pos;
	
	private List<Survey> mySurveys;

	@Action(value="/page/addPage", results={@Result(location="/WEB-INF/content/" +
			"page/editPage.jsp", params={"survey.id","${survey.id}"})})
	public String addPage(){
		return SUCCESS;
	}
	
	@Action(value="/page/saveOrUpdatePage", results={@Result(type="redirectAction", 
			params={"actionName","designSurvey","namespace","/survey","survey.id",
			"${survey.id}"})})
	public String saveOrUpdatePage(){
		survey = surveyIntegratedService.getSurvey(survey.getId());
		page.setSurvey(survey);
		
		surveyIntegratedService.saveOrUpdatePage(page);
		return SUCCESS;
	}
	
	@Action(value="/page/editPage")
	public String editPage(){
		page = surveyIntegratedService.getPage(page.getId());
		return SUCCESS;
	}
	
	@Action(value="/page/deletePage", results={@Result(type="redirectAction", 
			params={"actionName","designSurvey","namespace","/survey","survey.id",
			"${survey.id}"})})
	public String deletePage(){
		page = pageService.get(page.getId());
		pageService.delete(page);
		return SUCCESS;
	}
	
	@Action(value="/page/moveOrCopyPage-input")
	public String moveOrCopyPageInput(){
		mySurveys = surveyIntegratedService.getSurveysWithPages(getUser());
		return SUCCESS;
	}
	
	@Action(value="/page/moveOrCopyPage", results={@Result(type="redirectAction", 
			params={"actionName","designSurvey","namespace","/survey","survey.id",
			"${survey.id}"})})
	public String moveOrCopyPage(){
		page = pageService.get(page.getId());
		targetPage = pageService.get(targetPage.getId());
		surveyIntegratedService.moveOrCopyPage(page, targetPage, pos);
		return SUCCESS;
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

	public void setPage(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	public void setPageService(IPageService pageService) {
		this.pageService = pageService;
	}

	public IPageService getPageService() {
		return pageService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setTargetPage(Page targetPage) {
		this.targetPage = targetPage;
	}

	public Page getTargetPage() {
		return targetPage;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public int getPos() {
		return pos;
	}
}
