package com.bytrustu.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.common.action.Action;
import com.bytrustu.common.action.ActionForward;

public class MemberDeleteAction implements Action {
  
  @Override
  public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    // 회원탈퇴 , 정보삭제 처리 메뉴
    
    MemberDAO dao = MemberDAO.getInstance();
    ActionForward forward = new ActionForward();
    String userID = (String) request.getSession().getAttribute("userID");
    String userPassword = request.getParameter("userPassword");
    
    /*
     * System.out.println(">>>>>>>>" + userID); System.out.println(">>>>>>>>" + userPassword);
     */
    
    if (userPassword != "") { // 빈칸체크 --1
      
      if (dao.memberIDPWChecker(userID, userPassword) == 1) { // 비밀번호 체킹 --2
        
        if (dao.memberDelete(userID) == 1) { // 삭제처리 완료시 메세지 --3
          forward.setRedirect(true);
          forward.setNextPath("MainForm.do");
          request.getSession().removeAttribute("userID");
          request.getSession().setAttribute("msgSession", "정상적으로 회원 탈퇴 되었습니다.");
          
        } else { // 데이터베이스 오류 --3
          forward.setRedirect(true);
          forward.setNextPath("MainForm.do");
          request.getSession().setAttribute("msgSession", "비정상적인 오류 입니다.");
        }
        
      } else { // 비밀번호가 틀릴경우 --2
        forward.setRedirect(true);
        forward.setNextPath("DeleteForm.do");
        request.getSession().setAttribute("msgSession", "비밀번호가 틀렸습니다.");
      }
      
    } else { // 빈칸이 있는 경우 --1
      forward.setRedirect(true);
      forward.setNextPath("DeleteForm.do");
      request.getSession().setAttribute("msgSession", "비밀번호를 입력해 주세요.");
    }
    
    return forward;
  }
  
}
