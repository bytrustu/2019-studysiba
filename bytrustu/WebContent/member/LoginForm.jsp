<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>STUDYSIBA:로그인</title>
<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="../css/logincustom.css?ver6" rel="stylesheet">
<link rel="shortcut icon" href="../img/favicon-SS.ico">
<link rel="icon" href="../img/favicon-SS.png">
</head>
<body>


	<%
	  if (session.getAttribute("userID") != null) {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('이미 로그인 정보가 있습니다.');");
					script.println("location.href='../MainForm.do';");
					script.println("</script>");
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
/* 구글 연동
*/

function gooCheckLoginStatus() {
  if (gauth.isSignedIn.get()) {
    window.profile = gauth.currentUser.get().getBasicProfile();
  }
}

function init() {
  console.log('init');
  gapi.load('auth2', function() {
    window.gauth = gapi.auth2.init({
      client_id : '654531449937-1kb2cto1mbs1oq8ju4b2lcsmjtd05436.apps.googleusercontent.com'
    });
    gauth.then(function() {
      console.log("loginsucceess");
    })
    gauth.then(function() {
      console.log('googleAuth success');
      gooCheckLoginStatus();
    }, function() {
      console.log('googleAuth failed');
    });
  });
}

function googleJoin() {
  var userID = profile.getEmail();
  var userName = profile.getName();
  var userEmail = profile.getEmail();
  var imagePath = profile.getImageUrl();

  $.ajax({
    type : "POST",
    url : "../MemberSnsJoinAciton",
    data : {
      userID : encodeURIComponent(userID),
      userName : encodeURIComponent(userName),
      userEmail : encodeURIComponent(userEmail),
      imagePath : encodeURIComponent(imagePath),
    },
    success : function(result) {

      if (result == 1) {
        $("form").attr("method", "POST").attr("action", "../MemberSnsLoginAction.do?userID=" + userID).attr("target", "_parent").submit();
      }
      if (result == 0) {
        $("form").attr("method", "POST").attr("action", "../MemberSnsLoginAction.do?userID=" + userID).attr("target", "_parent").submit();
      }

    }
  });
}




/*  페이스북 연동
*/
function statusChangeCallback(response) {
  console.log('statusChangeCallback');
  console.log(response);
  if (response.status === 'connected') {
    testAPI();
  } else {
    document.getElementById('status').innerHTML = 'Please log ' +
      'into this app.';
  }
}

function checkLoginState() {
  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
}

var checkLoginStatus = function(response) {
  console.log(response);
  if (response.status === 'connected') {
    FB.api('/me', {
      fields : 'name,email'
    }, function(response) {
      window.userinfo = response;
      facebookJoin();
    });
  }
}

window.fbAsyncInit = function() {
  FB.init({
    appId : '489217858222608',
    cookie : true,
    xfbml : true,
    version : 'v3.1'
  });
};

(function(d, s, id) {
  var js,
    fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s);
  js.id = id;
  js.src = "https://connect.facebook.net/en_US/sdk.js";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

function testAPI() {
  console.log('Welcome!  Fetching your information.... ');
  FB.api('/me', function(response) {
    console.log('Successful login for: ' + response.name);
    document.getElementById('status').innerHTML = 'Thanks for logging in, ' + response.name + '!';
  });
}


function facebookJoin() {
  var userID = userinfo.email;
  var userName = userinfo.name;
  var userEmail = userinfo.email;
  var imagePath = null;

  $.ajax({
    type : "POST",
    url : "../MemberSnsJoinAciton",
    data : {
      userID : encodeURIComponent(userID),
      userName : encodeURIComponent(userName),
      userEmail : encodeURIComponent(userEmail),
      imagePath : encodeURIComponent(imagePath),
    },
    success : function(result) {

      if (result == 1) {
        $("form").attr("method", "POST").attr("action", "../MemberSnsLoginAction.do?userID=" + userID).attr("target", "_parent").submit();
      }
      if (result == 0) {
        $("form").attr("method", "POST").attr("action", "../MemberSnsLoginAction.do?userID=" + userID).attr("target", "_parent").submit();
      }

    }
  });

}

</script>


<script type="text/javascript">
function check(){
  var formData = $('#insertFrm').serialize();
  $.each($('form input[type=checkbox]') .filter(function(idx){ return $(this).prop('checked') === false }), function(idx, el){
  var emptyVal = ""; formData += '&' + $(el).attr('name') + '=' + emptyVal; } );
}

</script>



	<form method="post" id="insertFrm" action="../MemberLoginAction.do">


		<div id="login_warp">

			<div id="login_content">

				<div id="login_logo">
					<a href="../MainForm.do"> <img src="../img/login_logo.png">
					</a>
				</div>

				<div id="login_input">
					<input type="text" id="login_name" name="userID" maxlength="64" autocomplete="off" autocapitalize="off" placeholder="아이디 또는 이메일" tabindex="1" > <input type="password" id="password" name="userPassword" maxlength="16" autocomplete="off" autocapitalize="off" placeholder="비밀번호" tabindex="2" required>
				</div>

				<button id="button_login" type="submit">로그인</button>

				<div id="account_info">
					<p>
						<a href="#">계정 찾기</a>
					</p>
					<p>|</p>
					<p>
						<a href="#">비밀번호 찾기</a>
					</p>
					<p>|</p>
					<p>
						<a href="JoinForm.jsp">회원가입</a>
					</p>
				</div>

				<div class="clear"></div>


				<!-- 페이스북 로그인 버튼 -->
				<button type="button" class="button_box" id="button_facebook" onclick="
                               FB.login(function(response){
                                    checkLoginStatus(response);
                                     });
                                     facebookJoin();
                                      ">
					<a href="#">
						<div id="button_margin">
							<div id="fb_img">
								<img src="../img/fb.png">
							</div>
							<div id="fb_text">Facebook</div>
						</div>
					</a>
				</button>

				<div id="clear"></div>


				<!-- 구글 로그인 버튼 -->
				<button type="button" class="button_box2" id="button_google" onclick="
                        gauth.signIn().then(function(){
                        gooCheckLoginStatus();
                        googleJoin();
                      });
                        ">
					<a href="#">
						<div id="button_margin">
							<div id="gg_img">
								<img src="../img/gg.png">
							</div>
							<div id="gg_text">Google</div>
						</div>
					</a>
				</button>


			</div>
		</div>


	</form>
</body>

</html>