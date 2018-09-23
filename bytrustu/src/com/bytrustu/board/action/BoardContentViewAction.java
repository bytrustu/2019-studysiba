package com.bytrustu.board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.comment.model.CommentDAO;
import com.bytrustu.comment.model.CommentDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;


public class BoardContentViewAction implements Action {

  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	// 게시판 게시글(Content) 뷰처리
    ActionForward forward = new ActionForward();
    BoardDAO dao = BoardDAO.getInstance();
    CommentDAO commentdao = CommentDAO.getInstance();
    BoardDTO dto = null;
    ArrayList<CommentDTO> dtos = new ArrayList<CommentDTO>();
    
    int bNum;
    if( request.getAttribute("bNum") != null ) 
    	bNum = (int) request.getAttribute("bNum");
    else
    	bNum = Integer.parseInt(request.getParameter("bNum"));
    
    /*System.out.println("ContentView bNum>>>>>>"+bNum);*/
    
    if ( dao.boardGetContent(bNum) != null ) {
    	if( commentdao.getCommentList(bNum) != null ){
    		dtos = commentdao.getCommentList(bNum);
    		int commentCount = commentdao.getCommentCount(bNum);
    		request.setAttribute("commentList", dtos);
    		request.setAttribute("commentCount", commentCount);
    		
    		for(int i=0; i<dtos.size(); i++) {
    			/*System.out.println(dtos.get(i));*/
    		}
    		
    	} else {
    		/*System.out.println("commentList 값 null >>>>>>>>>>>>>");*/
    	}
      dao.boardContentCountUp(bNum);
      dtos = commentdao.getCommentList(bNum);
      
      /*System.out.println("count>>>>>>>>>>>"+dao.getCount(bNum));*/
      dto = dao.boardGetContent(bNum);
      /*System.out.println(">>>>>>>>>>> BoardContentViewAction 성공");*/
      /*System.out.println("bDate 값 확인 ★>>>>>>>>>>"+dto.getbDate());*/
      request.setAttribute("dto",dto);
      forward.setRedirect(false);
      forward.setNextPath("BoardContentForm.bo");
      
    } else {
      /*System.out.println(">>>>>>>>>>> BoardContentViewAction 실패");*/
    }
    return forward;
  }
}