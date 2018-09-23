<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>STUDYSIBA:로그인</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- PAGE ICON -->
<link rel="shortcut icon" href="../img/favicon-SS.ico">
<link rel="icon" href="../img/favicon-SS.png">
<!-- CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link href="../css/logincustom.css?ver3" rel="stylesheet">
<!-- SCRIPT -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

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
	%>

<script type="text/javascript">

// 아이디 칸 입력시 유저 아이디를 실시간 체크해서 중복여부 확인
function check(){
 window.userID = document.getElementById("userID");
 window.lb1 = document.getElementById("lb1");
  $.ajax({
    type: "POST",
    url:"../UserCheck",
    data: { checkID : encodeURIComponent(userID.value)
    },
    success : function(result) {
      console.log(result);
      if ( result == 1) {
        lb1.innerHTML = "이미 등록 된 아이디 입니다.";
        $("#lb1").css("color","red");
      } else {
        if(userID.value==""){
        lb1.innerHTML = "";
        $("#lb1").css("color","#1d4f9a");
        } else {
        lb1.innerHTML = "사용 가능한 아이디 입니다";
        $("#lb1").css("color","#1d4f9a");
        }
      }
    }
  });
}


// 사용하지 않음 - 중복 되지 않은 아이디 경우 사용아이콘 입력시 아이디 입력 text 칸 disable 설정
function idconfirm(){
  if(confirm("아이디를 사용하시겠습니까?")){
    text1.disabled=true;
    lb1.innerHTML = "";
  } else {
    userID.focus();
  }
}
</script>



	<form method="post" action="../MemberJoinAction.do">

		<div id="login_warp">
			<div id="id_label">
				<div id="lb1">
					<label id="lb1_text"></label>
				</div>
			</div>
			<div id="login_content">

				<div id="login_logo">
					<a href="../MainForm.do"> <img src="../img/login_logo.png">
					</a>
				</div>

				<div id="join_input">
					<input type="text" id="userID" name="userID" maxlength="64" autocomplete="off" autocapitalize="off" placeholder="아이디 또는 이메일" tabindex="1" required> <input type="password" id="userPassword" name="userPassword" maxlength="16" autocomplete="off" autocapitalize="off" placeholder="비밀번호" tabindex="2" required> <input type="text" id="userName" name="userName" maxlength="16" autocomplete="off" autocapitalize="off" placeholder="이름" tabindex="2" required> <input type="text" id="userEmail" name="userEmail" maxlength="30" autocomplete="off" autocapitalize="off" placeholder="EXAMPLE@DOMAIN.COM" tabindex="2" required>

					<div class="btn-group" id="btn_gender" data-toggle="buttons">
						<label class="btn btn-primary active"> <input type="radio" name="userGender" autocomplete="off" value="남자" checked>남자
						</label> <label class="btn btn-primary"> <input type="radio" name="userGender" autocomplete="off" value="여자">여자
						</label>
					</div>
				</div>

				<button id="button_login" type="submit">회원가입</button>
			</div>
		</div>


<!-- 아이디 입력 text 칸에 키를 입력시 아이디 체크 함수를 실행한다. -->
<script type="text/javascript">
document.getElementById("userID").onkeyup = function() {
  check();
};
</script>

	</form>
</body>

</html>