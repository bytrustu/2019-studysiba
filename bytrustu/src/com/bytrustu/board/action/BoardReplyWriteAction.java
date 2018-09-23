package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class BoardReplyWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		// 게시판 답변(Reply) 작성 처리
		ActionForward forward = new ActionForward();
		BoardDAO dao = BoardDAO.getInstance();
		/*System.out.println(">>>>>>>BoardReplyWriteAction 접근");*/
		
		String bId = (String)request.getSession().getAttribute("userID");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		int bGroup = Integer.parseInt(request.getParameter("bGroup"));
		int bStep = Integer.parseInt(request.getParameter("bStep"));
		int bIndent = Integer.parseInt(request.getParameter("bIndent"));
			
		if (dao.boardReplyWrite(bId, bTitle, bContent, bGroup, bStep, bIndent)==1){
			/*System.out.println(">>>>>>ReplyWrite 처리 완료");*/
			forward.setRedirect(false);
			forward.setNextPath("BoardListAction.bo");
		} else {
			/*System.out.println(">>>>>>ReplyWrite 처리 실패");*/
		}
		return forward;

	}
}
