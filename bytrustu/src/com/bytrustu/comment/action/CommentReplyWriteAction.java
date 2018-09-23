package com.bytrustu.comment.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.comment.model.CommentDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class CommentReplyWriteAction implements Action {

  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    CommentDAO dao = CommentDAO.getInstance();
    
   /* System.out.println("commentReplyWirte 접근");*/
    int bNum = Integer.parseInt(request.getParameter("bNum"));
    String cId = (String)request.getSession().getAttribute("userID");
    String cContent = request.getParameter("cContent");
    int cGroup = Integer.parseInt(request.getParameter("cGroup"));
    int cStep = Integer.parseInt(request.getParameter("cStep"));
    int cIndent = Integer.parseInt(request.getParameter("cIndent"));
    String precId = request.getParameter("precId");
    
    if ( dao.commentReplyWrite(bNum, cId, cContent, cGroup, cStep, cIndent, precId) == 1 ){
  /*    System.out.println("commentReplyWirte 완료");*/
    } else {
     /* System.out.println("commentReplyWirte 실패");*/
    }
    
    return null;
  }

}
