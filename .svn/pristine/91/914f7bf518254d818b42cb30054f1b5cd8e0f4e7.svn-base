package survey.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Answer;
import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;
import survey.model.User;
import survey.service.IAnswerService;
import survey.service.IPageService;
import survey.service.IQuestionService;
import survey.service.ISurveyIntegratedService;
import survey.service.ISurveyService;

@Component("surveyIntegratedService")
@Transactional
public class SurveyIntegratedServiceImpl implements ISurveyIntegratedService{
	
	@Resource
	private ISurveyService surveyService;
	@Resource
	private IPageService pageService;
	@Resource
	private IQuestionService questionService;
	@Resource
	private IAnswerService answerService;
	
	public Survey newSurvey(User u) {
		Survey s = new Survey();
		Page p = new Page();
		s.getPages().add(p);
		s.setUser(u);
		s.setCreateTime(new Date());
		p.setSurvey(s);
		
		surveyService.create(s);
		pageService.create(p);
		return s;
	}
	
	public void clearAnswers(Survey s) {
		answerService.clearAnswers(s);
	}
	
	public Page getPage(int id) {
		return pageService.get(id);
	}

	public void saveOrUpdatePage(Page p) {
		pageService.saveOrUpdate(p);
	}
	
	public void saveOrUpdateQuestion(Question q) {
		questionService.saveOrUpdate(q);
	}

	public void setSurveyService(ISurveyService surveyService) {
		this.surveyService = surveyService;
	}

	public ISurveyService getSurveyService() {
		return surveyService;
	}

	public void setPageService(IPageService pageService) {
		this.pageService = pageService;
	}

	public IPageService getPageService() {
		return pageService;
	}

	public Survey getSurvey(int id) {
		return surveyService.get(id);
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setAnswerService(IAnswerService answerService) {
		this.answerService = answerService;
	}

	public IAnswerService getAnswerService() {
		return answerService;
	}

	public List<Survey> getSurveysWithPages(User u) {
		return surveyService.getSurveysWithPages(u);
	}

	public void moveOrCopyPage(Page srcPage, Page tagPage, int pos) {
		//移动
		if(srcPage.getSurvey().getId() == tagPage.getSurvey().getId()){
			pageService.setOrderno(srcPage, tagPage, pos);
		}
		//复制
		else{
			Page tmpPage = 	pageService.getPageWithQuestions(srcPage.getId());	//初始化一个临时Page
			pageService.evict(tmpPage);	      //清除session中的缓存
			
			tmpPage.setId(null);
			for(Question q: tmpPage.getQuestions()){
				q.setId(null);
				q.setAnswers(null);
			}
			tmpPage.setSurvey(tagPage.getSurvey());
			
			//Page copyPage = (Page) DataUtil.deeplyCopy(tmpPage);	//深度复制
			
			pageService.create(tmpPage);
			
			pageService.setOrderno(tmpPage, tagPage, pos);
		}
	}

	public Page getFirstPage(int surveyId) {
		return pageService.getFirstPage(surveyId);
	}

	public Page getNextPageWithQuestions(Page p) {
		Page page = pageService.getNextPage(p);
		for(Question q : page.getQuestions()){
			q.getId();
		}
		return page;
	}

	public Page getPrePageWithQuestions(Page p) {
		Page page = pageService.getPrePage(p);
		for(Question q : page.getQuestions()){
			q.getId();
		}
		return page;
	}
	
	public void saveAnswers(List<Answer> answers) {
		Date date = new Date();
		String uuid = UUID.randomUUID().toString();
		for(Answer a : answers){
			a.setAnswerTime(date);
			a.setUuid(uuid);
			answerService.saveOrUpdate(a);
		}
	}

	public List<Question> getQuestions(Survey s) {
		List<Question> questions = new ArrayList<Question>();
		for(Page p : s.getPages()){
			questions.addAll(p.getQuestions());
		}
		return questions;
	}

	public List<Answer> getAnswers(Integer sId) {
		return answerService.getAnswers(sId);
	}

}
