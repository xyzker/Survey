package survey.model.statistics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import survey.model.Question;

public class QuestionStatisticsModel implements Serializable{
	private static final long serialVersionUID = 1440801130622390535L;
	private Question question;
	private int count;
	private List<OptionStatisticsModel> osms = new ArrayList<OptionStatisticsModel>();
	
	public void setQuestion(Question question) {
		this.question = question;
	}
	public Question getQuestion() {
		return question;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setOsms(List<OptionStatisticsModel> osms) {
		this.osms = osms;
	}
	public List<OptionStatisticsModel> getOsms() {
		return osms;
	}

}
