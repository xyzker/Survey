package survey.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.security.Right;
import survey.model.security.Role;
import survey.service.IRightService;
import survey.service.IRoleService;
import survey.util.StringUtil;
import survey.util.ValidateUtil;

@Component("roleService")
@Transactional
public class RoleServiceImpl extends ServiceImpl<Role> implements IRoleService {
	
	@Resource
	private IRightService rightService;
	
	@SuppressWarnings("unchecked")
	public void saveOrUpdateRole(Role role, Integer[] ownRightIds) {
		//判断ownRightIds是否有效
		if(!ValidateUtil.isValid(ownRightIds)){
			role.getRights().clear();
		}
		//有权限
		else{
			List<Right> rights = rightService.findRightsInRange(ownRightIds);
			role.setRights(new HashSet(rights));
		}
		this.saveOrUpdate(role);
	}
	
	public List<Role> findRolesInRange(Integer[] ids) {
		String hql = "from Role r where r.id in (" + StringUtil.arr2Str(ids) +")";
		return dao.list(hql);
	}

	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}

	public IRightService getRightService() {
		return rightService;
	}

	public Role getRoleWithRights(Integer id) {
		String hql = "from Role r where r.id = ?";
		Role role =  dao.uniqueResult(hql, id);
		if(ValidateUtil.isValid(role.getRights())){			//如果rights不为空，将其取出
			for(Right r : role.getRights()){
				r.getId();
			}
		}
		return role;
	}
	
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if(!ValidateUtil.isValid(roles)){				//已有权限为空
			return this.findAllEntities();
		}
		else{
			String hql = "from Role r where r.id not in (" + extractRoleIds(roles) + ")";
			return dao.list(hql);
		}
	}

	private String extractRoleIds(Set<Role> roles) {
		String tmp = "";
		for(Role r : roles){
			tmp = tmp + r.getId() + ",";
		}
		tmp = tmp.substring(0,tmp.length()-1);
		return tmp;
	}

}
