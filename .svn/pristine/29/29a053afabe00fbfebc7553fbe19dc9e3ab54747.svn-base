package survey.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Question;
import survey.model.statistics.OptionStatisticsModel;
import survey.model.statistics.QuestionStatisticsModel;
import survey.service.IAnswerService;
import survey.service.IStatisticsService;


@Component("statisticsService")
@Transactional
public class StatisticsServiceImpl implements IStatisticsService {
	
	@Resource
	private IAnswerService answerService;

	public QuestionStatisticsModel statistics(Question q) {
		QuestionStatisticsModel qsm = new QuestionStatisticsModel();
		qsm.setQuestion(q);
		
		//统计回答问题的总人数
		int count = answerService.getTotalCount("from Answer a where a.question = ?", q);
		qsm.setCount(count);
		
		String hql = "from Answer a where a.question = ? and concat(',', a.answersId, ',') like ?";
		int oCount = 0;
		
		switch(q.getQuestionType()){
			//非矩阵式问题
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				String[] arr = q.getOptionArr();
				OptionStatisticsModel osm = null;
				for(int i=0; i<arr.length; i++){
					osm = new OptionStatisticsModel();
					osm.setOptionIndex(i);
					osm.setOptionLabel(arr[i]);
					oCount = answerService.getTotalCount(hql, q, "%," + i + ",%");
					osm.setCount(oCount);
					qsm.getOsms().add(osm);
				}
				if(q.isOther()){
					osm = new OptionStatisticsModel();
					osm.setOptionIndex(-1);
					osm.setOptionLabel("其他");
					oCount = answerService.getTotalCount(hql, q, "%other%");
					osm.setCount(oCount);
					qsm.getOsms().add(osm);
				}
				break;
			//矩阵式问题
			case 6:
			case 7:
			case 8:
				String[] rows = q.getMatrixRowTitleArr();
				String[] cols = q.getMatrixColTitleArr();
				String[] opts = q.getMatrixSelectOptionArr();
				
				for(int i=0; i<rows.length; i++){
					for(int j=0; j<cols.length; j++){
						//matrix radio | checkbox
						if(q.getQuestionType() != 8){
							osm = new OptionStatisticsModel();
							osm.setMatrixRowIndex(i);
							osm.setMatrixRowLabel(rows[i]);
							osm.setMatrixColIndex(j);
							osm.setMatrixColLabel(cols[j]);
							oCount = answerService.getTotalCount(hql, q, "%," + i + "_" + j + ",%");
							osm.setCount(oCount);
							qsm.getOsms().add(osm);
						}
						//matrix select
						else {
							for(int k=0; k<opts.length; k++){
								osm = new OptionStatisticsModel();
								osm.setMatrixRowIndex(i);
								osm.setMatrixRowLabel(rows[i]);
								osm.setMatrixColIndex(j);
								osm.setMatrixColLabel(cols[j]);
								osm.setMatrixSelectIndex(k);
								osm.setMatrixSelectLabel(opts[k]);
								oCount = answerService.getTotalCount(hql, q, "%," + i + "_" + j + "_" + k + ",%");
								osm.setCount(oCount);
								qsm.getOsms().add(osm);
							}
						}
					}
				}
				break;
		}
		
		return qsm;
	}

	public void setAnswerService(IAnswerService answerService) {
		this.answerService = answerService;
	}

	public IAnswerService getAnswerService() {
		return answerService;
	}

}
