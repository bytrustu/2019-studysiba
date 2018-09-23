<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



</head>
<body style="position:relative;min-height:100%;">
<div class='bytrustu' style="margin-top:5px;">
 <div id="top-pagename" style="margin:0px 0 6px 0; "><a id='studySIBA' href='MainForm.do' ></a>
 <div id='logininfo'>
 
 		<% if ( session.getAttribute("userID") != null ) { %>			<!-- 		로그인 정보가 있을경우 메뉴바 우측아래에 접속중이 userID를 표시		 -->
		<img src = "img/siba-user.png" width="25px" height="25px"> 
		</div>
	<% } %>
 
 </div>
 </div>
  


