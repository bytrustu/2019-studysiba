package com.bytrustu.study.action;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.study.model.StudyChatDAO;


@WebServlet("/StudyChatSubmitAction")

public class StudyChatSubmitAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    
    
    String userID = request.getParameter("userID");
    String chatContent = request.getParameter("chatContent");
    int chatNum = Integer.parseInt(request.getParameter("gNum"));
    
    
    
    if (userID == null || userID.equals("") || chatContent == null || chatContent.equals("") || chatNum == 0) {
      response.getWriter().write("0");
    } else {
      userID = URLDecoder.decode(userID, "UTF-8");
      chatContent = URLDecoder.decode(chatContent, "UTF-8");
      response.getWriter().write(StudyChatDAO.getInstance().writeChat(userID, chatContent, chatNum) + "");
    }
    
  }
  
}
