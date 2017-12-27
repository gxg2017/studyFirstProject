package com.offcn.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offcn.pojo.Cabbage;
import com.offcn.pojo.Vegetables;
import com.offcn.service.ProductService;

import net.sf.json.JSONArray;

@Controller
public class ProductController {
	@Autowired
	private ProductService  productService;
	/**
	 * 抓取到的数据倒入到数据库中
	 * @param vg
	 * @return
	 */
	@RequestMapping(value="dataImport.action")
	public String dataImport(Vegetables vg){
		
		productService.insertProduct(vg);
		
		return "vegetables";
		
	}
	//ajax请求返回抓取的数据集合
	@RequestMapping(value="getCabbageList.action", method = RequestMethod.POST)
	@ResponseBody
	public List<Cabbage> getCabbageList(){
		
		List<Cabbage> cgList = productService.getCabbageList();
		
		
		
		return cgList;
		 
		
	}
	
	@RequestMapping(value="produceImg.action")
	public String produceImg(){
		productService.getTwoYearCabbageList();
		
		return "vegetables";
	}
	
	@RequestMapping(value="producePdf.action")
	public String producePdf(){
		productService.producePdf();
		
		return "vegetables";
	}
}
