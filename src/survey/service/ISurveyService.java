package survey.service;


import java.util.List;

import survey.model.Survey;
import survey.model.User;
import survey.service.IService;

public interface ISurveyService extends IService<Survey> {
	public List<Survey> findMySurveys(User u);
	public Survey getSurveyDetail(int id);
	public void toggleStatus(Survey s);
	public List<Survey> getSurveysWithPages(User u);		//�õ�һ���û����еĵ����Լ�ҳ��
	public List<Survey> findAllAvailableSurveys();
}
