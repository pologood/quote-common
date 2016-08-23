package com.baidu.stock.material.exception;

public class ServiceException extends Exception {

	private static final long serialVersionUID = -8000674463607904267L;
	
	public ServiceException(Exception e){
		super(e) ;
	}
	
	
	public ServiceException(String msg){
		super(msg) ;
	}

}
