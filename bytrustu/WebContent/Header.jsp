<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html >


<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>


<style type="text/css">
#nav-menubar{
width: 1920px;
}

</style>


<%
  String userID = null;
			if (session.getAttribute("userID") != null) { // session 에서 userID 체크해서 로그인 정보 확인
				userID = (String) session.getAttribute("userID");
				request.setAttribute("userID", userID);
			} else { // 접속정보가 없으면 로그인 화면으로 이동
				response.sendRedirect("member/LoginForm.jsp");
			}
			
			
    // 메세지 알림 세션 체크
            if (session.getAttribute("msgSession") != null) {
%>



<script type="text/javascript">
    alert('<%=session.getAttribute("msgSession")%>');
</script>
<%
    session.removeAttribute("msgSession");
            }
%>


<script type="text/javascript">
	function getUnread() {
		$.ajax({
			type : "POST",
			url : "ChatUnReadCheckAction",
			data : {
				userID : encodeURIComponent('<%=userID%>'),
			},
			success : function(result) {
				if (result >= 1) {
					showUnread(result);
				} else {
					showUnread('');
				}
			}
		});
	}
	function getInfiniteUnread() {
		setInterval(function() {
			getUnread();
		}, 4000);
	}
	function showUnread(result) {
		$('#unread').html(result);
	}



	$(function($) {
		$("#header-msg").hover(
			function() {
				$('i', this).addClass("fas fa-envelope-open");
			},

			function() {
				$('i', this).removeClass("fas fa-envelope-open");
				$('i', this).addClass("fas fa-envelope");
			});
	});

	
    /*  접속로그 시간 갱신 	 */
	function updateConnectInfo() {
        $.ajax({
            type : "POST",
            url : "MemberLastDateAction",
            success : function() {
            }
        });
    }

    function getInfiniteLastDate() {
        setInterval(function() {
            updateConnectInfo();
        }, 30000);
    }
</script>

<title>상단부분</title>
</head>
<body>




	<nav class="navbar-inverse" id="nav-menubar" style="background-color: transparent;">


		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="MainForm.do" style="margin-left: 100px;"><img id="logoimage" style="transition: all 1s;"></a> <a class="navbar-brand" href="MainForm.do" style="padding: 26px 15px 26px 0px; font-size: 1.5rem; margin-right: 10px;"> <b>스터디</b><b style="color: #F07171">시바</b>
			</a>
		</div>
		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav" id="menu-nav">
				<li><a href="MainForm.do" style="padding: 26px 25px; font-size: 1.8rem;"><i class="fa fa-home fa-lg itext"></i></a></li>
				<li><a href="BoardListAction.bo" style="padding: 26px 25px; font-size: 1.8rem;"><i class="fa fa-edit fa-lg itext"></i></a></li>
				<li id="header-msg"><a href="ChatForm.ch" style="padding: 26px 25px; font-size: 2.0rem;"><i class="fas fa-envelope itext"></i><span id="unread" class="label label-danger" style="position: absolute; right: 5px;"></span></a></li>
                <li><a href="StudyListAction.st" style="padding: 26px 25px; font-size: 1.7em;"><i class="fas fa-book itext"></i></a></li>
                <li><a href="StudyGroupListAction.st" style="padding: 26px 25px; font-size: 1.7em;"><i class="fas fa-user-plus itext"></i></a></li>
			</ul>

			<!-- 로그인 판별여부에 따라 메뉴바 출력부분 조정 -->

			<%
			  // 세션에 userID 가 null 인 경우 표시 사이트 이용에 필요한 로그인과 회원가입 메뉴만 표시
						if (userID == null) {
			%>


			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expaneded="false" style="padding: 26px 50px 25px 25px; font-size: 1.5rem;"><i class="fa fa-cog fa-spin fa-lg fa-fw itext"></i><span class="caret"></span></a>

					<ul class="dropdown-menu">
						<li><a href="member/LoginForm.jsp">로그인</a></li>
						<li><a href="member/JoinForm.jsp">회원가입</a></li>

					</ul></li>
			</ul>


			<%
			  // 세션에 userID가 admin 인 경우, 로그인 사용자의 기본적인 회원정보 메뉴에 추가해서 admin 아이디만 사용할수 있는 관리자 메뉴 추가
						} else if (userID.equals("admin")) {
			%>


			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expaneded="false" style="padding: 26px 50px 25px 25px; font-size: 1.5rem;"> <i class="fa fa-cog fa-spin fa-lg fa-fw"></i> <span class="sr-only"></span> <span class="caret"></span></a>

					<ul class="dropdown-menu">
					   <li><a href="ProfileForm.do">프로필수정</a></li>
						<li><a href="MemberModifyViewAction.do">정보변경</a></li>
						<li><a href="MemberLogoutAction.do" onclick="javascript:return confirm('로그아웃 하시겠습니까?')">로그아웃</a></li>
						<li><a href="DeleteForm.do">회원탈퇴</a></li>
					</ul></li>
			</ul>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expaneded="false" style="padding: 30px 25px; font-size: 1.5rem;"><i class="fa fa-star	 fa-spin fa-lg fa-fw"></i><span class="caret"></span></a>

					<ul class="dropdown-menu">
						<li><a href="MemberAllListAction.do">회원전체목록</a></li>
					</ul></li>
			</ul>


			<%
			  // 세션에 userID가 있는 경우 표시 로그인중 상태 , 회원정보 부분의 메뉴만 표시
						} else {
			%>

			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expaneded="false" style="padding: 26px 25px; font-size: 1.5rem;"><i class="fa fa-cog fa-spin fa-lg fa-fw"></i><span class="caret"></span></a>

					<ul class="dropdown-menu">
				    	<li><a href="ProfileForm.do">프로필수정</a></li>
						<li><a href="MemberModifyViewAction.do">정보수정</a></li>
						<li><a href="MemberLogoutAction.do" onclick="javascript:return confirm('로그아웃 하시겠습니까?')">로그아웃</a></li>
						<li><a href="DeleteForm.do">회원탈퇴</a></li>
					</ul></li>
			</ul>

			<%
			  }
			%>


		</div>
	</nav>



    <%
        if (session.getAttribute("userID") != null) {
    %>

	<script type="text/javascript">
		$(document).ready(function() {
			getUnread();
			getInfiniteUnread();
			updateConnectInfo();
		    getInfiniteLastDate();
		});
	</script>

    <%
        }
    %>



