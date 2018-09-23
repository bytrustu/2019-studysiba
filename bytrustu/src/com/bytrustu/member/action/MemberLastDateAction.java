package com.bytrustu.member.action;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bytrustu.member.model.MemberDAO;

@WebServlet("/MemberLastDateAction")
public class MemberLastDateAction extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 사용자 정보를 모두 뷰 , 관리자 메뉴에 이용

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        MemberDAO dao = MemberDAO.getInstance();
        dao.updateLastDate((String) request.getSession().getAttribute("userID"));
    }
}