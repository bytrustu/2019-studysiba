package com.bytrustu.board.action;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.board.model.BoardDAO;

@WebServlet("/BoardContentLikeAction")
public class BoardContentLikeAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    
    BoardDAO dao = BoardDAO.getInstance();
    
    // 좋아요 처리
    String checkACtion = request.getParameter("checkACtion");
    String num = request.getParameter("contentNo");
    int contentNo = Integer.parseInt(num);
    String userID = request.getParameter("userID");
    
    if (checkACtion.equals("1")) {
      
      int result = dao.setContentLike(contentNo, userID);
      if (result == 1) {
        response.getWriter().write("1");
      }
    } else {
      int result = dao.setContentUnLike(contentNo, userID);
      if (result == 1) {
        response.getWriter().write("0");
      }
    }
    
  }
}