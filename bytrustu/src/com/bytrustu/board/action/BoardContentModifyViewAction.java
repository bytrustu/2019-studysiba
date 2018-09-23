package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class BoardContentModifyViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		// 게시판 게시글(Content) 수정 뷰 처리
		ActionForward forward = new ActionForward();
		BoardDAO dao = BoardDAO.getInstance();
		/*System.out.println(">>>>>>>>>BoardContentModifyViewAction 접근");*/
		
		BoardDTO dto = null;
		int bNum = Integer.parseInt(request.getParameter("bNum"));
		
		/*System.out.println("bNum 값 확인 >>>>>>>>>>>>"+bNum );*/
		
		if ( dao.boardGetContent(bNum) != null ){		// 글 수정처리 뷰 표현
			
			dto = dao.boardGetContent(bNum);
			
			/*System.out.println(">>>>>>>>>>>>>>>글수정뷰접근");*/
			forward.setRedirect(false);
			forward.setNextPath("BoardModifyForm.bo");
			request.setAttribute("dto", dto);
			/*System.out.println(">>>>>>>>>>>>>>>>>>dto  "+dto.getbNum() );*/
		} else {
			forward.setRedirect(false);
			forward.setNextPath("BoardModifyForm.bo");
			/*System.out.println(">>>>>>>>>>>>>>>글수정뷰실패 DB오류");*/
		}
		return forward;
	}

}
