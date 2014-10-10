/*package survey.interceptor;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import survey.service.IRightService;
import survey.util.DataUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CatchUrlInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 5855432566671641833L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String url = DataUtil.getUrlByAction(invocation);//得到权限url
		
		WebApplicationContext ctx = WebApplicationContextUtils
	      .getWebApplicationContext(ServletActionContext.getServletContext());
		IRightService rs = (IRightService)ctx.getBean("rightService");
		rs.appendRightByURL(url);
		return invocation.invoke();
	}

}
*/