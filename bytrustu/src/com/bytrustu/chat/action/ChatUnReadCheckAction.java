package com.bytrustu.chat.action;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.chat.model.ChatDAO;

@WebServlet("/ChatUnReadCheckAction")


//AJAX 메세지함 : 메세지 등록


public class ChatUnReadCheckAction extends HttpServlet {

  private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
  
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    
    String userID = (String)request.getSession().getAttribute("userID");
    if ( userID == null || userID.equals("")) {
      response.getWriter().write("0");
    } else {
      userID = URLDecoder.decode(userID, "UTF-8");
      // 읽지 않는 메세지갯수 조회
      response.getWriter().write(ChatDAO.getInstance().getUnReadChat(userID) + "");
    }
  }
}
