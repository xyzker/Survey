package survey.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.model.security.Right;
import survey.service.IRightService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class RightAction extends ActionSupport {
	private static final long serialVersionUID = 2607745594923536682L;
	
	private List<Right> allRights = new ArrayList<Right>();
	private Right right;
	
	@Resource
	private IRightService rightService;
	
	/**
	 * 查询所有权限
	 */
	@Action(value="/right/findAllRights", results={@Result(location="/WEB-INF/content/right/rightList.jsp")})
	public String findAllRights(){
		setAllRights(rightService.findAllEntities());
		return SUCCESS ;
	} 
	
	@Action(value="/right/addRight", results={@Result(location="/WEB-INF/content/right/editRight.jsp")})
	public String addRight(){
		right = new Right();
		return SUCCESS;
	}
	
	@Action(value="/right/saveOrUpdateRight", results={@Result(type="redirectAction", location="findAllRights")})
	public String saveOrUpdateRight(){
		rightService.saveOrUpdateRight(right);
		return SUCCESS;
	}
	
	@Action(value="/right/editRight", results={@Result(location="/WEB-INF/content/right/editRight.jsp")})
	public String editRight(){
		right = rightService.get(right.getId());
		return SUCCESS;
	}
	
	@Action(value="/right/deleteRight", results={@Result(type="redirectAction", location="findAllRights")})
	public String deleteRight(){
		right = rightService.get(right.getId());
		rightService.delete(right);
		return SUCCESS;
	}
	
	@Action(value="/right/batchUpdateRights", results={@Result(type="redirectAction", location="findAllRights")})
	public String batchUpdateRights(){
		rightService.batchUpdateRights(allRights);
		return SUCCESS;
	}

	public void setRightService(IRightService rightService) {
		this.rightService = rightService;
	}

	public IRightService getRightService() {
		return rightService;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}

	public List<Right> getAllRights() {
		return allRights;
	}

	public void setRight(Right right) {
		this.right = right;
	}

	public Right getRight() {
		return right;
	}

}
