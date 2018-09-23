<%@page import="com.bytrustu.member.model.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>

<%
  String userID = null;
  if (session.getAttribute("userID") != null) {
    userID = (String) session.getAttribute("userID");
    request.setAttribute("userID", userID);
  }
%>
<div class="position-ab basic-location1">
	<span class="subpage-title">개인정보관리</span>
</div>


<div class="subpage-margin"></div>

<div class="container" style="float: left;">
	<div class="col-lg-4"></div>
	<div class="col-lg-4">
		<div class="jumbotron" style="padding-top: 20px; background-color: white; border: 1px solid #dddddd;">
			<form method="post" action="MemberDeleteAction.do">
				<h3 style="text-align: center;">회원탈퇴</h3>
				<div class="form-group">
					<input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20">
				</div>
				<div class="form-group" style="text-align: center;">
					<div class="btn-group" data-toggle="buttons"></div>
				</div>
				<button class="btn btn-primary form-control" onclick="javascript:return confirm('정말 탈퇴 하시겠습니까?')">탈퇴</button>
			</form>
		</div>
	</div>
	<div class="col-lg-4"></div>

</div>


