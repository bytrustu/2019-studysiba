<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">


<!-- PAGEICON -->
<link rel="shortcut icon" href="img/favicon-SS.ico">
<link rel="icon" href="img/favicon-SS.png">
<!-- CSS -->
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="css/sub-f2p.css?ver3">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
<link rel="stylesheet" href="css/maincustom.css?ver4">
<!-- SCRIPT -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<!-- FONT -->
<link href="https://fonts.googleapis.com/css?family=Do+Hyeon" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Permanent+Marker" rel="stylesheet">




<script>


	$(function() {
		$(window).scroll(function() { 
			var num = $(this).scrollTop();
			if ( num > 36 ) {
			  
			  
			 /*  $(".menu").css({
			  position:fixed,
			  top:0,
			  background-color:white,
			  border-bottom:none}
			  ); */
			  
				$(".menu").css("position", "fixed");
				$(".menu").css("top", "0");
				$(".menu").css("background-color","white");
				$(".menu").css("border-bottom","none");
				$(".itext").css("color","#4c4c4c");
				$("#nav-menubar").removeClass("navbar-inverse");
				$("#nav-menubar").addClass("navbar");
				
			} else if (num == 0) {
				$(".menu").css("position", "absolute");
				$(".menu").css("top", "50");
				$(".menu").css("background-color","transparent");
				$(".itext").css("color","#9d9d9d");
				$("#nav-menubar").removeClass("navbar");
			    $("#nav-menubar").addClass("navbar-inverse");
			} else {
				$(".menu").css("position", "absolute");
			}
		});
	});


</script>
<title>bytrustu.com</title>
</head>
<body>

	<div id="wrap">


		<div class="header">
			<div id="logo">
				<jsp:include page="TopHeader.jsp" />
			</div>
		</div>
		<!--			헤더(상단)부분			-->


		<div id="containerMain">


			<div id="main">
				<div class="menu">
					<jsp:include page="Header.jsp" />
				</div>

				<!-- 		contentPage 값이 없을경우 FirstView.jsp 를 참조 / 이외의 값은 contentPage 값 참조		 -->
				<c:set var="contentPage" value="${param.contentPage}" />
				<c:choose>
					<c:when test="${contentPage==null}">
						<jsp:include page="FirstView.jsp" />
					</c:when>
					<c:otherwise>
						<jsp:include page="${contentPage}" />
					</c:otherwise>
				</c:choose>

			</div>

		</div>

		<!--			푸터(하단)부분			-->
		<div id="footer">
			<jsp:include page="Footer.jsp" />
		</div>


	</div>


</body>
</html>