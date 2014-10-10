package survey.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.model.Log;
import survey.service.ILogService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class LogAction extends ActionSupport {
	private static final long serialVersionUID = 3215585560673753918L;
	
	private List<Log> logs;
	//默认查询日志的月份数
	private int monthNum = 2;
	
	@Resource
	private ILogService logService;
	
	@Action(value="/log/findAllLogs", results={@Result(location="/WEB-INF/content/log/logList.jsp")})
	public String findAllLogs(){
		logs = logService.findAllEntities();
		return SUCCESS;
	}
	
	/*
	 * 查询最近的日志
	 */
	@Action(value="/log/findNearestLogs", results={@Result(location="/WEB-INF/content/log/logList.jsp")})
	public String findNearestLogs(){
		logs = logService.findNearestLogs(monthNum);		//2表示查询最近2个月
		return SUCCESS;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	public ILogService getLogService() {
		return logService;
	}

	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}

	public int getMonthNum() {
		return monthNum;
	}

}
