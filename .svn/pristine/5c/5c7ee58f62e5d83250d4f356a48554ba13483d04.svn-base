package survey.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Log {
	@Id
	@GenericGenerator(name="hibernate-uuid", strategy="uuid")
	@GeneratedValue(generator="hibernate-uuid")
	private String id;
	private String operator;// 操作人
	private String operName;// 操作名称,方法名
	private String operParams;// 操作参数
	private String operResult;// 操作结果,成功,失败
	private String resultMsg;// 结果消息
	//@Temporal(value=TemporalType.TIMESTAMP) 默认就是TIMESTAMP
	private Date operTime = new Date(); // 操作时间

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperParams() {
		return operParams;
	}

	public void setOperParams(String operParams) {
		this.operParams = operParams;
	}

	public String getOperResult() {
		return operResult;
	}

	public void setOperResult(String operResult) {
		this.operResult = operResult;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	
}
