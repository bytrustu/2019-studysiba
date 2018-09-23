package com.bytrustu.visit.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.SessionEvent;

import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.visit.model.VisitCountDAO;

//		web.xml 에 listener 정보를 명시해주고 서블릿이 생성될때 방문자 카운트 정보에 해당되는 부분을 넣어서 
//		사이트 접속시 방문자 date를 등록시켜주고 필요한 경우 totalCount 와 todayCount로 
//		나눠서 총방문자/오늘방문자 정보를 반환해준다.

public class VisitSessionListener implements HttpSessionListener {
  
  @Override
  public void sessionCreated(HttpSessionEvent sessionEve) {
    
    if (sessionEve.getSession().isNew()) {
      execute(sessionEve);
    }
  }
  
  private void execute(HttpSessionEvent sessionEve) {
    VisitCountDAO dao = VisitCountDAO.getInstance();
    
    try {
      dao.setTotalCount();
      int totalCount = dao.getTotalCount();
      int todayCount = dao.getTodayCount();
      
      HttpSession session = sessionEve.getSession();
      
      session.setAttribute("totalCount", totalCount);
      session.setAttribute("todayCount", todayCount);
      
    } catch (Exception e) {
      /* System.out.println("방문자 카운터 오류"); */
      e.printStackTrace();
    }
  }
  
  @Override
  public void sessionDestroyed(HttpSessionEvent sessionEve) {
   /* String userID = null;
    HttpSession session = sessionEve.getSession();
    System.out.println(">>>>>>>>>>>1");
    if (session.getAttribute("userID") != null) {
      System.out.println(">>>>>>>>>>>2");
      userID = (String)sessionEve.getSession().getAttribute("userID");
      MemberDAO memberDAO = MemberDAO.getInstance();
      memberDAO.updateLogoutConnected(userID);
    }*/
  }
}
