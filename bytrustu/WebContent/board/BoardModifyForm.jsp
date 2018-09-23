<%@page import="com.bytrustu.board.model.BoardDTO"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.bytrustu.member.model.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


	<%
	String userID = null;
    BoardDTO dto = (BoardDTO)request.getAttribute("dto");
	if (session.getAttribute("userID") != null) {
    userID = (String) session.getAttribute("userID");
	request.setAttribute("userID", userID);
	} else {
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("alert('로그인정보가 없습니다.')");
	script.println("location.href='member/LoginForm.jsp'");
	script.println("</script>");
	}
	
	%>
	
	
	<div class="position-ab basic-location1">
    <span class="subpage-title">자유게시판</span>
</div>

	
	<div class ="container" style="width: 90%;">
	<div class="subpage-margin"></div>
	<div class="row">
		
		<form method="post" action="BoardContentModifyAction.bo?bNum=<%=dto.getbNum() %>">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd;">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;"> 수정 작성 </th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" class="form-control" placeholder="글 제목" name="bTitle" maxlength="50" value="<%=dto.getbTitle() %>"></td>
					</tr>
					<tr>
						<td><textarea class="form-control" placeholder="글 내용" name="bContent" maxlength="2048" style="height: 350px;"><%=dto.getbContent() %></textarea></td>
					</tr>
				</tbody>
			</table>
			<a class="btn btn-primary pull-right" ><button>글수정</button></a>
			</form>
		</div>
	</div>
	






