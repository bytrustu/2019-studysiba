package com.bytrustu.chat.action;

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

@WebServlet("/ChatViewAction")


//AJAX 메세지함 : 메세지 목록 조회



public class ChatViewAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    // 입력 된 정보 
    String fromID = request.getParameter("fromID");
    String toID = request.getParameter("toID");
    String listType = request.getParameter("listType");
    
    
    // 리스트 타입으로 조정하려 했으나..
    if (fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null || listType.equals("")) {
      response.getWriter().write("0");
    } else if (listType.equals("view")) {
      response.getWriter().write(getChat(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
    } else {
      response.getWriter().write(getChat(URLDecoder.decode(fromID, "UTF-8"), URLDecoder.decode(toID, "UTF-8")));
    }
  }
  
  public String getChat(String fromID, String toID) {
    StringBuffer result = new StringBuffer("");
    result.append("{\"result\":[");
    ChatDAO dao = ChatDAO.getInstance();
    ArrayList<ChatDTO> list = dao.getChatListByID(fromID, toID);
    if (list.size() == 0) {
      return "";
    }
    for (int i = 0; i < list.size(); i++) {
      String fileName = MemberDAO.getInstance().getFile(list.get(i).getFromID());
      result.append("[{\"value\": \"" + list.get(i).getFromID() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getToID() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getChatContent() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getChatTime() + "\"},");
      result.append("{\"value\": \"" + fileName + "\"}]");
      if (i != list.size() - 1) result.append(",");
    }
    result.append("], \"last\":\"" + list.get(list.size() - 1).getChatID() + "\"}");
    
    dao.chatRead(fromID, toID);
    return result.toString();
  }
  
}
