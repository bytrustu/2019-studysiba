<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%
  PrintWriter script = response.getWriter();
  String userID = null;
  if (session.getAttribute("userID") != null) {
    userID = (String) session.getAttribute("userID");
    request.setAttribute("userID", userID);
  } else {
    script.println("<script>");
    script.println("alert('로그인정보가 없습니다.')");
    script.println("location.href='member/LoginForm.jsp'");
    script.println("</script>");
  }
%>


<div class="position-ab basic-location1">
	<span class="subpage-title">자유게시판</span>
</div>


<div class="container" style="width: 90%;">
	<div class="subpage-margin"></div>
	<div class="row">

		<form method="post" action="BoardContentWriteAction.bo">

			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">글작성</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><input type="text" class="form-control" placeholder="글 제목" name="bTitle" maxlength="50"></td>
					</tr>
					<tr>
						<td><textarea class="form-control" placeholder="글 내용" name="bContent" maxlength="2048" style="height: 350px;"></textarea></td>
					</tr>
				</tbody>
			</table>
			<a class="btn btn-primary pull-right"><button>저장</button></a>
		</form>
	</div>
</div>




