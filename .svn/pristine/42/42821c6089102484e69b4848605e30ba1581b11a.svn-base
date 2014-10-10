package survey.service;

import java.util.List;

import survey.model.Answer;
import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;
import survey.model.User;

public interface ISurveyIntegratedService {
	public Survey newSurvey(User u);
	public Survey getSurvey(int id);
	public Page getPage(int id);
	public void saveOrUpdatePage(Page p);
	public void saveOrUpdateQuestion(Question q);
	public void clearAnswers(Survey s);			//清除调查
	public List<Survey> getSurveysWithPages(User u);		//得到一个用户所有的调查以及页面
	public void moveOrCopyPage(Page srcPage, Page tagPage, int pos);
	public Page getFirstPage(int surveyId);				//得到调查的首页
	public Page getPrePageWithQuestions(Page p);
	public Page getNextPageWithQuestions(Page p);
	public void saveAnswers(List<Answer> answers);		//批量保存答案
	public List<Question> getQuestions(Survey s);	//查询指定调查的所有问题
	public List<Answer> getAnswers(Integer sId);		//查询指定调查的所有答案
}
