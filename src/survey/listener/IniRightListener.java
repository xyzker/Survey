package survey.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import survey.model.security.Right;
import survey.service.IRightService;

/*
 * ��ʼ��Ȩ�޼���������spring��ʼ����ɺ���������.
 */
@Component
@SuppressWarnings("unchecked")
public class IniRightListener implements ApplicationListener, ServletContextAware {
	
	@Resource
	private IRightService rightService;
	
	private ServletContext servletContext;

	public void onApplicationEvent(ApplicationEvent arg0) {
		//������ˢ���¼�
		if(arg0 instanceof ContextRefreshedEvent){
			List<Right> rights = rightService.findAllEntities();
			Map<String, Right> map = new HashMap<String, Right>();
			for(Right r : rights){
				map.put(r.getRightUrl(), r);
			}
			if(servletContext != null){
				servletContext.setAttribute("all_rights_map", map);
				System.out.println("��ʼ������Ȩ�޵�Application��!!!!");
			}
		}
	}

	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}

	public IRightService getRightService() {
		return rightService;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
