package survey.service;

import java.util.List;

import survey.model.Log;
import survey.service.IService;

public interface ILogService extends IService<Log> {
	/*
	 * ͨ������������־��
	 */
	public void createLogTable(String tableName);
	
	/*
	 * ��ѯ���ָ���·�������־
	 */
	public List<Log> findNearestLogs(int i);
	
}
