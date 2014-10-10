package survey.action;

import java.awt.Font;
import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import survey.model.Question;
import survey.model.statistics.OptionStatisticsModel;
import survey.model.statistics.QuestionStatisticsModel;
import survey.service.IQuestionService;
import survey.service.IStatisticsService;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("survey")
public class ChartOutputAction extends ActionSupport {

	private static final long serialVersionUID = -3460249769771209348L;
	/* 平面饼图 */
	private static final int CHARTTYPE_PIE_2D = 0;
	/* 立体饼图 */
	private static final int CHARTTYPE_PIE_3D = 1;
	/* 水平平面柱状图 */
	private static final int CHARTTYPE_BAR_2D_H = 2;
	/* 竖直平面柱状图 */
	private static final int CHARTTYPE_BAR_2D_V = 3;
	/* 水平立体柱状图 */
	private static final int CHARTTYPE_BAR_3D_H = 4;
	/* 竖直立体柱状图 */
	private static final int CHARTTYPE_BAR_3D_V = 5;
	/* 平面折线图 */
	private static final int CHARTTYPE_LINE_2D = 6;
	/* 立体折线图 */
	private static final int CHARTTYPE_LINE_3D = 7;
	
	private String[] colors = {
		"red","green","blue","yellow","#00FFFF","gray"
	};
	
	private Question question;
	//图表类型
	private int chartType;
	private JFreeChart chart;
	private QuestionStatisticsModel qsm;
	
	@Resource
	private IStatisticsService statisticsService;
	@Resource
	private IQuestionService questionService;
	
	@Action(value="/question/chartOutput", results={@Result(type="chart", params={"height",
			"300","width","400"})})
	public String chartOutput(){
		DefaultPieDataset pieds = null;// 饼图的数据集
		DefaultCategoryDataset cateds = null;// 种类数据集
		question = questionService.get(question.getId());
		//统计问题
		qsm = statisticsService.statistics(question);
		if(chartType < 2){
			pieds = new DefaultPieDataset();
			for(OptionStatisticsModel osm : getQsm().getOsms()){
				pieds.setValue(osm.getOptionLabel(), osm.getCount());
			}
		}else{
			cateds = new DefaultCategoryDataset();
			for (OptionStatisticsModel osm : getQsm().getOsms()) {
				cateds.addValue(osm.getCount(), osm.getOptionLabel(), "");
			}
		}
		
		 //创建主题样式  
        StandardChartTheme standardChartTheme=new StandardChartTheme("CN");  
        //设置标题字体  
        standardChartTheme.setExtraLargeFont(new Font("隶书",Font.BOLD,20));  
        //设置图例的字体  
        standardChartTheme.setRegularFont(new Font("宋书",Font.PLAIN,15));  
        //设置轴向的字体  
        standardChartTheme.setLargeFont(new Font("宋书",Font.PLAIN,15));  
        //应用主题样式  
        ChartFactory.setChartTheme(standardChartTheme);  
		
        // 判断要求的图形
		switch (chartType) {
			case CHARTTYPE_PIE_2D:// 平面饼图
				chart = ChartFactory.createPieChart(getQsm().getQuestion().getTitle(), pieds, true, true, false);
				break ;
			case CHARTTYPE_PIE_3D:// 立体饼图
				chart = ChartFactory.createPieChart3D(getQsm().getQuestion().getTitle(), pieds, true, true, false);
				//设置前景色透明度
				chart.getPlot().setForegroundAlpha(0.6f);
				break ;
			case CHARTTYPE_BAR_2D_H:
				chart = ChartFactory.createBarChart(getQsm().getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.HORIZONTAL, true, true, false);
				break ;
			case CHARTTYPE_BAR_2D_V:
				chart = ChartFactory.createBarChart(getQsm().getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.VERTICAL, true, true, false);
				break;
			case CHARTTYPE_BAR_3D_H:
				chart = ChartFactory.createBarChart3D(getQsm().getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.HORIZONTAL, true, true, false);
				break;
			case CHARTTYPE_BAR_3D_V:
				chart = ChartFactory.createBarChart3D(getQsm().getQuestion().getTitle(), "选项", "人数", cateds,
						PlotOrientation.VERTICAL, true, true, false);
				break ;
			case CHARTTYPE_LINE_2D:// 平面条形图
				chart = ChartFactory.createLineChart(getQsm().getQuestion().getTitle(), "", "", cateds,
						PlotOrientation.VERTICAL, true, true, false);
				break ;
			case CHARTTYPE_LINE_3D:// 立体条形图
				chart = ChartFactory.createLineChart3D(getQsm().getQuestion().getTitle(), "", "", cateds,
						PlotOrientation.HORIZONTAL, true, true, false);
				break ;
		}
		//设置饼图特效
		if(chart.getPlot() instanceof PiePlot){
			PiePlot pieplot = (PiePlot) chart.getPlot();
			//定制标签
			//{0}:公司名称
			//{1}:公司销售额
			//{2}:百分比
			//{3}:总和
			pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {1}({2})"));
		}
		
		return SUCCESS;
	}
	
	public String matrixStatistics(){
		question = questionService.get(question.getId());
		//统计问题
		qsm = statisticsService.statistics(question);
		return "" + question.getQuestionType();
	}
	
	/*
	 * 计算每个选项的统计结果
	 */
	public String getScale(int rowIndex, int colIndex){
		//问题回答人数
		int qcount = qsm.getCount();
		//选项回答人数
		int ocount = 0;
		for(OptionStatisticsModel osm : qsm.getOsms()){
			if(osm.getMatrixRowIndex() == rowIndex && osm.getMatrixColIndex() == colIndex){
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if(qcount != 0){
			scale = (float)ocount/qcount * 100;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return ocount + " ( " + df.format(scale)  + "% )";
	}
	
	/*
	 * 计算每个选项的统计结果
	 */
	public String getScale(int rowIndex, int colIndex, int optIndex){
		//问题回答人数
		int qcount = qsm.getCount();
		//选项回答人数
		int ocount = 0;
		for(OptionStatisticsModel osm : qsm.getOsms()){
			if(osm.getMatrixRowIndex() == rowIndex 
					&& osm.getMatrixColIndex() == colIndex
						&& osm.getMatrixSelectIndex() == optIndex){
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if(qcount != 0){
			scale = (float)ocount/qcount * 100;
		}
		DecimalFormat df = new DecimalFormat("#,##0.00");
		return ocount + " ( " + df.format(scale)  + "% )";
	}
	
	/*
	 * 计算每个选项的百分比(图表中对应为显示的像素)
	 */
	public int getPercent(int rowIndex, int colIndex, int optIndex){
		//问题回答人数
		int qcount = qsm.getCount();
		//选项回答人数
		int ocount = 0;
		for(OptionStatisticsModel osm : qsm.getOsms()){
			if(osm.getMatrixRowIndex() == rowIndex 
					&& osm.getMatrixColIndex() == colIndex
						&& osm.getMatrixSelectIndex() == optIndex){
				ocount = osm.getCount();
				break;
			}
		}
		float scale = 0;
		if(qcount != 0){
			scale = (float)ocount/qcount * 100;
		}
		return (int)scale;
	}


	public void setQuestion(Question question) {
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}

	public void setChartType(int chartType) {
		this.chartType = chartType;
	}

	public int getChartType() {
		return chartType;
	}

	public void setStatisticsService(IStatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	public IStatisticsService getStatisticsService() {
		return statisticsService;
	}

	public void setQuestionService(IQuestionService questionService) {
		this.questionService = questionService;
	}

	public IQuestionService getQuestionService() {
		return questionService;
	}

	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}

	public JFreeChart getChart() {
		return chart;
	}

	public void setQsm(QuestionStatisticsModel qsm) {
		this.qsm = qsm;
	}

	public QuestionStatisticsModel getQsm() {
		return qsm;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public String[] getColors() {
		return colors;
	}
}
