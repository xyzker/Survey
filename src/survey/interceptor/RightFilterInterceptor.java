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
		if(action instanceof UserAction){			//PersonAction����֤
			return invocation.invoke();
		}
		if(user != null){							//�û��ѵ�½
			if(action instanceof UserAware){
				//ע��session�е�user
				((UserAware)action).setUser(user);
			}
			return invocation.invoke();
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("message", "���ȵ�¼��");
		return "login";*/
		
		String url = DataUtil.getUrlByAction(invocation);
		Map<String, Right> map = (Map<String, Right>)ActionContext.getContext().getApplication().get("all_rights_map");
		//ͨ��url��ѯRight����
		Right r = map.get(url);
		//rΪ������Դ
		if(r == null || r.isCommon()){
			//���map��û�д�Ȩ�ޣ�����������ݿ�
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
			//δ��½
			if(user == null){
				ServletActionContext.getRequest().setAttribute("message", "���ȵ�¼��");
				return "login";
			}
			//��½
			else{
				ActionSupport action = (ActionSupport)invocation.getAction();
				if(action instanceof UserAware){
					//ע��session�е�user
					((UserAware)action).setUser(user);
				}
				//��������Ա
				if(user.isSuperAdmin()){
					return invocation.invoke();
				}
				
				else{
					//��Ȩ��
					if(user.hasRight(r)){
						return invocation.invoke();
					}
					else{
						ServletActionContext.getRequest().setAttribute("message", "�Բ�����û�д�Ȩ�ޣ�");
						return "error_no_right";
					}
					
				}
			}
		}
	}

}
