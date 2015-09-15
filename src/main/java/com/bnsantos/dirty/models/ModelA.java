package com.bnsantos.dirty.models;

import java.util.List;

public class ModelA {
	private String value;
	private List<String> list;
		
	public ModelA() {
		super();
	}
	public ModelA(String value, List<String> list) {
		super();
		this.value = value;
		this.list = list;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
}
