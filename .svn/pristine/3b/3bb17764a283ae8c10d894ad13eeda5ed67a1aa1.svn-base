package survey.service.impl;

import java.util.List;


import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Page;
import survey.model.Question;
import survey.model.Survey;
import survey.model.User;
import survey.service.ISurveyService;

@Component("surveyService")
@Transactional
public class SurveyServiceImpl extends ServiceImpl<Survey> implements ISurveyService{

	@SuppressWarnings("unchecked")
	public List<Survey> findMySurveys(User user) {
		return (List<Survey>)dao.createQuery("from Survey s where s.user = :user")
				.setParameter("user", user).list();
	}

	public Survey getSurveyDetail(int id) {
		Survey s = (Survey)dao.createQuery("from Survey s where s.id = :id").
						setParameter("id", id).uniqueResult();
		for(Page p : s.getPages()){		//强行初始化page和question属性
			for(Question q :p.getQuestions()){
				q.getId();
			}
		}
		return s;
	}

	public void toggleStatus(Survey s) {
		dao.createQuery("update Survey s set s.closed = :isClosed where s.id = :id")
						.setParameter("isClosed", !s.isClosed()).setParameter("id", s.getId())
						.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	public List<Survey> getSurveysWithPages(User u) {
		return (List<Survey>)dao.createQuery("select distinct s from Survey s join fetch s.pages where s.user = :user")
				.setParameter("user", u).list();
	}

	@SuppressWarnings("unchecked")
	public List<Survey> findAllAvailableSurveys() {
		return (List<Survey>)dao.createQuery("from Survey s where s.closed = false").list();
	}

}
