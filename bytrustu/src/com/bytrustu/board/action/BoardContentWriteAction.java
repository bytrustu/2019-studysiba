package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class BoardContentWriteAction implements Action {

  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	// 게시판 게시글(Content) 작성 처리
    ActionForward forward = new ActionForward();
    BoardDAO dao = BoardDAO.getInstance();
   /* System.out.println(">>>>>>>BoardContentWrite 접근");*/
    
    String bId = (String) request.getSession().getAttribute("userID");
    String bTitle = request.getParameter("bTitle");
    String bContent = request.getParameter("bContent");
   /* System.out.println(">>>>>>>>title : " + bTitle + ">>>>>>>>content : " + bContent);*/
    
    if ( dao.boardContentWirte(bId, bTitle, bContent) == 1 ) {
      /*System.out.println(">>>>>>>BoardContentWrite 완료");*/
      request.setAttribute("bNum", dao.getNext());
     //System.out.println("마지막페이지숫자>>>>>>>>>>>"+dao.getNext());
      forward.setRedirect(false);
      forward.setNextPath("BoardContentViewAction.bo");
    } else {
    /*  System.out.println(">>>>>>>BoardContentWrite 실패");*/
      
    }
    return forward;
  }
  
  
}