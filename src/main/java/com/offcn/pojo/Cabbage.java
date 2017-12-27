package com.offcn.pojo;

import java.sql.Date;

public class Cabbage {
	
	private String name;
	
	private Double mainprice;
	
	private Double avgprice;
	
	private Double maxprice;
	
	private Date publishtime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Double getMainprice() {
		return mainprice;
	}

	public void setMainprice(Double mainprice) {
		this.mainprice = mainprice;
	}

	public Double getAvgprice() {
		return avgprice;
	}

	public void setAvgprice(Double avgprice) {
		this.avgprice = avgprice;
	}

	public Double getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(Double maxprice) {
		this.maxprice = maxprice;
	}

}
