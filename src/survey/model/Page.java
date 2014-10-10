package survey.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * 页面类
 */
@Entity
public class Page extends BaseEntity {
	@Id
	@GeneratedValue
	private Integer id;
	private String title = "未命名";
	private String description;
	private float orderno;  //页序

	//简历从Page到Survey之间多对一关联关系
	@ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="survey_id", nullable=false)
	private Survey survey;

	//简历从Page到Question之间一对多关联关系
	@OneToMany(mappedBy="page", cascade={CascadeType.ALL})
	private List<Question> questions = new ArrayList<Question>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		if(id != null){
			this.orderno = id;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public void setOrderno(float orderno) {
		this.orderno = orderno;
	}

	public float getOrderno() {
		return orderno;
	}

}
