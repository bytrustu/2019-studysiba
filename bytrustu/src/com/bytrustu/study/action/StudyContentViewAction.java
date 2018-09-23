package com.bytrustu.study.action;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.study.model.StudyDAO;
import com.bytrustu.study.model.StudyDTO;


public class StudyContentViewAction implements Action {

  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
	// 스터디게시판 게시글(Content) 뷰처리
    ActionForward forward = new ActionForward();
    StudyDAO studyDAO = StudyDAO.getInstance();
    MemberDAO  memberDAO = MemberDAO.getInstance();
    StudyDTO content = new StudyDTO();

    int contentNum = Integer.parseInt(request.getParameter("num"));
    content = studyDAO.studyGetListById(contentNum);
    String userFileName = memberDAO.getFile(content.getUserID());
    if( userFileName == null ) {
      userFileName = "siba-default.gif";
    }
    String userID = (String)request.getSession().getAttribute("userID");
    
    
    // 그룹에 가입 되어있는지 확인 여부
    int checkMember = studyDAO.checkMember(content.getgNum(), userID);
    
    // 그룹에 가입된 멤버들의 아이디와 프로필 사진
    int gNum = studyDAO.getGroupNum(content.getGroupName(), content.getUserID());
    ArrayList<String> memberList = new ArrayList<String>();
    ArrayList<String> transList = new ArrayList<String>();
    memberList = studyDAO.getMemberFile(gNum);
    int memberCount = studyDAO.StudyGetGroupMemberCount(gNum);
    
    for(int i=0; i<memberList.size()/2; i++){
      String userStr = memberList.get(i*2);
      String fileName = memberList.get(i*2+1);
      if ( userStr.length() > 7 ) {
        userStr = userStr.substring(0, 8)+"...";
      }
      if ( fileName == null ) {
        fileName = "siba-default.gif";
      }
      transList.add(userStr);
      transList.add(fileName);
    }
    
    request.setAttribute("content", content);
    request.setAttribute("checkMember", checkMember);
    request.setAttribute("userFileName", userFileName);
    request.setAttribute("memberList", transList);
    request.setAttribute("memberCount", memberCount);
    forward.setNextPath("StudyContentForm.st");
    forward.setRedirect(false);
    
    return forward;
  }
}