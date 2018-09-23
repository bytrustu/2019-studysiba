<%@page import="java.util.ArrayList"%>
<%@page import="com.bytrustu.member.model.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.PrintWriter"%>


<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>

<style type="text/css">
.thead-inverse th {
  color: #fff;
  background-color: #212529;
}
</style>



<!-- ADMIN 회원정보 PAGE -->



	<%		// userID 가 admin 인지 체크
		String userID = null;
		userID = (String) session.getAttribute("userID");
		if (!userID.equals("admin")) {
	%>
	<script>
		alert("잘못된 접근 입니다.");
		history.back();
	</script>
	<%
		}
	%>
	
	
	<br>
	<div class = "container">
		<div class="form-group row pull-right">
			<div class="col-xs-8">
				<input class="form-control" type="text" size="30">
			</div>
			<div>
				<button class="btn btn-danger" type="button" >검색</button>
			</div>
		</div>
		<table class="table" style="text-align: center; border: 1px solid #dddddd">
			<thead class="thead-inverse">
				<tr>
					<th style="text-align: center;">아디</th>
					<th style="text-align: center;">이름</th>
					<th style="text-align: center;">성별</th>
					<th style="text-align: center;">이메일</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${dtos}" var="dtos">
				<tr>
					<td>${dtos.userID}</td>
					<td>${dtos.userName}</td>
					<td>${dtos.userGender}</td>
					<td>${dtos.userEmail}</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		
	</div>
	

	


