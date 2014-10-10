package survey.service.impl;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import survey.model.Page;
import survey.service.IPageService;

@Component("pageService")
@Transactional
public class PageServiceImpl extends ServiceImpl<Page> implements IPageService{

	public void setOrderno(Page srcPage, Page tagPage, int pos) {
		//放目标页前
		if(pos == 0){
			//判断目标页是否为首页
			if(isFirstPage(tagPage)){
				srcPage.setOrderno(tagPage.getOrderno() - 0.01f);
			}
			else{
				//取目标页前页
				Page prePage = getPrePage(tagPage);
				srcPage.setOrderno((prePage.getOrderno() + tagPage.getOrderno())/2);
			}
		}
		//放目标页后
		else{
			//判断目标页是否为尾页
			if(isLastPage(tagPage)){
				srcPage.setOrderno(tagPage.getOrderno() + 0.01f);
			}
			else{
				//取目标页后页
				Page nextPage = getNextPage(tagPage);
				srcPage.setOrderno((nextPage.getOrderno() + tagPage.getOrderno())/2);
			}
		}
		this.saveOrUpdate(srcPage);
	}

	@SuppressWarnings("unchecked")
	public Page getPrePage(Page p) {
		List<Page> pages = (List<Page>)dao.createQuery("from Page p where p.survey = :survey " +
			"and p.orderno < :orderno order by p.orderno desc")
			.setParameter("survey", p.getSurvey()).setParameter("orderno", p.getOrderno()).list();
		return pages.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public Page getNextPage(Page p) {
		List<Page> pages = (List<Page>)dao.createQuery("from Page p where p.survey = :survey " +
			"and p.orderno > :orderno order by p.orderno asc")
			.setParameter("survey", p.getSurvey()).setParameter("orderno", p.getOrderno()).list();
		return pages.get(0);
	}

	public boolean isFirstPage(Page p) {
		float minOrderno = (Float)dao.createQuery("select min(p.orderno) from Page p where p.survey = :survey")
			.setParameter("survey", p.getSurvey()).uniqueResult();
		if(p.getOrderno() == minOrderno){
			return true;
		}
		return false;
	}
	
	public boolean isLastPage(Page p) {
		float maxOrderno = (Float)dao.createQuery("select max(p.orderno) from Page p where p.survey = :survey")
		.setParameter("survey", p.getSurvey()).uniqueResult();
		if(p.getOrderno() == maxOrderno){
			return true;
		}
		return false;
	}

	public Page getPageWithQuestions(Integer id) {
		return (Page)dao.createQuery("from Page p join fetch p.questions where p.id = :id")
				.setParameter("id", id).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public Page getFirstPage(int surveyId) {
		List<Page> pages = (List<Page>)dao.createQuery("from Page p join fetch p.questions where p.survey.id = :surveyId order by p.orderno asc")
						.setParameter("surveyId", surveyId).list();
		if(pages.size() == 0) return null;
		else return pages.get(0);
	}

}
