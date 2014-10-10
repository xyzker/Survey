package survey.service;

import survey.model.Page;

public interface IPageService extends IService<Page> {
	public void setOrderno(Page srcPage, Page tagPage, int pos);
	public boolean isFirstPage(Page p);
	public Page getPrePage(Page p);
	public boolean isLastPage(Page p);
	public Page getNextPage(Page p);
	public Page getPageWithQuestions(Integer id);
	public Page getFirstPage(int surveyId);		//得到调查首页
}
