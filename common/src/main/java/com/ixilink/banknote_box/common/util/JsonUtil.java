package com.ixilink.banknote_box.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author 张皓峰
 * 2018年7月6日
 */
public class JsonUtil {

	/**
	 * 功能描述：对象转json
	 * 〈将对象转为json字符串〉
	 * @param o 转换对象
	 * date: 2018年7月6日
	 * @return: json字符串
	 */
	public static String obj2str(Object o) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	/**
	 * 将集合转换成json字符串
	 * 2018年7月6日
	 * @param o list集合
	 * @return json字符串
	 */
	public static <T> String list2str(List<T> o) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		try {
			jsonStr = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonStr;
	}

	public static String listToString(List list, String separator) {
		StringBuilder sb = new StringBuilder();
		for (Object aList : list) {
			sb.append(aList).append(separator);
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}
	 /**
    * 字符串转对象
    * 2018年7月6日
    * @param json
    * @param classes
    * @return
    */
	public static <T> Object str2Obj(String json,Class<T> classes) {
		ObjectMapper mapper = new ObjectMapper();
		T obj = null;
		try {
			obj = mapper.readValue(json, classes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

    
    
    /**
     * 字符串转自定义类型（list、map等等）
     * 2018年7月6日
     * @param jsonStr 需解析的json字符串
     * @param collectionClass 外层类型
     * @param elementClasses 内层类型
     * @return <T> 自定义类型的值
     */
    public static <T> T jackson2other(String jsonStr, Class<?> collectionClass, Class<?>... elementClasses) {
        ObjectMapper mapper = new ObjectMapper();

        JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
		T o = null;
		try {
			o = mapper.readValue(jsonStr, javaType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return o;
	}

}
