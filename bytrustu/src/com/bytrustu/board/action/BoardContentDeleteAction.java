package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class BoardContentDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 게시판 게시글(Content) 삭제 처리 , update - bAvailable 삭제시 0으로 조정
		ActionForward forward = new ActionForward();
		BoardDAO dao = BoardDAO.getInstance();
		/*System.out.println(">>>>>>>>>BoardContentDeleteAction 접근");*/
		
		int bNum = Integer.parseInt(request.getParameter("bNum"));
		
		/*System.out.println("bNum 값 확인 >>>>>>>>>>>>"+bNum);*/
		
		if ( dao.boardContentDelete(bNum) == 1 ){		// 글 삭제처리 업데이트시
			System.out.println(">>>>>>>>>>>>>>>글수정완료");
			forward.setRedirect(true);
			forward.setNextPath("BoardListAction.bo");
		} /*else {
			System.out.println(">>>>>>>>>>>>>>>글수정실패 DB오류");
		}*/
		return forward;
	}
}
