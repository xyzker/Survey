package survey.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Question;
import survey.model.statistics.QuestionStatisticsModel;
import survey.service.IQuestionService;
import survey.service.IStatisticsService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Transactional
public class ModelTest {
	
	@Resource
	private IStatisticsService statisticsService;
	
	@Resource
	private IQuestionService questionService;
	
	@Test
	public void testModel(){
		Question q = questionService.get(42);
		QuestionStatisticsModel qsm = statisticsService.statistics(q);
		System.out.println(qsm);
	}
	
}
