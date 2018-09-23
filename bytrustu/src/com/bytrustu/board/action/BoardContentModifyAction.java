package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class BoardContentModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		// 게시판 게시글(Content) 수정 처리
		ActionForward forward = new ActionForward();
		BoardDAO dao = BoardDAO.getInstance();
		
		int bNum = Integer.parseInt(request.getParameter("bNum"));
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		if ( dao.boardContentUpdate(bNum, bTitle, bContent) == 1 ){		// 글 수정처리 업데이트시
			forward.setRedirect(true);
			forward.setNextPath("BoardContentViewAction.bo?bNum="+bNum);
		}  else if(  bTitle != ""  && bContent != "" ) {
			/*System.out.println(">>>>>>>>>>>>>>>글수정빈칸");*/
		} else {
			/*System.out.println(">>>>>>>>>>>>>>>글수정실패 DB오류");*/

		}
		
		
		return forward;
	}

}
