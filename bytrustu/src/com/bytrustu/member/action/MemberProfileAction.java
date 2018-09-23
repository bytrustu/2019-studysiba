package com.bytrustu.member.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.member.model.MemberDAO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class MemberProfileAction implements Action {
  
  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    ActionForward forward = new ActionForward();
    MultipartRequest multi = null;
    int fileMaxSize = 10 * 1024 * 1024;
    String savePath = "C:/upload/";
    try {
      multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
      
    } catch (Exception e) {
      e.printStackTrace();
      request.getSession().setAttribute("msgSession", "파일 크기는 10MB를 넘길수 없습니다.");
      forward.setRedirect(true);
      forward.setNextPath("ProfileForm.do");
      return forward;
    }
    String userID = multi.getParameter("userID");
    if (!userID.equals((String) request.getSession().getAttribute("userID"))) {
      forward.setRedirect(true);
      forward.setNextPath("ProfileForm.do");
      return forward;
    }
    
    String fileName = "";
    File file = multi.getFile("userProfile");
    if (file != null) {
      String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
      if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
        String prev = MemberDAO.getInstance().getFile(userID);
        File prevFile = new File(savePath + "/" + prev);
        if (prevFile.exists()) {
          prevFile.delete();
        }
        fileName = file.getName();
      } else {
        if (file.exists()) {
          file.delete();
        }
        request.getSession().setAttribute("msgSession", "이미지 파일만 업로드 가능 합니다.");
        forward.setRedirect(true);
        forward.setNextPath("ProfileForm.do");
        return forward;
      }
    }
    MemberDAO.getInstance().updateFile(userID, fileName);
    forward.setRedirect(true);
    forward.setNextPath("ProfileForm.do");
    request.getSession().setAttribute("msgSession", "프로필 등록이 완료 되었습니다.");
    System.out.println(">>>>>>파일 등록 완료");
    return forward;
  }
}