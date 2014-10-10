package survey.scheduler;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import survey.service.ILogService;
import survey.util.LogUtil;

/*
 * 创建日志表的石英(quartz)调度
 */
@Component
public class CreateLogTablesTask {
	@Resource
	private ILogService logService;
	
	/*
	 * 生成日志表
	 */
	@Scheduled(cron="0 0 0 15 * ?")		//每月中旬创建表
	//@Scheduled(cron="0/5 * * * * ? ")		//测试
	public void CreateLogTable(){
		System.out.println("建表中...");
		String tableName1 = LogUtil.generateLogTableName(1);	//下一月
		String tableName2 = LogUtil.generateLogTableName(2);	//下两月
		String tableName3 = LogUtil.generateLogTableName(3);	//下三月
		logService.createLogTable(tableName1);
		logService.createLogTable(tableName2);
		logService.createLogTable(tableName3);
	}
	
	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	public ILogService getLogService() {
		return logService;
	}
}
