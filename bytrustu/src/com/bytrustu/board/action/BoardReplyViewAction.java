package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class BoardReplyViewAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 게시판 답변(Reply) 뷰 처리
		ActionForward forward = new ActionForward();
		BoardDAO dao = BoardDAO.getInstance();
		/*System.out.println(">>>>>>>BoardReplyViewAction 접근");*/
		
		int bNum = Integer.parseInt(request.getParameter("bNum"));
		BoardDTO dto = null;
		
		if( dao.boardGetContent(bNum) != null ) {
			dto = dao.boardGetContent(bNum);
			request.setAttribute("dto", dto);
			/*System.out.println(">>>>>>BoardReplyViewAction 처리 완료");*/
			forward.setRedirect(false);
			forward.setNextPath("BoardReplyWriteForm.bo");
		} else {
			/*System.out.println(">>>>>>BoardReplyViewAction 처리 실패");*/
		}
		return forward;

	}
}
