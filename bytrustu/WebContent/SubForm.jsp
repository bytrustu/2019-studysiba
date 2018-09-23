<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<!-- PAGEICON -->
<link rel="shortcut icon" href="img/favicon-SS.ico">
<link rel="icon" href="img/favicon-SS.png">
<!-- css -->
<link href="css/subform.css?ver5" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/sub-f2p.css" media="screen" />
<link rel="stylesheet" href="css/bootstrap.css">
<link href="css/chatcustom.css?ver3" rel="stylesheet">
<!-- font -->
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Do+Hyeon" rel="stylesheet">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
<!-- script -->
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<title>Insert title here</title>
</head>
<body>


<!-- 
	<script type="text/javascript">
$(function() {
  $(window).scroll(function() { //스크롤하면 아래 코드 실행
      var num = $(this).scrollTop(); // 스크롤값
      if (num > 50) { // 스크롤을 36이상 했을 때
          $("#sub-flexheader").css("position", "fixed");
          $("#sub-flexheader").css("top", "0");
          $("#sub-flexheader").css("z-index", "999");
      } else if (num == 0) {
          $("#sub-flexheader").css("position", "absolute");
          $("#sub-flexheader").css("top", "50");
      } else {
          $("#sub-flexheader").css("position", "absolute");
      }
  });
});

</script>
 -->

	<!-- 서브페이지-전체 -->
	<div id="sub-warp">


		<!-- 서브페이지-헤더 -->
		<div id="sub-header">
			<div id="sub-topheader"></div>
			<div id="sub-flexheader">
				<jsp:include page="Header.jsp" />
			</div>
		</div>


		<!-- 서브페이지-메인 -->
		<div id="sub-maincontainer">
			<div id="sub-box">   
				<!-- 서브페이지-메인-왼쪽사이드 -->
				<div id="sub-leftside"></div>




				<!-- 서브페이지-메인-중앙컨텐츠 -->
				<div id="sub-centercontent">

					<%-- <jsp:include page="board/BoardListAction.jsp" /> --%>

					<c:set var="contentPage" value="${param.contentPage}" />
					<c:choose>
						<c:when test="${contentPage==null}">
						</c:when>
						<c:otherwise>
							<jsp:include page="${param.contentPage}" />
						</c:otherwise>
					</c:choose>

				</div>




				<!-- 서브페이지-메인-오른쪽사이드 -->
				<div id="sub-rightside">
					<jsp:include page="SubRightSideForm.jsp" />
				</div>

			</div>
		</div>



		<div class="clear"></div>


		<!-- 서브페이지-하단푸터 -->
		<div id="sub-footer">
			<jsp:include page="Footer.jsp" />
		</div>


	</div>


</body>
</html>