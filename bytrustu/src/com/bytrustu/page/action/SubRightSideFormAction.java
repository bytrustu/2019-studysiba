package com.bytrustu.page.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.visit.model.VisitCountDAO;
import com.bytrustu.board.model.BoardDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class SubRightSideFormAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	  
		request.setCharacterEncoding("UTF-8");
		ActionForward forward = new ActionForward();
		MemberDAO memberDAO = MemberDAO.getInstance();
    BoardDAO boardDAO = BoardDAO.getInstance();
    VisitCountDAO visitCountDAO = VisitCountDAO.getInstance();
    HashMap<String, Object> subMap = new HashMap<>();
		
		String userID = null;
		if ( request.getSession().getAttribute("userID") != null ) {
		  userID = (String)request.getSession().getAttribute("userID");
		}
		
		String lastDate = memberDAO.getConnectDate(userID);
		String fileName = memberDAO.getFile(userID);
		int visitCount = visitCountDAO.getUserVisitCount(userID);
		int contentCount = boardDAO.getUserContentCount(userID);
		int replyCount = boardDAO.getUserReplyCount(userID);
		
		subMap.put("lastDate", lastDate);
		subMap.put("fileName", fileName);
		subMap.put("visitCount", visitCount);
		subMap.put("contentCount", contentCount);
		subMap.put("replyCount", replyCount);
		
		System.out.println(">>>>>>>");
		forward.setNextPath("SubRightSideForm.page");
		forward.setRedirect(false);
		
		return forward;
		
		
	}
	
}
