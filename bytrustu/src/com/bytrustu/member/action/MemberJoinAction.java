package com.bytrustu.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.member.model.MemberDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class MemberJoinAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 회원가입 메뉴
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		MemberDAO dao = MemberDAO.getInstance();
		MemberDTO dto = new MemberDTO();
		
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		
		dto.setUserID(userID);
		dto.setUserPassword(userPassword);
		dto.setUserName(userName);
		dto.setUserGender(userGender);
		dto.setUserEmail(userEmail);
		
		if (userID != "" && userPassword != "" && userName != "" && userGender != ""
				&& userEmail != "") { 				// 빈칸 체크
			
			if (dao.memberIDCheck(userID) == 1) { // 아이디 중복 체크
			  request.getSession().setAttribute("msgSession", "이미 등록 된 아이디 입니다");
				forward.setRedirect(true);
				forward.setNextPath("MainForm.do");
				
			} else {
				if (dao.memberJoin(dto) == 1) { // 중복 된 아이디가 없을시 가입 성공
					forward.setRedirect(true);
					request.getSession().setAttribute("msgSession", "회원가입을 축하합니다.");
					forward.setNextPath("MainForm.do");
					request.getSession().setAttribute("userID", dto.getUserID());
					
				} else {
				  request.getSession().setAttribute("msgSession", "비정상적인 오류 입니다."); // 데이터베이스 오류
					forward.setRedirect(true);
					forward.setNextPath("MainForm.do");
				}
			}
			
		} else {
		  forward.setNextPath("MainForm.do");
			request.getSession().setAttribute("msgSession", "정보를 모두 입력 해주세 요."); // 빈칸이 있을시 메세지
			forward.setRedirect(true);
		}
		return forward;
	}
	
}
