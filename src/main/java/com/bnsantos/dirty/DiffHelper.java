package com.bnsantos.dirty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class DiffHelper {
	public static <T> T diff(T original, T edited){
		if(!original.getClass().isInstance(edited)){
			throw new IllegalArgumentException("Not same class");
		}
		T diff = null;
		try {
			Constructor<? extends Object> constructor = original.getClass().getConstructor(null);
			diff = (T) constructor.newInstance(null);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Field[] declaredFields = original.getClass().getDeclaredFields();
		for (int i = 0; i < declaredFields.length; i++) {
			declaredFields[i].setAccessible(true);
			try {
				Object fieldOriginal = declaredFields[i].get(original);
				Object fieldEdited = declaredFields[i].get(edited);
				if(fieldOriginal!=null){
					if(!fieldOriginal.equals(fieldEdited)){
						declaredFields[i].set(diff, fieldEdited);
					}	
				}else if(fieldEdited!=null){
					declaredFields[i].set(diff, fieldEdited);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block throw this to 
				e.printStackTrace();
			}
		}
		return diff;
	}
}
