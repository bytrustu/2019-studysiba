<%@page import="com.sun.xml.internal.txw2.Document"%>
<%@page import="com.bytrustu.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<form>

<input type="text" value="<%=request.getParameter("userID")%>">

<input type="button" value="중복체크" onclick="checkID()">


<%
String userID = request.getParameter("userID");
MemberDAO dao = MemberDAO.getInstance();
int result = dao.memberIDCheck(userID);

if ( result == 1) {
  
%>

<script type="text/javascript">
alert("사용중인 아이디 입니다.");
/* window.close();
opener.document.getElementById("text1").focus();  */
</script>
<%
} else {
 %>
<script type="text/javascript">
alert("사용가능한 아이디 입니다.");
/* window.close();
opener.document.getElementById("text1").focus();  */
</script>


<%
<input type="button" value="아이디선택" onclick="result()">

}
%>


</form>

</body>
</html>