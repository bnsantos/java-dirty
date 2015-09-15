package com.bnsantos.dirty.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bnsantos.dirty.DiffHelper;
import com.bnsantos.dirty.models.ModelA;
import com.bnsantos.dirty.models.ModelB;

public class TestCase {

	@Test(expected=IllegalArgumentException.class)
	public void test1(){
		ModelA original = new ModelA("test1", null);
		ModelB test = new ModelB("modelB"); 
		DiffHelper.diff(original, test);
	}
	
	@Test
	public void test2(){
		ModelA original = new ModelA("test2", null);
		ModelA edited = new ModelA(original.getValue(), original.getList());
		List<String> list = new ArrayList<>();
		list.add("item1");
		list.add("item2");
		list.add("item3");
		list.add("item4");
		edited.setList(list);
		
		ModelA diff = DiffHelper.diff(original, edited);
		Assert.assertNotNull(diff);
		Assert.assertNull(diff.getValue());
		Assert.assertEquals(diff.getList(), edited.getList());
	}
}
