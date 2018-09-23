package com.bytrustu.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.visit.model.VisitCountDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class MemberLoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 로그인 메뉴
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		MemberDAO dao = MemberDAO.getInstance();

		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		
		if (dao.memberIDPWChecker(userID, userPassword) == 1) {			 // 아이디 비번 체크
		    
		    dao.updateLastDate(userID);
		    dao.updateLastConnected(userID);
		    VisitCountDAO.getInstance().setMemberVisit(userID);
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("msgSession", "로그인 되었습니다.");
			forward.setRedirect(true);
			forward.setNextPath("MainForm.do");		
		} else {
			forward.setRedirect(true);
			request.getSession().setAttribute("msgSession", "아이디 혹은 비밀번호가 틀렸습니다.");
			forward.setNextPath("member/LoginForm.jsp");
		}
		return forward;
	}
}
