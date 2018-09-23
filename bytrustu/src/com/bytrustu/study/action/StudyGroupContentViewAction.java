package com.bytrustu.study.action;


import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.study.model.GroupFileDTO;
import com.bytrustu.study.model.StudyDAO;
import com.bytrustu.study.model.StudyDTO;


public class StudyGroupContentViewAction implements Action {

  
  
  public static int pageNum=1; // 현재 페이지 번호
  private int totalPage; // 최대 페이지 수
  private int startPage; // 페이지 리스트에서 시작 되는 페이지 번호
  private int endPage; // 페이지 리스트에서 마무리 되는 페이지 번호
  private static final int COUNTLIST = 3; // 화면에 표시 될 게시글 갯수
  private static final int COUNTPAGE = 2; // 화면에 표시 될 페이지 번호 갯수
  StudyDAO studyDAO = StudyDAO.getInstance();
  public static int gNum=0;
  
  
  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    ActionForward forward = new ActionForward();
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    
    MemberDAO  memberDAO = MemberDAO.getInstance();    
    StudyDTO content = new StudyDTO();

    gNum = Integer.parseInt(request.getParameter("gNum"));
    content = studyDAO.studyGetListBygNum(gNum);
    /*String userFileName = memberDAO.getFile(content.getUserID());
    if( userFileName == null ) {
      userFileName = "siba-default.gif";
    }*/
    ArrayList<HashMap<String,Object>> groupList = new ArrayList<HashMap<String,Object>>();
    groupList = studyDAO.StudyGetGroupContent(gNum);
    
    
    
    // 페이지 번호 값을 받았을 경우
    if (request.getParameter("pageNum") != null) {
      pageNum = Integer.parseInt(request.getParameter("pageNum"));
      // 페이지 번호 값을 받지 못했을경우 default 페이지번호 1
    } else {
      pageNum = 1;
    }
    ArrayList<GroupFileDTO> list = new ArrayList<GroupFileDTO>();
    list = studyDAO.StudyGetGroupFileList(pageNum, COUNTLIST, gNum);
    setPageInfo();
    
    
    if (list != null) {
      request.setAttribute("fileList", list);
      request.setAttribute("pageNum", new Integer(pageNum));
      request.setAttribute("totalPage", new Integer(totalPage));
      request.setAttribute("startPage", new Integer(startPage));
      request.setAttribute("endPage", new Integer(endPage));
    }
    
    System.out.println(pageNum);
    
    request.setAttribute("content", content);
    request.setAttribute("groupList", groupList);
    forward.setRedirect(false);
    forward.setNextPath("StudyGroupContentForm.st");
    return forward;
  }

  public void setPageInfo() { // 총 게시글 갯수로 총페이지,시작페이지,끝페이지 구하기
    int totalCount = getTotalCount();
    totalPage = totalCount / COUNTLIST;
    if (totalCount % COUNTLIST > 0) {
      totalPage++;
    }
    
    if (totalPage < pageNum) {
      pageNum = totalPage;
    }
    
    startPage = ((pageNum - 1) / COUNTPAGE) * COUNTPAGE + 1;
    endPage = startPage + COUNTPAGE - 1;
    
    if (endPage > totalPage) {
      endPage = totalPage;
    }
  }
  
  public int getTotalCount() { // 총 게시글 갯수
    int totalCount = studyDAO.getGroupFileCount(gNum);
    return totalCount;
  }
}