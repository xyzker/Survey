package survey.util;

import java.util.Collection;

/**
 * 校验工具类
 */
public class ValidateUtil {
	
	/**
	 * 判断字符串有效性
	 */
	public static boolean isValid(String src){
		if(src == null || "".equals(src.trim())){
			return false ;
		}
		return true ;
	}
	
	/**
	 * 判断集合的有效性 
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){			
			return false ;
		}
		return true ;
	}
	
	/*
	 * 判断数组是否有效
	 */
	public static boolean isValid(Object[] arr){
		if(arr == null || arr.length == 0){
			return false;
		}
		return true;
	}
	
	/*
	 * 验证是否有权限
	 
	@SuppressWarnings("unchecked")
	public static boolean hasRight(String nameSpace, String actionName, HttpServletRequest request){
		if(!ValidateUtil.isValid(nameSpace) || nameSpace.equals("/")){
			nameSpace = "";
		}
		//将超链接的参数部分滤掉
		if(actionName.contains("?")){
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url =  nameSpace + "/" + actionName;		//得到权限url
		HttpSession session = request.getSession();
		ServletContext sc = session.getServletContext();
		Map<String, Right> map = (Map<String, Right>)sc.getAttribute("all_rights_map");
		Right r = map.get(url);
		//如果map中没有此权限，则添加入数据库
		if(r == null){
			WebApplicationContext ctx = WebApplicationContextUtils
		      .getWebApplicationContext(sc);
			IRightService rs = (IRightService)ctx.getBean("rightService");
			rs.appendRightByURL(url);
			return true;
		}
		else if(r.isCommon()){
			return true;
		}
		else{
			User user = (User)session.getAttribute("user");
			//未登陆
			if(user == null){
				return false;
			}
			//超级管理员
			else if(user.isSuperAdmin()){
				return true;
			}
			else{
				//有权限
				if(user.hasRight(r)){
					return true;
				}
				//没有权限
				else return false;
			}
		}
	}*/
}
