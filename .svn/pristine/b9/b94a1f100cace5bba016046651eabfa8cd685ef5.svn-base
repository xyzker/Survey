package survey.service;

import java.util.List;
import java.util.Set;

import survey.model.security.Role;

public interface IRoleService extends IService<Role> {

	public void saveOrUpdateRole(Role role, Integer[] ownRightIds);

	public Role getRoleWithRights(Integer id);		//得到角色以及对应权限

	public List<Role> findRolesNotInRange(Set<Role> roles);

	public List<Role> findRolesInRange(Integer[] ownRoleIds);

}
