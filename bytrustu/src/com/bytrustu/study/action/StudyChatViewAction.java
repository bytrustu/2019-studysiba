package com.bytrustu.study.action;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.chat.model.ChatDAO;
import com.bytrustu.chat.model.ChatDTO;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.study.model.StudyChatDAO;
import com.bytrustu.study.model.StudyChatDTO;

@WebServlet("/StudyChatViewAction")

public class StudyChatViewAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    int chatNum = Integer.parseInt(request.getParameter("gNum"));
    String listType = request.getParameter("listType");
    
    if (chatNum == 0 || listType == null || listType.equals("")) {
      response.getWriter().write("0");
    } else if (listType.equals("view")) {
      response.getWriter().write(getChat(chatNum));
    } else {
      response.getWriter().write(getChat(chatNum));
    }
  }
  
  public String getChat(int chatNum) {
    
    StringBuffer result = new StringBuffer("");
    result.append("{\"result\":[");
    StudyChatDAO dao = StudyChatDAO.getInstance();
    ArrayList<StudyChatDTO> list = dao.getChatListByID(chatNum);
    if (list.size() == 0) {
      return "";
    }
    for (int i = 0; i < list.size(); i++) {
      String fileName = MemberDAO.getInstance().getFile(list.get(i).getUserID());
      result.append("[{\"value\": \"" + list.get(i).getUserID() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getChatContent() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getChatTime() + "\"},");
      result.append("{\"value\": \"" + fileName + "\"}]");
      if (i != list.size() - 1) result.append(",");
    }
    result.append("], \"last\":\"" + list.get(list.size() - 1).getChatID() + "\"}");
    
    return result.toString();
  }
  
}
