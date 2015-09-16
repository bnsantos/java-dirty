package com.bnsantos.dirty;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.bnsantos.dirty.exceptions.DiffCheckerException;

public class DiffHelper {
	public static <T> T diff(T original, T edited) throws DiffCheckerException{
		if(!original.getClass().isInstance(edited)){
			throw new IllegalArgumentException("Not same class");
		}
		if(original instanceof List || edited instanceof List){
			throw new IllegalArgumentException("This method can't handle lists generics with different objects types");
		}
		try {
			Constructor<? extends Object> constructor = original.getClass().getConstructor();
			@SuppressWarnings("unchecked")
			T diff = (T) constructor.newInstance();
			boolean isDiff = false;
			Field[] declaredFields = original.getClass().getDeclaredFields();
			for (int i = 0; i < declaredFields.length; i++) {
				declaredFields[i].setAccessible(true);
				Object fieldOriginal = declaredFields[i].get(original);
				Object fieldEdited = declaredFields[i].get(edited);
				if(fieldOriginal!=null){
					if(!fieldOriginal.equals(fieldEdited)){
						isDiff = true;
						declaredFields[i].set(diff, fieldEdited);
					}	
				}else if(fieldEdited!=null){
					declaredFields[i].set(diff, fieldEdited);
					isDiff = true;
				}
			}
			if(isDiff){
				return diff;	
			}else{
				return null;
			}
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new DiffCheckerException("Error while diff objects", e);
		}
	}
}
