package com.bytrustu.common.action;

public class ActionForward {

	// isRedirect : false 일 경우 RequestDispatcher , true 일 경우 sendRedirect 사용
	private boolean isRedirect = false;
	// nextPath : 다음 이동 될 경로 명
	private String nextPath = null;
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	public String getNextPath() {
		return nextPath;
	}
	public void setNextPath(String nextPath) {
		this.nextPath = nextPath;
	}
}
