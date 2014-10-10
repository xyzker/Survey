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
	 * 保存/更新权限
	 */
	public void saveOrUpdateRight(Right right) {
		//插入操作
		int pos = 0;
		long code = 1L;
		if(right.getId() == null){	
			String hql = "select max(r.rightPos), max(r.rightCode) from " +
					"Right r where r.rightPos = (select max(rr.rightPos) from Right rr) ";
			Object[] arr = (Object[]) dao.uniqueResultObject(hql);
			Integer topPos = (Integer) arr[0];
			Long topCode = (Long) arr[1]; 
			//没有权限
			if(topPos == null){
				pos = 0;
				code = 1L;
			}
			else{
				//权限码是否达到最大值
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
		//更新操作
		dao.saveOrUpdate(right);
	}
	
	/**
	 * 按照url追加权限
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
		if(!ValidateUtil.isValid(rights)){				//已有权限为空
			return this.findAllEntities();
		}
		else{
			String hql = "from Right r where r.id not in (" + extractRightIds(rights) + ")";
			return dao.list(hql);
		}
	}

	/*
	 * 抽取所有Right的id，形成字符串，逗号分隔
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
