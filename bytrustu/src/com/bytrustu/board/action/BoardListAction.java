package com.bytrustu.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.board.model.BoardDTO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;


public class BoardListAction implements Action {

	public int pageNum;		// 현재 페이지 번호
	private int totalPage;		// 최대 페이지 수
	private int startPage;		// 페이지 리스트에서 시작 되는 페이지 번호
	private int endPage;		// 페이지 리스트에서 마무리 되는 페이지 번호
	private static final int COUNTLIST=10;		// 화면에 표시 될 게시글 갯수
	private static final int COUNTPAGE=3;		// 화면에 표시 될 페이지 번호 갯수
	
	BoardDAO dao = BoardDAO.getInstance();
	

  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	// 게시판 리스트 뷰 처리
    request.setCharacterEncoding("UTF-8");
    ActionForward forward = new ActionForward();
    
    
    // 페이지 번호 값을 받았을 경우 
    if ( request.getParameter("pageNum") != null ){		
    	pageNum = Integer.parseInt(request.getParameter("pageNum"));
    // 페이지 번호 값을 받지 못했을경우 default 페이지번호 1
    } else {
    	pageNum = 1;
    }
    ArrayList<BoardDTO> dtos = dao.boardGetList(pageNum,COUNTLIST);
    setPageInfo();
   /* System.out.println(">>>>>>>>>"+ pageNum +" "+ totalPage +" "+ startPage +" "+ endPage );*/
    
    if( dtos != null ) {
      forward.setRedirect(false);
      forward.setNextPath("BoardListForm.bo");
      request.setAttribute("dtos", dtos);
      request.setAttribute("pageNum", new Integer(pageNum));
      request.setAttribute("totalPage", new Integer(totalPage));
      request.setAttribute("startPage", new Integer(startPage));
      request.setAttribute("endPage", new Integer(endPage));
      /*System.out.println(">>>>>>>>>>>boardListAction 처리완료");*/
    } else {
     /* System.out.println("BoardAllListAction 오류");*/
    }
    return forward;
  }
	
	public void setPageInfo(){		// 총 게시글 갯수로 총페이지,시작페이지,끝페이지 구하기
		int totalCount = getTotalCount();
		totalPage = totalCount / COUNTLIST;
		if( totalCount % COUNTLIST > 0 ) {
			totalPage++;
		}
		
		if( totalPage < pageNum ) {
			pageNum = totalPage;
		}
		
		startPage = ((pageNum-1) / COUNTPAGE) * COUNTPAGE +1;
		endPage = startPage + COUNTPAGE -1;
		
		if ( endPage > totalPage ) {
			endPage = totalPage;
		}
	}
	
  
	public int getTotalCount(){		// 총 게시글 갯수
		int totalCount = dao.getContentCount();
		return totalCount;
	}
  
	
  
  

}
