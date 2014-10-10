package survey.test;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class JFreeChartlTest {
	
	public static void main(String[] args){
		 DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
	        dpd.setValue("管理人员", 25);  //输入数据
	        dpd.setValue("市场人员", 25);
	        dpd.setValue("开发人员", 45);
	        dpd.setValue("其他人员", 10);
	        
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
	        
	        JFreeChart chart=ChartFactory.createPieChart("某公司人员组织数据图",dpd,true,true,false); 
	        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
	        
	      //得到绘图区
			PiePlot plot = (PiePlot) chart.getPlot();
			//定制标签
			//{0}:公司名称
			//{1}:公司销售额
			//{2}:百分比
			//{3}:总和
			//{4}:没有了
			plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}: {2}"));
	        
	        ChartFrame chartFrame=new ChartFrame("某公司人员组织数据图",chart); 
	        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
	        chartFrame.pack(); //以合适的大小展现图形
	        chartFrame.setVisible(true);//图形是否可见
	}
}
