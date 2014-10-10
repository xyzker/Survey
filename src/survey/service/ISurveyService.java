package survey.service;


import java.util.List;

import survey.model.Survey;
import survey.model.User;
import survey.service.IService;

public interface ISurveyService extends IService<Survey> {
	public List<Survey> findMySurveys(User u);
	public Survey getSurveyDetail(int id);
	public void toggleStatus(Survey s);
	public List<Survey> getSurveysWithPages(User u);		//得到一个用户所有的调查以及页面
	public List<Survey> findAllAvailableSurveys();
}
