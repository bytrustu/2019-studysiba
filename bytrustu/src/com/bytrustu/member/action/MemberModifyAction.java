package com.bytrustu.member.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class MemberModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		// 회원정보 수정 처리
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		MemberDAO dao = MemberDAO.getInstance();
		
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userName = request.getParameter("userName");
		String userGender = request.getParameter("userGender");
		String userEmail = request.getParameter("userEmail");
		
		
		if (userID != "" && userPassword != "" && userName != "" && userGender != "" && userEmail != "") {
			// 빈칸체크

			if (dao.memberIDPWChecker(userID, userPassword) == 1) { // 회원수정시 비밀번호 확인

				if (dao.memberUpdate(userID, userName, userGender, userEmail) == 1) { // 회원정보 수정처리

					/*System.out.println("정보수정완료");*/
					forward.setRedirect(true);
					forward.setNextPath("MainForm.do");
					request.getSession().setAttribute("msgSession", "정상적으로 회원정보다 수정되었습니다.");

				} else { // 데이터베이스 오류
					/*System.out.println("데이터베이스 오류");*/
					forward.setRedirect(true);
					forward.setNextPath("MainForm.do");
					request.getSession().setAttribute("msgSession", "비정상적인 오류 입니다.");
				}

			} else { // 비번체크 틀릴경우

				/*System.out.println("비번틀림");*/
				forward.setRedirect(true);
				forward.setNextPath("MemberModifyViewAction.do");
				request.getSession().setAttribute("msgSession", "비밀번호가 틀렸습니다.");

			}
			
		} else {		// 입력정보 다시 체크
			
		/*	System.out.println("미등록정보재확인");*/
			forward.setRedirect(true);
			forward.setNextPath("MemberModifyViewAction.do");
			request.getSession().setAttribute("msgSession", "회원정보를 모두 입력해 주세요.");
			
		}

		return forward;
	}

}
