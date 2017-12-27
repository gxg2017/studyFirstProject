package com.offcn.mappers;

import com.offcn.pojo.Vegetables;

public interface ProductMapper {
	//将蔬菜倒入到数据库中
	public void insertProduct(Vegetables vg);

}
