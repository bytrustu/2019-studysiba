package com.bytrustu.chat.action;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.chat.model.ChatDAO;

@WebServlet("/ChatSubmitAction")


//AJAX 메세지함 : 메세지 등록


public class ChatSubmitAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    
    String fromID = request.getParameter("fromID");
    String toID = request.getParameter("toID");
    String chatContent = request.getParameter("chatContent");
    
    // 항목중에 null 값이 있을경우
    if (fromID == null || fromID.equals("") || toID == null || toID.equals("") || chatContent == null || chatContent.equals("")) {
      response.getWriter().write("0");
      
    // 보낸사람과 받는사람이 같을 경우
    } else if (fromID.equals(toID)) {
      response.getWriter().write("-1");
      
    // 정상적인 경우
    } else {
      // 한글처리를 위한 디코드
      fromID = URLDecoder.decode(fromID, "UTF-8");
      toID = URLDecoder.decode(toID, "UTF-8");
      chatContent = URLDecoder.decode(chatContent, "UTF-8");
      // 메세지 등록
      response.getWriter().write(ChatDAO.getInstance().writeChat(fromID, toID, chatContent) + "");
    }
    
  }
  
}
