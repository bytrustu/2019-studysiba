<%@page import="com.bytrustu.member.model.MemberDTO"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

	<%
	  MemberDTO dto = (MemberDTO) request.getAttribute("dto");
	  
	  String userID = null;
	  if (session.getAttribute("userID") != null) {
	    userID = (String) session.getAttribute("userID");
	    request.setAttribute("userID", userID);
	  }
	%>

<div class="position-ab basic-location1">
    <span class="subpage-title">개인정보관리</span>
</div>



	<div class="container">
	<div class="subpage-margin"></div>
	<div class="col-lg-4"></div>
		<div class="col-lg-4">
			<div class="jumbotron" style="padding-top: 20px; background-color: white; border: 1px solid #dddddd;">
				<form method="post" action="MemberModifyAction.do">
					<h3 style="text-align: center;">회원정보수정</h3>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="아이디" name="userID" maxlength="20" value=<%=dto.getUserID()%> readOnly>
					</div>
					<div class="form-group">
						<input type="password" class="form-control" placeholder="비밀번호" name="userPassword" maxlength="20">
					</div>
					<div class="form-group">
						<input type="text" class="form-control" placeholder="이름" name="userName" maxlength="20" value=<%=dto.getUserName()%>>
					</div>
					<div class="form-group" style="text-align: center;">
						<div class="btn-group" data-toggle="buttons">


							<!-- 성별을 판별해서 라디오버튼 활성화 부분을 구분해준다. -->

							<%
							  if (dto.getUserGender().equals("남자")) {
							%>

							<label class="btn btn-primary active"> <input type="radio" name="userGender" autocomplete="off" value="남자" checked>남자
							</label> <label class="btn btn-primary"> <input type="radio" name="userGender" autocomplete="off" value="여자">여자
							</label>

							<%
							  } else {
							%>

							<label class="btn btn-primary"> <input type="radio" name="userGender" autocomplete="off" value="남자">남자	</label> 
							<label class="btn btn-primary active"> <input type="radio" name="userGender" autocomplete="off" value="여자" checked>여자</label>

							<%
							  }
							%>



						</div>
					</div>
					<div class="form-group">
						<input type="email" class="form-control" placeholder="이메일" name="userEmail" maxlength="20" value=<%=dto.getUserEmail()%>>
					</div>
					<a class="btn btn-primary form-control"><button>정보변경</button> </a>
				</form>
			</div>
		</div>
		<div class="col-lg-4"></div>

	</div>

