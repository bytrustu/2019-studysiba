package com.bytrustu.visit.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.visit.model.VisitCountDAO;

@WebServlet("/SibaTotalCount")
public class SibaTotalCount extends HttpServlet {
 
  private static final long serialVersionUID = 1L;

  /*
   *  AJAX 접속자 , 글 등록수 1주일 통계 조회
   */
  
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    
    StringBuffer result = new StringBuffer("");
    result.append("{\"result\":[");
    
    VisitCountDAO dao = VisitCountDAO.getInstance();
    ArrayList<ArrayList<Object>> list = dao.getSibaCount();
    if (list.size() == 0) {
      return;
    }
    for (int i = 0; i < list.size(); i++) {
        result.append("[{\"value\": \"" + list.get(i).get(0) + "\"},");
        result.append("{\"value\": \"" + list.get(i).get(1) + "\"},");
        result.append("{\"value\": \"" + list.get(i).get(2) + "\"}]");
      if (i != list.size() - 1) result.append(",");
    }
    result.append("]}");
    
    response.getWriter().write(result.toString());
    
  }
}