package com.bytrustu.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.member.model.MemberDAO;

public class MemberLogoutAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그아웃 메뉴
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();

		MemberDAO memberDAO = MemberDAO.getInstance();
    memberDAO.updateLogoutConnected((String)request.getSession().getAttribute("userID"));
    
		// userID 세션을 삭제한다.
		request.getSession().removeAttribute("userID");
		
		
		
		// 로그아웃시 메인페이지로 돌아간다.
		forward.setRedirect(true);
		forward.setNextPath("MainForm.do");
		request.getSession().setAttribute("msgSession", "정상적으로 로그아웃 되었습니다.");
		return forward;
	}

	
	
}
