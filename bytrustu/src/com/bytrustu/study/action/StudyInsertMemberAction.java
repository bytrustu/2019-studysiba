package com.bytrustu.study.action;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.chat.model.ChatDAO;
import com.bytrustu.study.model.StudyDAO;

@WebServlet("/StudyInsertMemberAction")

public class StudyInsertMemberAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    StudyDAO studyDAO = StudyDAO.getInstance();
    int gNum = Integer.parseInt(request.getParameter("gNum"));
    String userID = (String) request.getSession().getAttribute("userID");
    int result = 0;
    result = studyDAO.insertMember(gNum, userID);
    if (result == 1) {
      response.getWriter().write("1");
    } else {
      response.getWriter().write("0");
    }
  }
}