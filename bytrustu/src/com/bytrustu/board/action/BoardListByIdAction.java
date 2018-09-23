package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;


public class BoardListByIdAction implements Action {

	

  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	// 게시판 리스트 뷰 처리
    request.setCharacterEncoding("UTF-8");
    ActionForward forward = new ActionForward();
    BoardDAO dao = BoardDAO.getInstance();
    String bId = null;
    
    
    if ( request.getParameter("bId") != null || request.getParameter("bId").equals("") ) {
      bId = request.getParameter("bId");
    }
    
    ArrayList<BoardDTO> dtos = dao.boardGetListById(bId);
    
    if( dtos != null ) {
     /* System.out.println(dtos.get(0).getbContent());*/
      forward.setRedirect(false);
      forward.setNextPath("BoardListByIdForm.bo");
      request.setAttribute("dtos", dtos);
      /*System.out.println(">>>>>>>>>>>boardListByIdAction 처리완료");*/
    } else {
      System.out.println("boardListByIdAction 오류");
    }
    return forward;
  }
	
  
  

}
