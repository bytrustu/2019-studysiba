<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<style type="text/css">
body {
		font-family: 'Nanum Gothic', sans-serif;
}
</style>


<%
  String userID = null;
  if (session.getAttribute("userID") != null) {
    userID = (String) session.getAttribute("userID");
  } else {
    PrintWriter script = response.getWriter();
    script.println("<script>");
    script.println("alert('로그인 정보가 없습니다.');");
    script.println("location.href='member/LoginForm.jsp';");
    script.println("</script>");
  }
%>



<div class="position-ab basic-location1">
	<span class="subpage-title">개인정보관리</span>
</div>


<div class="subpage-margin"></div>

<form method="post" action="MemberProfileAction.do" enctype="multipart/form-data">
	<div>
		<div class="container" style="width: 45%; float: left; margin-left: 5%;">
			<table class="table" style="margin: 0 auto;">
				<thead>
					<tr>
						<th><i class="fas fa-file-image" style="margin-right: 10px;"></i>프로필 사진 수정</th>
					</tr>
				</thead>
				<div style="overflow-y: auto; width: 100%; max-height: 450px;">
					<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">

						<tbody>
							<tr>
								<td style="width: 110px;"><h5>아이디</h5></td>
								<td><h5><%=userID%></h5>
									<input type="hidden" name="userID" value="<%=userID%>"></td>
							<tr>
								<td style="width: 110px;"><h5>사진 업로드</h5></td>
								<td><span class="btn btn-default btn-file">이미지를 업로드하세요.<input type="file" name="userProfile"></span></td>
							</tr>
							<tr>
								<td colspan="2"><button type="submit" class="btn btn-primary pull-right">수정</button></td>
							</tr>

						</tbody>
					</table>
				</div>
			</table>

		</div>


	</div>