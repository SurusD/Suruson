package com.surus.github.service;

public interface ValueCallBack<T> {
	public T callBack(T parameter) throws Exception;
}
