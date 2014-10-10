package survey.service;

import java.util.List;

import survey.model.Log;
import survey.service.IService;

public interface ILogService extends IService<Log> {
	/*
	 * 通过表明创建日志表
	 */
	public void createLogTable(String tableName);
	
	/*
	 * 查询最近指定月份数的日志
	 */
	public List<Log> findNearestLogs(int i);
	
}
