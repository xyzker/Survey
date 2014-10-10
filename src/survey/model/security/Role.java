package survey.model.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import survey.model.BaseEntity;

/**
 * 角色
 */
@Entity
public class Role extends BaseEntity {
	@Id
	@GeneratedValue
	private Integer id;
	private String roleName;
	private String roleValue;
	private String roleDesc;
	
	//权限集合
	@ManyToMany
	@JoinTable(name = "role_right", joinColumns =	{@JoinColumn(name = "role_id") },
		inverseJoinColumns = { @JoinColumn(name= "right_id")}	)		//中间表配置
	private Set<Right> rights = new HashSet<Right>();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
	public Set<Right> getRights() {
		return rights;
	}
	
	
}
