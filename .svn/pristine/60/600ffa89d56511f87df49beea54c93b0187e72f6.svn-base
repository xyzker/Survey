package survey.service;

import java.util.List;
import java.util.Set;

import survey.model.security.Right;

public interface IRightService extends IService<Right> {
	public void saveOrUpdateRight(Right right);
	/**
	 * 按照url追加权限
	 */
	public void appendRightByURL(String url);
	public void batchUpdateRights(List<Right> allRights);
	
	public List<Right> findRightsInRange(Integer[] ids);	//通过id数组找权限集合
	public List<Right> findRightsNotInRange(Set<Right> rights);
	public int getMaxRightPos();	//查询最大权限位
}
