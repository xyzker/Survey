package survey.service;

import java.util.List;

import survey.model.Answer;
import survey.model.Survey;

public interface IAnswerService extends IService<Answer>{
	public void clearAnswers(Survey s);
	public List<Answer> getAnswers(Integer sId);		//��ѯָ����������д�
}
