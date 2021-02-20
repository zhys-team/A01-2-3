package com.zhys.utils;

import org.dozer.DozerBeanMapper;

public class POJOConvertUtil {
	
	public static DozerBeanMapper getConvertMapper(){
		
		DozerBeanMapper mapper = new DozerBeanMapper();
		return mapper;
	}

}
