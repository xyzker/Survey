package survey.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Answer;
import survey.model.Survey;
import survey.service.IAnswerService;


@Component("answerService")
@Transactional
public class AnswerServiceImpl extends ServiceImpl<Answer> implements IAnswerService {

	public void clearAnswers(Survey s) {
		dao.createQuery("delete from Answer a where a.surveyId = :surveyId")
						.setParameter("surveyId", s.getId()).executeUpdate();
	}

	public List<Answer> getAnswers(Integer sId) {
		return (List<Answer>)dao.list("from Answer a where a.surveyId = ? " +
				"order by a.uuid asc", sId);
	}

}
