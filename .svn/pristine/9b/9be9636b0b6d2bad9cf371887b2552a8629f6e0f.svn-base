package survey.interceptor;

import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import survey.model.User;
import survey.model.security.Right;
import survey.service.IRightService;
import survey.util.DataUtil;

public class RightFilterInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 7918143213426544935L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		/*User user = (User)ActionContext.getContext().getSession().get("user");
		ActionSupport action = (ActionSupport)invocation.getAction();
		if(action instanceof UserAction){			//PersonAction不验证
			return invocation.invoke();
		}
		if(user != null){							//用户已登陆
			if(action instanceof UserAware){
				//注入session中的user
				((UserAware)action).setUser(user);
			}
			return invocation.invoke();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("message", "请先登录！");
		return "login";*/
		
		String url = DataUtil.getUrlByAction(invocation);
		Map<String, Right> map = (Map<String, Right>)ActionContext.getContext().getApplication().get("all_rights_map");
		//通过url查询Right对象
		Right r = map.get(url);
		//r为公共资源
		if(r == null || r.isCommon()){
			//如果map中没有此权限，则添加入数据库
			if(r == null){
				WebApplicationContext ctx = WebApplicationContextUtils
			      .getWebApplicationContext(ServletActionContext.getServletContext());
				IRightService rs = (IRightService)ctx.getBean("rightService");
				rs.appendRightByURL(url);
			}
			return invocation.invoke();
		}
		else{
			User user = (User)ActionContext.getContext().getSession().get("user");
			//未登陆
			if(user == null){
				ServletActionContext.getRequest().setAttribute("message", "请先登录！");
				return "login";
			}
			//登陆
			else{
				ActionSupport action = (ActionSupport)invocation.getAction();
				if(action instanceof UserAware){
					//注入session中的user
					((UserAware)action).setUser(user);
				}
				//超级管理员
				if(user.isSuperAdmin()){
					return invocation.invoke();
				}
				
				else{
					//有权限
					if(user.hasRight(r)){
						return invocation.invoke();
					}
					else{
						ServletActionContext.getRequest().setAttribute("message", "对不起，您没有此权限！");
						return "error_no_right";
					}
					
				}
			}
		}
	}

}
