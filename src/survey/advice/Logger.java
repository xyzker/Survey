package survey.advice;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionContext;

import survey.model.Log;
import survey.model.User;
import survey.service.ILogService;
import survey.util.StringUtil;

/*
 * 日志记录类
 */
@Aspect
@Component
public class Logger {
	@Resource
	private ILogService logService;
	
	@Pointcut("(execution(public * survey.service.impl.*.clear*(..)) || " +
			"execution(public * survey.service.impl.*.save*(..)) || " + 
			"execution(public * survey.service.impl.*.append*(..)) || " + 
			//"execution(public * survey.service.impl.*.batchUpdate*(..)) || " + 
			"execution(public * survey.service.impl.*.create(..)) || " + 
			"execution(public * survey.service.impl.*.delete*(..)) || " + 
			"execution(public * survey.service.impl.*.update*(..)) || " +
			"execution(public * survey.service.impl.*.toggle*(..))) && " + 
			"!bean(logService)")
	public void recordLog(){}
	
	@Around("recordLog()")
	public void record(ProceedingJoinPoint pjp){
		Log log = new Log();
		//设置操作人
		User user = (User)ActionContext.getContext().getSession().get("user");
		if(user != null){
			log.setOperator(user.getId() + ":" + user.getNickName());
		}
		//操作名称
		String mname = pjp.getSignature().getName();
		log.setOperName(mname);
		//操作参数
		String params = StringUtil.arr2Str(pjp.getArgs());
		//防止字符串过长
		if(params.length() > 100){
			params = params.substring(0, 100);
		}
		log.setOperParams(params);
		//调用目标对象的方法
		try {
			Object ret = pjp.proceed();
			//设置操作结果
			log.setOperResult("success");
			//设置结果消息
			if(ret != null){
				log.setResultMsg(ret.toString());
			}
		} catch (Throwable e) {
			log.setOperResult("failure");
			log.setResultMsg(e.getMessage());
		}finally{
			logService.saveOrUpdate(log);
		}
	}

	public void setLogService(ILogService logService) {
		this.logService = logService;
	}

	public ILogService getLogService() {
		return logService;
	}
}
