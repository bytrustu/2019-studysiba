package com.bytrustu.member.action;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.chat.model.ChatDAO;
import com.bytrustu.member.model.MemberDAO;
import com.bytrustu.member.model.MemberDTO;

@WebServlet("/MemberSnsJoinAciton")

public class MemberSnsJoinAction extends HttpServlet {
  
  private static final long serialVersionUID = 1L;
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    MemberDAO dao = MemberDAO.getInstance();
    MemberDTO dto = new MemberDTO();
    
    String userID = request.getParameter("userID");
    String userPassword = "1234";
    String userName = request.getParameter("userName");
    String userGender = "남자";
    String userEmail = request.getParameter("userEmail");
    String userImage = request.getParameter("imagePath");
    
    userID = URLDecoder.decode(userID, "UTF-8");
    userName = URLDecoder.decode(userName, "UTF-8");
    userEmail = URLDecoder.decode(userEmail, "UTF-8");
    userImage = URLDecoder.decode(userImage, "UTF-8");
    
    dto.setUserID(userID);
    dto.setUserPassword(userPassword);
    dto.setUserName(userName);
    dto.setUserGender(userGender);
    dto.setUserEmail(userEmail);
    
    if (dao.memberIDCheck(userID) == 1) { // 아이디 중복 체크
      response.getWriter().write("0");
    } else {
      if (dao.memberJoin(dto) == 1) { // 중복 된 아이디가 없을시 가입 성공
        response.getWriter().write("1");
      }
    }
  }
}
