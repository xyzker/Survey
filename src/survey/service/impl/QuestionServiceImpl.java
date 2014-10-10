package survey.service.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Question;
import survey.service.IQuestionService;

@Component("questionService")
@Transactional
public class QuestionServiceImpl extends ServiceImpl<Question> implements IQuestionService{

}
