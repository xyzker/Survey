package survey.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.model.User;
import survey.model.security.Role;
import survey.service.IRoleService;
import survey.service.IUserService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class AuthorizeAction extends ActionSupport {

	private static final long serialVersionUID = 2666782931644085254L;
	
	private List<User> allUsers;
	private User user;
	private List<Role> noOwnRoles;
	private Integer[] ownRoleIds ;
	
	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;
	
	@Action(value="/authorize/findAllUsers", results={@Result(location="/WEB-INF" +
			"/content/authorize/userAuthorizeList.jsp")})
	public String findAllUsers(){
		allUsers = userService.findAllEntities();
		return SUCCESS;
	}

	@Action(value="/authorize/editAuthorize", results={@Result(location="/WEB-INF" +
			"/content/authorize/userAuthorize.jsp")})
	public String editAuthorize(){
		user = userService.getUserWithRoles(user.getId());
		noOwnRoles = roleService.findRolesNotInRange(user.getRoles());
		return SUCCESS;
	}
	
	@Action(value="/authorize/updateAuthorize", results={@Result(type="redirectAction", location="findAllUsers")})
	public String updateAuthorize(){
		userService.updateAuthorize(user.getId(), ownRoleIds);
		return SUCCESS;
	}
	
	@Action(value="/authorize/clearAuthorize", results={@Result(type="redirectAction", location="findAllUsers")})
	public String clearAuthorize(){
		user = userService.getUserWithRoles(user.getId());
		user.getRoles().clear();
		user.setRightSum(null);		//清空用户权限和
		userService.update(user);
		return SUCCESS;
	}
	
	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setNoOwnRoles(List<Role> noOwnRoles) {
		this.noOwnRoles = noOwnRoles;
	}

	public List<Role> getNoOwnRoles() {
		return noOwnRoles;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setOwnRoleIds(Integer[] ownRoleIds) {
		this.ownRoleIds = ownRoleIds;
	}

	public Integer[] getOwnRoleIds() {
		return ownRoleIds;
	}

}
