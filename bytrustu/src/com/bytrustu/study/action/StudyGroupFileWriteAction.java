package com.bytrustu.study.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.study.model.StudyChatDAO;
import com.bytrustu.study.model.StudyDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class StudyGroupFileWriteAction implements Action {
  
  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    ActionForward forward = new ActionForward();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    MultipartRequest multi = null;
    
    String userID = (String) request.getSession().getAttribute("userID");
    
    int fileMaxSize = 10 * 1024 * 1024;
    String savePath = "C:/upload/group";
    int gNum = 0;
    try {
      multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
      gNum = Integer.parseInt(multi.getParameter("gNum"));
    } catch (Exception e) {
      e.printStackTrace();
      request.getSession().setAttribute("msgSession", "파일 크기는 10MB를 넘길수 없습니다.");
      forward.setRedirect(true);
      forward.setNextPath("StudyGroupContentViewAction.st?gNum="+gNum);
      return forward;
    }
    
    File gFile = multi.getFile("gFile");
    String fileName = "";
    fileName = gFile.getName();
    /* MemberDAO.getInstance().updateFile(userID, fileName); */
    
    StudyDAO studyDAO = StudyDAO.getInstance();
    String content = multi.getParameter("groupContent");
    
    
    int result = 0;
    // 스터디 글 작성
    result = studyDAO.writeGroupFile(gNum, userID, content, fileName);
    
    if (result == 1) {
      forward.setRedirect(false);
      forward.setNextPath("StudyGroupContentViewAction.st?gNum="+gNum);
      request.getSession().setAttribute("msgSession", fileName + "  파일이 등록 되었습니다.");
      StudyChatDAO studyChatDAO = StudyChatDAO.getInstance();
      studyChatDAO.writeChat(userID, fileName+" 파일이 등록 되었습니다.", gNum);
    } else {
      forward.setRedirect(true);
      forward.setNextPath("StudyGroupContentViewAction.st?gNum="+gNum);
      request.getSession().setAttribute("msgSession", "등록에 실패했습니다.");
    }
    
    return forward;
  }
}
