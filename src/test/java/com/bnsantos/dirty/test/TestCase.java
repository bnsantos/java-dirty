package com.bnsantos.dirty.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bnsantos.dirty.DiffHelper;
import com.bnsantos.dirty.exceptions.DiffCheckerException;
import com.bnsantos.dirty.models.ModelA;
import com.bnsantos.dirty.models.ModelB;

public class TestCase {

	@Test(expected=IllegalArgumentException.class)
	public void testDifferentClasses() throws DiffCheckerException{
		ModelA original = new ModelA("test1", null);
		ModelB test = new ModelB("modelB"); 
		DiffHelper.diff(original, test);
	}
	
	@Test
	public void testSameObject() throws DiffCheckerException{
		ModelA original = new ModelA("test2", generateStringList(5));
		Assert.assertNull(DiffHelper.diff(original, original));
	}
	
	@Test
	public void testModelADiff1() throws DiffCheckerException{
		ModelA original = new ModelA("test3", null);
		ModelA edited = new ModelA(original.getValue(), generateStringList(10));
		ModelA diff = DiffHelper.diff(original, edited);
		Assert.assertNotNull(diff);
		Assert.assertNull(diff.getValue());
		Assert.assertEquals(diff.getList(), edited.getList());
	}
	
	@Test
	public void testModelADiff2() throws DiffCheckerException{
		ModelA original = new ModelA("test4", generateStringList(10));
		ModelA edited = new ModelA("test4Diff", original.getList());
		ModelA diff = DiffHelper.diff(original, edited);
		Assert.assertNotNull(diff);
		Assert.assertEquals("test4Diff", diff.getValue());
		Assert.assertNull(diff.getList());
	}
	
	
	
	public List<String> generateStringList(int count){
		List<String> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			list.add("Item"+i);
		}
		return list;
	}
}
