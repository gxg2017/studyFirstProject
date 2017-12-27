package com.offcn.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.offcn.mappers.ProductMapper;
import com.offcn.pojo.Vegetables;
import com.offcn.service.ProductService;
import com.offcn.serviceImpl.ProductServiceImpl;

public class TestImport2 {
	
	/*public static void main(String[] args) throws IOException {
		
		 Document document = Jsoup.connect("http://www.tuniu.com/?p=1400&cmpid=mkt_06002401&utm_source=baidu&utm_medium=brand&utm_campaign=brand").get();
			
		 Elements divs = document.getElementsByTag("a");
			for(Element e :divs){
				
				System.out.println(e.text());
			}
			
		Elements imgs=document.getElementsByTag("img");
		
		for (Element element : imgs) {
			System.out.println(element.attr("src"));
		}
	}
	
	public static void getInfo() throws IOException{
		 Document document = Jsoup.connect("http://www.tuniu.com").get();
		 
		 Element ul = document.getElementById("user_login_info");
		 
		 System.out.println(ul.text());
	}*/
	
	@Test
	public void test1() throws Exception {
		
			ProductService ps=new ProductServiceImpl();
			
			Vegetables vg=new Vegetables();
			
			ps.insertProduct(vg);
			
		}
		
	

}
