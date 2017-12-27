package com.offcn.service;


import java.util.List;

import com.offcn.pojo.Cabbage;
import com.offcn.pojo.Vegetables;

public interface ProductService {
	//将蔬菜类导入到数据库中
	public void insertProduct(Vegetables vg);
	//得到2017年11月大白菜价格数据
	public List<Cabbage> getCabbageList();
	//得到2016年11月，2017年11月大白菜价格数据
	public void getTwoYearCabbageList();
	
	public void producePdf();

}
