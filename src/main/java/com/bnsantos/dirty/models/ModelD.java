package com.bnsantos.dirty.models;

public class ModelD {
	private String value;
	private int[] array;
	private float percent;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int[] getArray() {
		return array;
	}
	public void setArray(int[] array) {
		this.array = array;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ModelD){
			ModelD modelD = (ModelD) obj;
			boolean valueEqual=false, arrayEqual=false;
			
			if(this.value==null){
				valueEqual = modelD.value==null;
			}else{
				valueEqual = this.value.equals(modelD.value);
			}
			
			if(this.array==null){
				arrayEqual = modelD.array==null;
			}else{
				arrayEqual = this.array.equals(modelD.array);
			}
			return this.percent==modelD.percent&&valueEqual&&arrayEqual;
		}
		return false;
	}
	
	
}
