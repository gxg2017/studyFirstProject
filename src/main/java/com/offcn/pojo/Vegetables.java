package com.offcn.pojo;

import java.sql.Date;
import java.sql.Timestamp;

public class Vegetables {
	
	private Integer id;
	
	private String name;
	
	private Double mainprice;
	
	private Double avgprice;
	
	private Double maxprice;
	
	private String spec;
	
	private String unit;
	
	private Date publishtime;
	
	

	public Date getPublishtime() {
		return publishtime;
	}

	public void setPublishtime(Date publishtime) {
		this.publishtime = publishtime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	

}
