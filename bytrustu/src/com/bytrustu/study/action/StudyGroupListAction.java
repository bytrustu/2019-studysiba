package com.bytrustu.study.action;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.study.model.StudyDAO;

public class StudyGroupListAction implements Action {
  
  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    ActionForward forward = new ActionForward();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    
    String userID = null;
    if ( request.getSession().getAttribute("userID") != null) {
      userID = (String)request.getSession().getAttribute("userID");
    } else {
      request.getSession().setAttribute("msgSession", "로그인 정보가 없습니다.");
      forward.setRedirect(false);
      forward.setNextPath("MainForm.do");
      return forward;
    }
    StudyDAO studyDAO = StudyDAO.getInstance();
    ArrayList<HashMap<String,Object>> groupList = new ArrayList<HashMap<String,Object>>();
    groupList = studyDAO.StudyGetGroupList(userID);
    
    request.setAttribute("groupList", groupList);
    forward.setRedirect(false);
    forward.setNextPath("StudyGroupForm.st");
    return forward;
  }
}
