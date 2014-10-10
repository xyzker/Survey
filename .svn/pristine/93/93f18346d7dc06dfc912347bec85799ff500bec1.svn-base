package survey.service;


import survey.model.User;
import survey.service.IService;

public interface IUserService extends IService<User> {
	public boolean existsEmail(String email);
	public User getUser(String email, String pass);
	public User getUserWithRoles(Integer id);
	public void updateAuthorize(int userId, Integer[] ownRoleIds);
}
