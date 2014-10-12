package survey.dataSource;

import survey.model.Survey;

/*
 * Survey令牌
 */
public class SurveyToken {
	
	private static ThreadLocal<Survey> token = new ThreadLocal<Survey>();

	/*
	 * 将制定的令牌对象绑定到当前线程
	 */
	public static void bindToken(Survey s){
		token.set(s);
	}
	
	/*
	 * 解除当前线程绑定的令牌
	 */
	public static void unbindToken(){
		token.remove();
	}
	
	/*
	 * 从当前线程获得绑定的令牌
	 */
	public static Survey getCurrentToken(){
		return token.get();
	}
	
}
