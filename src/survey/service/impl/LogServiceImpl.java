package survey.service.impl;

import java.util.List;

import org.hibernate.id.UUIDHexGenerator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Log;
import survey.service.ILogService;
import survey.util.LogUtil;

@Component("logService")
@Transactional
public class LogServiceImpl extends ServiceImpl<Log> implements ILogService{

	public void createLogTable(String tableName) {
		String sql = "create table if not exists " + tableName + " like log";
		this.executeSQL(sql);
	}

	/*
	 * ��д�÷�������̬������־��¼����̬��
	 */
	public void saveOrUpdate(Log log) {
		String sql = "insert into " + LogUtil.generateLogTableName(0) + "(id, operName,operParams," +
				"operResult,operator,resultMsg,operTime) values(?,?,?,?,?,?,?)";
		//ÿ�����ֶ�����UUID
		UUIDHexGenerator uuid = new UUIDHexGenerator();
		String id = (String)uuid.generate(null, null);		
		this.executeSQL(sql, id, log.getOperName(), log.getOperParams(), log.getOperResult(), log.getOperator(),
				log.getResultMsg(),log.getOperTime());
	}

	@SuppressWarnings("unchecked")
	public List<Log> findNearestLogs(int i) {
		String tableName = LogUtil.generateLogTableName(0);
		//��ѯ���������־������
		String sql = "select table_name from information_schema.tables where table_schema = " +
				"'survey' and table_name like 'logs_%' and table_name <= '" + tableName + "' " +
						"order by table_name desc limit 0, " + i;
		List<String> list = (List<String>)this.executeSQLQuery(null, sql);
		//��ѯ����������ڵ���־
		String logSql = "";
		for(String s : list){
			logSql += "select * from " + s + " union ";
			logSql = logSql.substring(0, logSql.lastIndexOf("union"));
		}
		return this.executeSQLQuery(Log.class, logSql);		//ָ����װ��Logʵ����
	}
	
}
