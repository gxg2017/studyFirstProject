package com.offcn.service;


import java.util.List;

import com.offcn.pojo.Cabbage;
import com.offcn.pojo.Vegetables;

public interface ProductService {
	//���߲��ർ�뵽���ݿ���
	public void insertProduct(Vegetables vg);
	//�õ�2017��11�´�ײ˼۸�����
	public List<Cabbage> getCabbageList();
	//�õ�2016��11�£�2017��11�´�ײ˼۸�����
	public void getTwoYearCabbageList();
	
	public void producePdf();

}
