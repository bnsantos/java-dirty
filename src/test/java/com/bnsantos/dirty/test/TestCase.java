package com.bnsantos.dirty.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bnsantos.dirty.DiffHelper;
import com.bnsantos.dirty.exceptions.DiffCheckerException;
import com.bnsantos.dirty.models.ModelA;
import com.bnsantos.dirty.models.ModelB;
import com.bnsantos.dirty.models.ModelC;
import com.bnsantos.dirty.models.ModelD;

public class TestCase {
	private static Random random;
	
	@BeforeClass
	public static void init(){
		random = new Random(System.currentTimeMillis());
	}

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
	
	@Test
	public void testChildrenObj() throws DiffCheckerException{
		ModelC original = new ModelC();
		List<ModelD> list = generateModelDList(15);
		original.setList(list);
		
		ModelC edited = new ModelC();

		List<ModelD> list2 = new ArrayList<>(list);
		list2.addAll(generateModelDList(2));
		edited.setList(list2);
		
		ModelC diff = DiffHelper.diff(original, edited);
		Assert.assertNotNull(diff);
	}
	
	@Test
	public void testChildrenObj2() throws DiffCheckerException{
		ModelC original = new ModelC();
		List<ModelD> list = generateModelDList(15);
		original.setList(list);
		
		ModelC edited = new ModelC();

		List<ModelD> list2 = new ArrayList<>(list);
		ModelD d = new ModelD();
		d.setArray(list2.get(0).getArray());
		d.setPercent(list2.get(0).getPercent());
		d.setValue("EditedValue");
		list2.remove(0);
		list2.add(0,  d);
		edited.setList(list2);
		
		ModelC diff = DiffHelper.diff(original, edited);
		Assert.assertNotNull(diff);
		Assert.assertEquals(15, diff.getList().size());
		Assert.assertEquals("EditedValue", diff.getList().get(0).getValue());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testListGenericsDifferentType() throws DiffCheckerException{
		List<ModelD> models = generateModelDList(15);
		List<String> strings = new ArrayList<>();
		strings.add("teste1");
		strings.add("teste2");
		strings.add("teste3");
		
		DiffHelper.diff(models, strings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testListGenericsDifferentType2() throws DiffCheckerException{
		List<String> strings = new ArrayList<>();
		strings.add("teste1");
		strings.add("teste2");
		strings.add("teste3");
		ModelA model = new ModelA("Test1", null);
		
		DiffHelper.diff(model, strings);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testList() throws DiffCheckerException{
		List<ModelD> list1 = generateModelDList(15);
		List<ModelD> list2 = new ArrayList<>(list1);
		ModelD d = new ModelD();
		d.setArray(generateRandomArray(4));
		d.setValue("New Value");
		d.setPercent(random.nextFloat());
		list2.set(0, d);

		DiffHelper.diff(list1, list2);
	}
	
	public List<String> generateStringList(int count){
		List<String> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			list.add("Item"+i);
		}
		return list;
	}
	
	public List<ModelD> generateModelDList(int count){
		List<ModelD> list = new ArrayList<>(count);
		for (int i = 0; i < count; i++) {
			ModelD d = new ModelD();
			d.setValue("ModelD"+i);
			d.setPercent(random.nextFloat());
			d.setArray(generateRandomArray(random.nextInt(100)));
			list.add(d);
		}
		return list;
	}
	
	public int[] generateRandomArray(int count){
		int[] array = new int[count];
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt(1000);
		}
		return array;
	}
}
