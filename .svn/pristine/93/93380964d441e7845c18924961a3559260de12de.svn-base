package survey.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.User;
import survey.model.security.Right;
import survey.model.security.Role;
import survey.service.IRightService;
import survey.service.IRoleService;
import survey.service.IUserService;
import survey.util.ValidateUtil;

@Component("userService")
@Transactional
public class UserServiceImpl extends ServiceImpl<User> implements IUserService{
	
	@Resource
	private IRoleService roleService;
	@Resource
	private IRightService rightService;
	
	public boolean existsEmail(String email) {
		User u = (User) dao.createQuery("from User u where u.email = :email")
				.setParameter("email", email).uniqueResult();
		if(u != null) return true;
		else return false;
	}
	
	public User getUser(String email, String pass) {
		User user = (User)dao.createQuery("from User u where u.email = :email and u.password = :pass")
					.setParameter("email", email).setParameter("pass", pass).uniqueResult();
		return user;
	}
	
	public User getUserWithRoles(Integer id) {
		String hql = "from User u where u.id = ?";
		User u =  dao.uniqueResult(hql, id);
		if(ValidateUtil.isValid(u.getRoles())){			//如果rights不为空，将其取出
			for(Role r : u.getRoles()){
				r.getId();
			}
		}
		return u;
	}

	@SuppressWarnings("unchecked")
	public void updateAuthorize(int userId, Integer[] ownRoleIds) {
		User u = this.get(userId);		//得到需要修改的user
		//判断ownRightIds是否有效
		if(!ValidateUtil.isValid(ownRoleIds)){
			u.getRoles().clear();
			u.setRightSum(null);
		}
		//有权限
		else{
			List<Role> roles = roleService.findRolesInRange(ownRoleIds);
			u.setRoles(new HashSet(roles));
			//初始化权限位数组
			int maxPos = rightService.getMaxRightPos();
			long[] rightSum = new long[maxPos + 1];
			int pos = 0;
			long code = 0;
			for(Role role : roles){
				if("-1".equals(role.getRoleValue())){
					u.setSuperAdmin(true);
					this.update(u);
					return;
				}
				for(Right right : role.getRights() ){
					pos = right.getRightPos();
					code = right.getRightCode();
					rightSum[pos] = rightSum[pos] | code;
				}
			}
			u.setRightSum(rightSum);		//更新用户权限和
		}
		this.update(u);
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

	public IRoleService getRoleService() {
		return roleService;
	}

	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}

	public IRightService getRightService() {
		return rightService;
	}

}
