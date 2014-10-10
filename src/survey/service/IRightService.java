package survey.service;

import java.util.List;
import java.util.Set;

import survey.model.security.Right;

public interface IRightService extends IService<Right> {
	public void saveOrUpdateRight(Right right);
	/**
	 * ����url׷��Ȩ��
	 */
	public void appendRightByURL(String url);
	public void batchUpdateRights(List<Right> allRights);
	
	public List<Right> findRightsInRange(Integer[] ids);	//ͨ��id������Ȩ�޼���
	public List<Right> findRightsNotInRange(Set<Right> rights);
	public int getMaxRightPos();	//��ѯ���Ȩ��λ
}
