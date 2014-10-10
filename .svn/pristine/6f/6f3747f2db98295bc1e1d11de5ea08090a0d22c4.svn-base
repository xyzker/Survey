package survey.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import survey.interceptor.UserAware;
import survey.model.Answer;
import survey.model.Question;
import survey.model.Survey;
import survey.model.User;
import survey.service.ISurveyIntegratedService;
import survey.service.ISurveyService;
import survey.util.ValidateUtil;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class SurveyAction extends ActionSupport implements UserAware {

	private static final long serialVersionUID = 7199813643470519636L;
	
	@Resource
	private ISurveyService surveyService;
	
	@Resource
	private ISurveyIntegratedService surveyIntegratedService;
	
	private List<Survey> mySurveys = new ArrayList<Survey>();
	
	private User user;
	private Survey survey;
	
	private File logoPhoto;
	private String logoPhotoContentType;
	private String logoPhotoFileName;	
	
	private InputStream inputStream;
	private String fileName;
	
	@Action(value="/survey/mySurveys")
	public String mySurveys(){
		mySurveys = surveyService.findMySurveys(user);
		return SUCCESS;
	}
	
	@Action(value="/survey/newSurvey", results={@Result(type="redirectAction", 
			location="editSurvey", params = {"survey.id", "${survey.id}"})})
	public String newSurvey(){
		survey = surveyIntegratedService.newSurvey(user);
		return SUCCESS;
	}
	
	@Action(value="/survey/designSurvey")
	public String designSurvey(){
		survey = surveyService.getSurveyDetail(survey.getId());
		return SUCCESS;
	}
	
	@Action(value="/survey/editSurvey")
	public String editSurvey(){
		survey = surveyService.get(survey.getId());
		return SUCCESS;
	}
	
	@Action(value="/survey/updateSurvey", results={@Result(type="redirectAction", 
			location="designSurvey", params = {"survey.id", "${survey.id}"})})
	public String updateSurvey(){
		surveyService.saveOrUpdate(survey);
		return SUCCESS;
	}
	
	@Action(value="/survey/deleteSurvey", results={@Result(type="redirectAction", 
			location="mySurveys")})
	public String deleteSurvey(){
		survey = surveyService.get(survey.getId());
		surveyService.delete(survey);
		return SUCCESS;
	}
	
	@Action(value="/survey/clearAnswers", results={@Result(type="redirectAction", 
			location="mySurveys")})
	public String clearAnswers(){
		survey = surveyService.get(survey.getId());
		surveyIntegratedService.clearAnswers(survey);
		return SUCCESS;
	}
	
	@Action(value="/survey/toggleStatus", results={@Result(type="redirectAction", 
			location="mySurveys")})
	public String toggleStatus(){
		survey = surveyService.get(survey.getId());
		surveyService.toggleStatus(survey);
		return SUCCESS;
	}
	
	@Action(value="/survey/addLogo-input")
	public String addLogoInput(){
		return SUCCESS;
	}
	
	@Action(value="/survey/addLogo",interceptorRefs = {@InterceptorRef("loginStack"), 
			@InterceptorRef("uploadStack") }, results={@Result(type="redirectAction", 
			location="designSurvey", params = {"survey.id", "${survey.id}"})})
	public String addLogo() throws Exception{
		if(ValidateUtil.isValid(logoPhotoFileName)){
			survey = surveyService.get(survey.getId());
			String savePath = ServletActionContext.getServletContext().getRealPath("/upload");
			//扩展名
			String ext = logoPhotoFileName.substring(logoPhotoFileName.lastIndexOf("."));
			logoPhotoFileName = "survey_" + survey.getId() + ext;
			//以服务器的文件保存地址和文件名建立上传文件输出流
			FileOutputStream fos = new FileOutputStream(savePath + 
									"/" + logoPhotoFileName);
			FileInputStream fis = new FileInputStream(getLogoPhoto());
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = fis.read(buffer)) > 0){
				fos.write(buffer, 0, len);
			}
			fis.close();
	        fos.close();
	        
	        survey.setLogoPhotoPath("/upload/" + logoPhotoFileName);
	        surveyService.saveOrUpdate(survey);
	        return SUCCESS;
		}
		else return INPUT;
	}
	
	@Action(value="/survey/analyzeSurvey")
	public String analyzeSurvey(){
		survey = surveyService.getSurveyDetail(survey.getId());
		return SUCCESS;
	}
	
	/*
	 *收集信息 
	 */
	@Action(value="/survey/collectInformation", results={@Result(type="stream", 
			params={"inputName","inputStream","contentType","application/vnd.ms-excel", 
			"contentDisposition", "attachment;filename=\"${fileName}\""})})
	public String collectInformation() throws IOException{
		survey = surveyService.getSurveyDetail(survey.getId());
		//存放qid --> index映射
		Map<Integer, Integer> qidIndexMap = new HashMap<Integer, Integer>();
		
		HSSFWorkbook wb = new HSSFWorkbook();	//工作簿
		HSSFSheet sheet = wb.createSheet("收集信息 ———" + survey.getTitle());	//工作表
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		List<Question> questions = surveyIntegratedService.getQuestions(survey);
		for(int i=0; i<questions.size(); i++){
			cell = row.createCell(i);
			cell.setCellValue(questions.get(i).getTitle());
			sheet.autoSizeColumn(i);
			qidIndexMap.put(questions.get(i).getId(), i);
		}
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		//输出answers
		List<Answer> answers = surveyIntegratedService.getAnswers(survey.getId());
		String oldUuid = "";
		String newUuid = "";
		int rowIndex = 0;
		for(Answer a : answers){
			newUuid = a.getUuid();
			if(!newUuid.equals(oldUuid)){
				rowIndex ++;
				oldUuid = newUuid;
				row = sheet.createRow(rowIndex);
			}
			cell = row.createCell(qidIndexMap.get(a.getQuestion().getId()));
			cell.setCellValue(a.getAnswersId());
			cell.setCellStyle(style);
		}
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		baos.close();
		inputStream = new ByteArrayInputStream(baos.toByteArray());
		//下载时被显示的文件名包含中文时,要编码
		fileName = new String((survey.getTitle() + "_" + "信息收集.xls").getBytes("GBK"),"ISO8859-1");
		return SUCCESS;
	}
	
	public void setSurveyService(ISurveyService surveyService) {
		this.surveyService = surveyService;
	}

	public ISurveyService getSurveyService() {
		return surveyService;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setSurveyIntegratedService(ISurveyIntegratedService surveyIntegratedService) {
		this.surveyIntegratedService = surveyIntegratedService;
	}

	public ISurveyIntegratedService getSurveyIntegratedService() {
		return surveyIntegratedService;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser(){
		return user;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setLogoPhoto(File logoPhoto) {
		this.logoPhoto = logoPhoto;
	}

	public File getLogoPhoto() {
		return logoPhoto;
	}

	public void setLogoPhotoContentType(String logoPhotoContentType) {
		this.logoPhotoContentType = logoPhotoContentType;
	}

	public String getLogoPhotoContentType() {
		return logoPhotoContentType;
	}

	public void setLogoPhotoFileName(String logoPhotoFileName) {
		this.logoPhotoFileName = logoPhotoFileName;
	}

	public String getLogoPhotoFileName() {
		return logoPhotoFileName;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

}
