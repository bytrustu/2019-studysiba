package com.bytrustu.study.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.study.model.StudyDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class StudyContentWrite implements Action {
  
  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    ActionForward forward = new ActionForward();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    MultipartRequest multi = null;
    
    String userID = (String) request.getSession().getAttribute("userID");
    
    int fileMaxSize = 10 * 1024 * 1024;
    String savePath = "C:/upload/study";
    try {
      multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
      
    } catch (Exception e) {
      e.printStackTrace();
      request.getSession().setAttribute("msgSession", "파일 크기는 10MB를 넘길수 없습니다.");
      forward.setRedirect(true);
      forward.setNextPath("StudyListAction.st");
      return forward;
    }
    
    String fileName = "";
    File file = multi.getFile("studyImage");
    if (file != null) {
      String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
      if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
        fileName = file.getName();
      } else {
        if (file.exists()) {
          file.delete();
        }
        request.getSession().setAttribute("msgSession", "이미지 파일만 업로드 가능 합니다.");
        forward.setRedirect(true);
        forward.setNextPath("StudyListAction.st");
        return forward;
      }
    }
    /* MemberDAO.getInstance().updateFile(userID, fileName); */
    forward.setRedirect(true);
    forward.setNextPath("StudyListAction.st");
    
    StudyDAO studyDAO = StudyDAO.getInstance();
    
    // lat lng 각각 나누기
    String mapData = multi.getParameter("mapData").substring(1, multi.getParameter("mapData").length() - 1);
    String[] array = mapData.split(", ");
    String lat = array[0].toString();
    String lng = array[1].toString();
    String groupName = multi.getParameter("studyName");
    System.out.println();
    String divide = multi.getParameter("studyDivide");
    String title = multi.getParameter("studyTitle");
    String content = multi.getParameter("studyContent");
    int person = Integer.parseInt(multi.getParameter("studyPerson"));
    String area = multi.getParameter("studyArea");
    String address = multi.getParameter("studyAdress");
    String period = multi.getParameter("studyDate");
    String stime = multi.getParameter("studyTime");
    
    
    int result = 0;
    // 스터디 글 작성
    result = studyDAO.writeStudyContent(userID, groupName, divide, title, content, lat, lng, area, address, period, stime, person, fileName);
    
    if (result == 1) {
      // 스터디 그룹생성
      int gNum = 0;
      gNum = studyDAO.getGroupNum(groupName, userID);
      studyDAO.makeGroup(gNum, groupName, userID);
      forward.setRedirect(true);
      forward.setNextPath("StudyListAction.st");
      request.getSession().setAttribute("msgSession", groupName + " 스터디가 개설 되었습니다.");
    } else {
      forward.setRedirect(true);
      forward.setNextPath("StudyListAction.st");
      request.getSession().setAttribute("msgSession", "등록에 실패했습니다.");
    }
    
    return forward;
  }
}
