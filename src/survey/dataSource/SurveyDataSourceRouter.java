package survey.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import survey.model.Survey;

/*
 * �Զ�������Դ·�ɣ��ֲ�ʽ���ݿ⣩
 */
public class SurveyDataSourceRouter extends AbstractRoutingDataSource {

	/*
	 * ��⵱ǰkey
	 */
	protected Object determineCurrentLookupKey() {
		//ȡ��SurveyToken
		Survey token = SurveyToken.getCurrentToken();
		if(token != null){
			Integer id = token.getId();
			//������Ƶİ�
			SurveyToken.unbindToken();
			return ((id % 2) == 0) ? "even" : "odd"; 
		}
		return null;
	}
	
}