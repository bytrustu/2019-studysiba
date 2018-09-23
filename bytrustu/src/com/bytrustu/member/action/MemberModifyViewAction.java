package com.bytrustu.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.member.model.MemberDTO;

public class MemberModifyViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 회원정보 수정 메뉴 클릭시 뷰에 표시 될 해당 회원정보 뷰처리
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		MemberDAO dao = MemberDAO.getInstance();

		String userID = request.getSession().getAttribute("userID").toString();
		MemberDTO dto = dao.memberInfo(userID);

		
		forward.setRedirect(false);		// dispatcher를 통해 dto 정보를 담아 보냄
		forward.setNextPath("ModifyForm.do");
		request.setAttribute("dto", dto);
		
		return forward;
	}

}
