package com.bytrustu.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 인터페이스 정의
public interface Action {
		public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception ;
}