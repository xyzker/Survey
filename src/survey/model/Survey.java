package survey.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Formula;

/**
 * 调查类
 */
@Entity
public class Survey extends BaseEntity {
	@Id
	@GeneratedValue
	private Integer id;
	private String title = "未命名";
	private String preText = "上一步";
	private String nextText = "下一步";
	private String exitText = "退出";
	private String doneText = "完成";
	private boolean closed;		//  打开/关闭状态(默认打开)
	private String logoPhotoPath;  //logo存放路径
	//sql语句，后面的参数id才是对象的东西，此列为虚拟列
	@Formula("(select min(p.orderno) from page p where p.survey_id = id)")
	private float minOrderno;
	@Formula("(select max(p.orderno) from page p where p.survey_id = id)")
	private float maxOrderno;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createTime;
	
	//建立从Survey到User之间多对一关联关系
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user ;
	
	//建立从Survey到Page之间一对多关联关系
	@OneToMany(mappedBy="survey", cascade={CascadeType.ALL})
	private List<Page> pages = new ArrayList<Page>();

	public List<Page> getPages() {
		return pages;
	}

	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPreText() {
		return preText;
	}

	public void setPreText(String preText) {
		this.preText = preText;
	}

	public String getNextText() {
		return nextText;
	}

	public void setNextText(String nextText) {
		this.nextText = nextText;
	}

	public String getExitText() {
		return exitText;
	}

	public void setExitText(String exitText) {
		this.exitText = exitText;
	}

	public String getDoneText() {
		return doneText;
	}

	public void setDoneText(String doneText) {
		this.doneText = doneText;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setLogoPhotoPath(String logoPhotoPath) {
		this.logoPhotoPath = logoPhotoPath;
	}

	public String getLogoPhotoPath() {
		return logoPhotoPath;
	}

	public void setMinOrderno(float minOrderno) {
		this.minOrderno = minOrderno;
	}

	public float getMinOrderno() {
		return minOrderno;
	}

	public void setMaxOrderno(float maxOrderno) {
		this.maxOrderno = maxOrderno;
	}

	public float getMaxOrderno() {
		return maxOrderno;
	}

}
