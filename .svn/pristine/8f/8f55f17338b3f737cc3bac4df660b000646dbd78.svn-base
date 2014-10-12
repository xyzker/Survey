package survey.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import survey.model.Survey;

/*
 * 自定义数据源路由（分布式数据库）
 */
public class SurveyDataSourceRouter extends AbstractRoutingDataSource {

	/*
	 * 检测当前key
	 */
	protected Object determineCurrentLookupKey() {
		//取得SurveyToken
		Survey token = SurveyToken.getCurrentToken();
		if(token != null){
			Integer id = token.getId();
			//解除令牌的绑定
			SurveyToken.unbindToken();
			return ((id % 2) == 0) ? "even" : "odd"; 
		}
		return null;
	}
	
}