package survey.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.model.User;

import survey.service.IUserService;
import survey.util.DataUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class UserAction extends ActionSupport{

	private static final long serialVersionUID = 2006835019411739973L;

	//要输出的对象
	 private Map<String, Object> jsonMap = new HashMap<String, Object>();
	 
	 private User user;
	 
	@Resource
	private IUserService userService;
	 
	@Action("/user/register-input")
	public String registerInput() {
		return SUCCESS;
	}

	@Action(value="validateEmail", results={@Result(type="json",params={"contentType",
			"text/html","noCache","true","root","jsonMap"})})
	public String validateEmail() throws Exception {
		if(userService.existsEmail(user.getEmail())){
			jsonMap.put("exists", "true");
			jsonMap.put("msg", "该邮箱已被注册！");
		}
		else {
			jsonMap.put("exists", "false");
			jsonMap.put("msg", "该邮箱可以注册！");
		}
		return SUCCESS;
	}
	
	@Action(value="/user/register", results={@Result(name="success", type="redirectAction", 
			location="index")})
	public String register(){
		if(userService.existsEmail(user.getEmail())){
			addFieldError("user.email", "该邮箱已被注册！");
			return INPUT;
		}
		//密码加密
		user.setPassword(DataUtil.MD5(user.getPassword()));
		user.setRegDate(new Date());
		userService.create(user);
		//注册时默认配上“普通用户”(id为5)权限
		userService.updateAuthorize(user.getId(), new Integer[]{5});
		return SUCCESS;
	}
	
	@Action(value="/user/login", results={@Result(name="input", location="/index.jsp"), 
			@Result(type="redirectAction", name="success", location="index", params=
			{"namespace","/"})})
	public String login(){
		user.setPassword(DataUtil.MD5(user.getPassword()));
		user = userService.getUser(user.getEmail(), user.getPassword());//同时会得到用户的roles
		if(user != null){
			ActionContext.getContext().getSession().put("user", user);
			return SUCCESS;
		}
		else {
			addActionError("用户名或密码不正确！");
			return INPUT;
		}
	}
	
	@Action(value="/user/logout", results={@Result(type="redirectAction", location="index", 
			params={"namespace","/"})})
	public String logout() throws Exception {
		ActionContext.getContext().getSession().put("user", null);
		return SUCCESS;
	}
	
	public void setJsonMap(Map<String, Object> jsonMap) {
		this.jsonMap = jsonMap;
	}

	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
