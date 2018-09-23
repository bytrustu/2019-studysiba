package com.bytrustu.chat.action;


import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bytrustu.member.model.MemberDAO;

@WebServlet("/ChatUserCheckAction")

public class ChatUserCheckAction extends HttpServlet {

  private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
   
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html; charset=UTF-8");
    
    
    String userID = request.getParameter("userID");
    // 유저 아이디를 체크해서 유저 프로필 사진 파일 조회
    response.getWriter().write(MemberDAO.getInstance().memberIDCheck(userID) + "," + MemberDAO.getInstance().getFile(userID) + "");
    
    
    
  }
}
