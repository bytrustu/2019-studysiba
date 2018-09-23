<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
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

<%-- <%

ArrayList<String> list = new ArrayList<String>();
list = MemberDAO.getInstance().getConnectedUser();


for (int i=0; i<list.size(); i++){
out.println(list.get(i));
}
%> --%>

<%
ArrayList<String> list = new ArrayList<String>();
list.add("a1");
list.add("b2");
list.add("c3");
list.add("d4");

int num = list.size();

String [] test= new String[list.size()];

for ( int i =0; i<test.length; i++){
    test[i] = list.get(i).toString();
    out.println(test[i]);
}

%>


</body>
</html>