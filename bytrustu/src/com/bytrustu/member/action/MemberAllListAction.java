package com.bytrustu.member.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.member.model.MemberDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class MemberAllListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		// 사용자 정보를 모두 뷰 , 관리자 메뉴에 이용
		
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		MemberDAO dao = MemberDAO.getInstance();
		ArrayList<MemberDTO> dtos = dao.memberAllList();
		
		if (dao.memberAllList() != null ) {
			
			forward.setRedirect(false);
			forward.setNextPath("ListForm.do");
			request.setAttribute("dtos", dtos);
			
		} else {
			/*System.out.println("MemberAllListAction 오류");*/
		}
		
		return forward;
	}

	
	
}
