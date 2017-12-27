package com.offcn.serviceImpl;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.offcn.mappers.ProductMapper;
import com.offcn.pojo.Cabbage;
import com.offcn.pojo.Vegetables;
import com.offcn.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	/**
	 * 抓取数据保存到数据库中
	 * 参数暂时用不到，懒得改了
	 */
	public void insertProduct(Vegetables vg) {

		for (int j = 1; j < 10; j++) {

			String url = "http://www.xinfadi.com.cn/marketanalysis/0/list/" + j + ".shtml";

			String html = getHtml(url);

			Document document = Jsoup.parse(html);

			Elements trs = document.getElementsByClass("tr_color");

			for (Element tr : trs) {
				Vegetables vt = new Vegetables();
				vt.setName(tr.child(0).text());

				vt.setMainprice(Double.parseDouble(tr.child(1).text()));

				vt.setAvgprice(Double.parseDouble(tr.child(2).text()));

				vt.setMaxprice(Double.parseDouble(tr.child(3).text()));

				vt.setSpec(tr.child(4).text());

				vt.setUnit(tr.child(5).text());

				Date time = Date.valueOf(tr.child(6).text());

				vt.setPublishtime(time);

				productMapper.insertProduct(vt);

				if (tr.nextElementSibling() != null) {
					Vegetables vt1 = new Vegetables();
					vt1.setName(tr.nextElementSibling().child(0).text());

					vt1.setMainprice(Double.parseDouble(tr.nextElementSibling().child(1).text()));

					vt1.setAvgprice(Double.parseDouble(tr.nextElementSibling().child(2).text()));

					vt1.setMaxprice(Double.parseDouble(tr.nextElementSibling().child(3).text()));

					vt1.setSpec(tr.nextElementSibling().child(4).text());

					vt1.setUnit(tr.nextElementSibling().child(5).text());

					Date time1 = Date.valueOf(tr.nextElementSibling().child(6).text());

					vt1.setPublishtime(time1);

					productMapper.insertProduct(vt1);
				}

			}

		}

	}

	/**
	 * 抓取到指定网页数据；
	 * 
	 * @param url
	 * @return
	 */

	public String getHtml(String url) {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpGet get = new HttpGet(url);

		String html = null;
		try {
			CloseableHttpResponse response = httpClient.execute(get);

			html = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return html;
	}

	/**
	 * 将大白菜11月的数据装在集合中；
	 */
	public List<Cabbage> getCabbageList() {

		List<Cabbage> cgList = new ArrayList<Cabbage>();

		String url = "http://www.xinfadi.com.cn/marketanalysis/0/list/2.shtml?prodname=%E5%A4%A7%E7%99%BD%E8%8F%9C&begintime=2017-11-01&endtime=2017-11-30";
		String url1 = "http://www.xinfadi.com.cn/marketanalysis/0/list/1.shtml?prodname=%E5%A4%A7%E7%99%BD%E8%8F%9C&begintime=2017-11-01&endtime=2017-11-30";
		List<Cabbage> cgList1 = grabData(url);
		List<Cabbage> cgList2 = grabData(url1);
		cgList.addAll(cgList1);
		cgList.addAll(cgList2);

		return cgList;
	}

	// 抓取到指定网页的数据转化成实体对象装在集合中返回；
	public List<Cabbage> grabData(String url) {

		List<Cabbage> cgList = new ArrayList<Cabbage>();

		String html = getHtml(url);

		Document document = Jsoup.parse(html);

		Elements trs = document.getElementsByClass("tr_color");

		for (Element tr : trs) {

			Cabbage cb1 = new Cabbage();
			cb1.setName(tr.child(0).text());

			cb1.setMainprice(Double.parseDouble(tr.child(1).text()));

			cb1.setAvgprice(Double.parseDouble(tr.child(2).text()));

			cb1.setMaxprice(Double.parseDouble(tr.child(3).text()));

			Date time = Date.valueOf(tr.child(6).text());

			cb1.setPublishtime(time);

			cgList.add(cb1);

			// System.out.println(tr.nextElementSibling().child(0).text());

			if (tr.nextElementSibling() != null) {

				Cabbage cb = new Cabbage();
				String str = tr.nextElementSibling().child(0).text();

				cb.setName(tr.nextElementSibling().child(0).text());

				cb.setMainprice(Double.parseDouble(tr.nextElementSibling().child(1).text()));

				cb.setAvgprice(Double.parseDouble(tr.nextElementSibling().child(2).text()));

				cb.setMaxprice(Double.parseDouble(tr.nextElementSibling().child(3).text()));

				Date time1 = Date.valueOf(tr.nextElementSibling().child(6).text());

				cb.setPublishtime(time1);

				cgList.add(cb);
			}

		}

		Collections.reverse(cgList);

		return cgList;
	}

	// 得到2016年11月大白菜价格数据
	public List<Cabbage> getOneYearCabbageList() {

		List<Cabbage> cgList = new ArrayList<Cabbage>();

		String url = "http://www.xinfadi.com.cn/marketanalysis/0/list/2.shtml?prodname=%E5%A4%A7%E7%99%BD%E8%8F%9C&begintime=2016-11-01&endtime=2016-11-30";
		String url1 = "http://www.xinfadi.com.cn/marketanalysis/0/list/1.shtml?prodname=%E5%A4%A7%E7%99%BD%E8%8F%9C&begintime=2016-11-01&endtime=2016-11-30";
		List<Cabbage> cgList1 = grabData(url);
		List<Cabbage> cgList2 = grabData(url1);
		cgList.addAll(cgList1);
		cgList.addAll(cgList2);

		return cgList;

	}

	/**
	 * 抓取两年11月数据用JfreeCharts生成折线图
	 */
	public void getTwoYearCabbageList() {
		// 2016-11月价格数据（大白菜）
		List<Cabbage> data1 = getOneYearCabbageList();
		// 2017-11月价格数据 （大白菜）
		List<Cabbage> data2 = getCabbageList();

		String[] columKeys = new String[data1.size()];
		String[] rowKeys = { "2016", "2017" };
		// 得到11月有效数据天数
		for (int i = 0; i < data1.size(); i++) {
			String timeStr = String.valueOf(data1.get(i).getPublishtime());

			String day = timeStr.substring(timeStr.lastIndexOf("-") + 1);

			columKeys[i] = day;
		}
		double[][] data = new double[2][data1.size()];

		for (int i = 0; i < data.length; i++) {

			for (int j = 0; j < data[i].length; j++) {

				Double price1 = data1.get(j).getAvgprice();

				Double price2 = data2.get(j).getAvgprice();

				data[0][j] = price1;
				data[1][j] = price2;

			}

		}
		System.out.println(data);
		CategoryDataset dataset = DatasetUtilities.createCategoryDataset(rowKeys, columKeys, data);

		// 创建主题样式
		StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
		// 设置标题字体
		standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));
		// 设置图例的字体
		standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));
		// 设置轴向的字体
		standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));

		// 应用主题样式
		ChartFactory.setChartTheme(standardChartTheme);

		JFreeChart chart = ChartFactory.createLineChart("2017年11月与2016年同期大白菜平均价对比走势图", "日期", "价格", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		CategoryPlot plot = chart.getCategoryPlot();// 图形的绘制结构对象

		// X 轴
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));// 轴标题
		domainAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));// 轴数值
		domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色

		// Y 轴
		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setLabelFont(new Font("宋体", Font.PLAIN, 12));
		rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色
		rangeAxis.setTickLabelFont(new Font("宋体", Font.PLAIN, 12));

		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		renderer.setItemLabelsVisible(true);
		plot.setRenderer(renderer);

		try {
			ChartUtilities.saveChartAsJPEG(new File("D:/photo/b1.jpg"), chart, 800, 600);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 生成pdf菜价分析报告
	 */
	public void producePdf() {

		// 2016-11月价格数据（大白菜）
		List<Cabbage> data1 = getOneYearCabbageList();
		// 2017-11月价格数据 （大白菜）
		List<Cabbage> data2 = getCabbageList();

		PdfWriter writer = null;
		try {
			writer = new PdfWriter("D:/photo/data1.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PdfDocument pdfdocument = new PdfDocument(writer);
		PdfDocumentInfo pdfInfo = pdfdocument.getDocumentInfo();

		pdfInfo.setTitle("抓取数据报表");

		com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document(pdfdocument);
		// 1、创建段落对象
		Paragraph pa = new Paragraph("2017年11月与2016年同期大白菜平均价走势对比图");
		// 设置文字对齐方式，居中
		pa.setTextAlignment(TextAlignment.CENTER);
		// 创建PDF字体
		PdfFont font = null;
		try {
			font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 为段落对象指定字体
		pa.setFont(font);

		// 设置字体大小
		pa.setFontSize(20);

		float[] columNum = new float[30];
		columNum[0] = 2f;
		for (int i = 1; i < columNum.length; i++) {
			columNum[i] = 1f;
		}
		Table table = new Table(columNum);

		table.setWidthPercent(100);

		Cell one = new Cell();

		table.addCell(one);
		for (int i = 1; i < columNum.length; i++) {

			table.addCell(new Cell().add(new Paragraph(String.valueOf(i)).setFont(font).setFontSize(8)));
		}

		Cell two = new Cell();

		two.add(new Paragraph("2016"));

		table.addCell(two);

		for (int i = 0; i < data1.size(); i++) {

			Double avgPrice = data1.get(i).getAvgprice();

			table.addCell(new Cell().add(new Paragraph(String.valueOf(avgPrice)).setFont(font).setFontSize(8)));
		}

		Cell three = new Cell();

		three.add(new Paragraph("2017"));

		table.addCell(three);

		for (int i = 0; i < data2.size(); i++) {

			Double avgPrice = data2.get(i).getAvgprice();

			table.addCell(new Cell().add(new Paragraph(String.valueOf(avgPrice)).setFont(font).setFontSize(8)));
		}

		// 读取图片
		Image image = null;
		try {
			image = new Image(ImageDataFactory.create("D:/photo/b1.jpg"));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 设置图片的绝对大小，宽和高
		// image.scaleAbsolute(50f, 50f);

		// 价格分析段落
		// 17年11月月初月末大白菜均价；
		Double a = data2.get(0).getAvgprice();
		Double b = data2.get(data2.size() - 1).getAvgprice();
		// 16年11月月初月末大白菜均价；
		Double c = data1.get(data1.size() - 1).getAvgprice();
		
		String incprice=mathIncrease(a, b);
		
		String incprice1=mathIncrease(c, b);
		//2017年11月最高最低价格
		Double maxPrice = getMonthMaxPrice(data2);
		
		Double mainPrice = getMonthMainPrice(data2);
		//2016年11月最高最低价格
		Double maxPrice1 = getMonthMaxPrice(data1);
		
		Double mainPrice1 = getMonthMainPrice(data1);
		//最低最高与同期的差价
		Double maxbw = betweenPrice(maxPrice,maxPrice1);
		
		Double mainbw = betweenPrice(mainPrice,mainPrice1);
		
		//本月最高价与最低价的差价与对比
		 
		Double mybw = betweenPrice(maxPrice,mainPrice);
		
		String myInc = mathIncrease(mainPrice,maxPrice);
		
		//与同期的对比；
		String maxInc = mathIncrease(maxPrice1,maxPrice);
		
		String mainInc = mathIncrease(mainPrice1,mainPrice);
			
		String str1 = data2.get(0).getPublishtime() + ",大白菜的平均价是" + a + " 元/斤,"
				+ data2.get(data2.size() - 1).getPublishtime() + ",价格是" + b + " 元/斤," + "月末比月初" + incprice
				+ "。  月末的价格比去年同期的"+c+" 元/斤" +incprice1;
		
		String str2 ="月内的最高价是"+maxPrice+" 元/斤,比去年同期的最高价"+maxPrice1+" 元/斤"+maxInc+"差价为"+maxbw;
		
		String str3 ="月内的最低价是"+mainPrice+" 元/斤,比去年同期的最低价"+mainPrice1+" 元/斤" +mainInc+"差价为"+mainbw;
		
		String str4 ="月内的最高价与最代价差价为"+mybw+"元/斤,"+""+myInc;
		
		
		Paragraph p1=new Paragraph(str1);
		
		Paragraph p2=new Paragraph(str2);
		
		Paragraph p3=new Paragraph(str3);
		
		Paragraph p4=new Paragraph(str4);
		
		p1.setFont(font);
		p2.setFont(font);
		p3.setFont(font);
		p4.setFont(font);
		
		doc.add(pa);
		doc.add(table);
		doc.add(image);
		doc.add(p1);
		doc.add(p2);
		doc.add(p3);
		doc.add(p4);
		doc.close();
		System.out.println("ok");

	}

	public String mathIncrease(Double a, Double b) {
		double increase = (b - a) / a;

		DecimalFormat df = new DecimalFormat("######0.00");
		String inc = df.format(increase * 100);
		String incprice = null;
		if (increase > 0) {
			incprice = "上涨" + inc + "%";
		} else {
			incprice = "下降" + inc.substring(inc.indexOf("-") + 1) + "%";
		}

		return incprice;
	}

	public Double getMonthMaxPrice(List<Cabbage> cList) {
		Double max = cList.get(0).getAvgprice();
		for (int i = 0; i < cList.size(); i++) {

			if (cList.get(i).getAvgprice() > max) {
				max = cList.get(i).getAvgprice();
			}
		}
		return max;
	}

	public Double getMonthMainPrice(List<Cabbage> cList) {
		Double main = cList.get(0).getAvgprice();
		for (int i = 0; i < cList.size(); i++) {

			if (cList.get(i).getAvgprice() < main) {
				main = cList.get(i).getAvgprice();
			}
		}
		return main;
	}
	
	public Double betweenPrice(Double a,Double b){
		Double maxInc=0.0;
		if(a>b){
			maxInc=a-b;
		}else{
			maxInc=b-a;
		}
		return maxInc;
	}

}
