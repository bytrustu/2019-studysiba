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

@WebServlet("/ChatListAction")


// AJAX 메세지함 : 목록 데이터 조회


public class ChatListAction extends HttpServlet {

  private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    
    String userID = (String)request.getSession().getAttribute("userID");
    
    // userID 정보가 없을 경우 
    if ( userID == null || userID.equals("") ) {
      response.getWriter().write("0");
      
    // userID 가 정상적으로 있을 경우
    } else {
      try {
        // 한글처리를 위한 디코드
        userID = URLDecoder.decode(userID, "UTF-8");
        response.getWriter().write(getList(userID));  
      } catch ( Exception e ) {
        e.printStackTrace();
        response.getWriter().write("");
      }
    }
  }
  
  
  public String getList(String userID) {
    
    StringBuffer result = new StringBuffer("");
    result.append("{\"result\":[");
    ChatDAO dao = ChatDAO.getInstance();
    
    // 각 사용자 간에 나눈 메세지중 마지막 정보만 저장해서 리스트에 담을 데이터를 조회 한다.
    ArrayList<ChatDTO> list = dao.getChatList(userID);
    
    if (list.size() == 0) {
      return "";
    }
    for (int i = 0; i < list.size(); i++) {
      result.append("[{\"value\": \"" + list.get(i).getFromID() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getToID() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getChatContent() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getRead() + "\"},");
      result.append("{\"value\": \"" + list.get(i).getChatTime() + "\"}]");
      if (i != list.size() - 1) result.append(",");
    }
    result.append("], \"last\":\"" + list.get(list.size() - 1).getChatID() + "\"}");
    return result.toString();
  }
  
}
