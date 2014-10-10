package survey.action;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.model.security.Right;
import survey.model.security.Role;
import survey.service.IRightService;
import survey.service.IRoleService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class RoleAction extends ActionSupport{

	private static final long serialVersionUID = 8499013082698036278L;

	private List<Role> allRoles;
	private Role role;
	//角色没有的权限集合
	private List<Right> noOwnRights ;
	//角色拥有的权限id数组
	private Integer[] ownRightIds ;
	
	@Resource
	private IRoleService roleService;
	@Resource
	private IRightService rightService;
	
	@Action(value="/role/findAllRoles", results={@Result(location="/WEB-INF/content/role/roleList.jsp")})
	public String findAllRoles(){
		allRoles = roleService.findAllEntities();
		return SUCCESS;
	}
	
	@Action(value="/role/addRole", results={@Result(location="/WEB-INF/content/role/editRole.jsp")})
	public String addRole(){
		role = new Role();
		noOwnRights = rightService.findAllEntities();
		return SUCCESS;
	}
	
	@Action(value="/role/saveOrUpdateRole", results={@Result(type="redirectAction", location="findAllRoles")})
	public String saveOrUpdateRole(){
		roleService.saveOrUpdateRole(role, ownRightIds);
		return SUCCESS;
	}
	
	@Action(value="/role/editRole", results={@Result(location="/WEB-INF/content/role/editRole.jsp")})
	public String editRole(){
		role = roleService.getRoleWithRights(role.getId());
		noOwnRights = rightService.findRightsNotInRange(role.getRights());
		return SUCCESS;
	}
	
	@Action(value="/role/deleteRole", results={@Result(type="redirectAction", location="findAllRoles")})
	public String deleteRole(){
		//因为有关联，不能直接deleteById
		role = roleService.get(role.getId());
		roleService.delete(role);
		return SUCCESS;
	}

	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}

	public List<Role> getAllRoles() {
		return allRoles;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setNoOwnRights(List<Right> noOwnRights) {
		this.noOwnRights = noOwnRights;
	}

	public List<Right> getNoOwnRights() {
		return noOwnRights;
	}

	public void setOwnRightIds(Integer[] ownRightIds) {
		this.ownRightIds = ownRightIds;
	}

	public Integer[] getOwnRightIds() {
		return ownRightIds;
	}

}
