package survey.model;

import java.lang.reflect.Field;

import javassist.Modifier;

/*
 * 抽象的实体超类，专门用于继承
 */
public abstract class BaseEntity {
	
	@SuppressWarnings("unchecked")
	public String toString(){
		try {
			StringBuffer buffer = new StringBuffer();
			Class clazz = this.getClass();
			String simpleName = clazz.getSimpleName();
			buffer.append(simpleName + "{");
			//
			Field[] fs = clazz.getDeclaredFields();
			Class ftype = null;
			String fname = null;
			Object fvalue = null;
			for(Field f : fs){
				ftype = f.getType();
				fname = f.getName();
				//保证私有的属性可以访问
				f.setAccessible(true);
				fvalue = f.get(this);
				//是否是基本数据类型
				if(ftype.isPrimitive()
					|| ftype == Integer.class
					|| ftype == Long.class
					|| ftype == Short.class
					|| ftype == Boolean.class
					|| ftype == Character.class
					|| ftype == Double.class
					|| ftype == Float.class
					|| ftype == String.class
					//不要静态属性
					&& !Modifier.isStatic(f.getModifiers())){
					buffer.append(fname + ":" + fvalue + ",");
				}
			}
			buffer.delete(buffer.length()-1, buffer.length());
			buffer.append("}");
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
