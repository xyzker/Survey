package survey.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.security.Right;
import survey.service.IRightService;
import survey.util.StringUtil;
import survey.util.ValidateUtil;

@Component("rightService")
@Transactional
public class RightServiceImpl extends ServiceImpl<Right> implements IRightService {
	
	/*
	 * ����/����Ȩ��
	 */
	public void saveOrUpdateRight(Right right) {
		//�������
		int pos = 0;
		long code = 1L;
		if(right.getId() == null){	
			String hql = "select max(r.rightPos), max(r.rightCode) from " +
					"Right r where r.rightPos = (select max(rr.rightPos) from Right rr) ";
			Object[] arr = (Object[]) dao.uniqueResultObject(hql);
			Integer topPos = (Integer) arr[0];
			Long topCode = (Long) arr[1]; 
			//û��Ȩ��
			if(topPos == null){
				pos = 0;
				code = 1L;
			}
			else{
				//Ȩ�����Ƿ�ﵽ���ֵ
				if(topCode >= (1L << 60)){
					pos = topPos + 1;
					code = 1L;
				}
				else{
					pos = topPos;
					code = topCode << 1;
				}
			}
			right.setRightPos(pos);
			right.setRightCode(code);
		}
		//���²���
		dao.saveOrUpdate(right);
	}
	
	/**
	 * ����url׷��Ȩ��
	 */
	public void appendRightByURL(String url){
		String hql = "select count(*) from Right r where r.rightUrl = ?" ;
		Long count = (Long) dao.uniqueResultObject(hql,url);
		if(count == 0){
			Right r = new Right();
			r.setRightUrl(url);
			this.saveOrUpdateRight(r);
		}
	}

	public void batchUpdateRights(List<Right> allRights) {
		String hql = "update Right r set r.rightName = ?, r.common = ? where r.id = ?";
		for(Right r : allRights){
			dao.executeUpdateByHQL(hql, r.getRightName(), r.isCommon(), r.getId());
		}
	}

	public List<Right> findRightsInRange(Integer[] ids) {
		String hql = "from Right r where r.id in (" + StringUtil.arr2Str(ids) +")";
		return dao.list(hql);
	}

	public List<Right> findRightsNotInRange(Set<Right> rights) {
		if(!ValidateUtil.isValid(rights)){				//����Ȩ��Ϊ��
			return this.findAllEntities();
		}
		else{
			String hql = "from Right r where r.id not in (" + extractRightIds(rights) + ")";
			return dao.list(hql);
		}
	}

	/*
	 * ��ȡ����Right��id���γ��ַ��������ŷָ�
	 */
	private String extractRightIds(Set<Right> rights) {
		String tmp = "";
		for(Right r : rights){
			tmp = tmp + r.getId() + ",";
		}
		tmp = tmp.substring(0,tmp.length()-1);
		return tmp;
	}

	public int getMaxRightPos() {
		String hql = "select max(r.rightPos) from Right r";
		Integer pos = (Integer) dao.uniqueResultObject(hql);
		return pos == null ? 0 : pos;
	}

}
