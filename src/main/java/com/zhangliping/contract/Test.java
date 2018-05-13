package com.zhangliping.contract;

import java.lang.reflect.Field;
import java.util.List;

import com.google.common.collect.Lists;
import com.zhangliping.baseUtil.JsonUtil;


public class Test {
	final static String defaultPackage = "com.zhangliping.contract.";
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws ClassNotFoundException {
		
		List<PageSelectDto> pageSelectDtos = ajaxPost("ContractDto");
		
		System.out.println(JsonUtil.toString(pageSelectDtos));
		
	}
	public static List<PageSelectDto> ajaxPost(String templateClass) throws ClassNotFoundException {
		Class<?> clazz = Class.forName(defaultPackage + templateClass);
		
		
		Field[] field = clazz.getDeclaredFields();
		List<PageSelectDto> pageSelectDtos = Lists.newArrayList();
		PageSelectDto pageSelectDto = null;
		for (Field field2 : field) {
			if (field2.isAnnotationPresent(ContractAnnotation.class)) {
				ContractAnnotation contractAnnotation = field2.getAnnotation(ContractAnnotation.class);
				pageSelectDto = new PageSelectDto();
				pageSelectDto.setKey(field2.getName());
				pageSelectDto.setValue(contractAnnotation.value());
				pageSelectDto.setMaxLength(contractAnnotation.maxLength());
				pageSelectDtos.add(pageSelectDto);
			}
		}
		return pageSelectDtos;
	}

}
