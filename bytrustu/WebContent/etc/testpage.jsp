<%@page import="com.bytrustu.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>



<script type="text/javascript">
function check(){
 window.text1 = document.getElementById("text1");
 window.text2 = document.getElementById("text2");
 window.lb1 = document.getElementById("lb1");
  $.ajax({
    type: "POST",
    url:"UserCheck",
    data: { checkID : encodeURIComponent(text1.value)
    },
    success : function(result) {
      if ( result == 1) {
        lb1.innerHTML = "이미 등록 된 아이디 입니다.";
        $("#btn").css("visibility","hidden");
      } else {
        lb1.innerHTML = "사용 가능한 아이디 입니다..";
        $("#btn").css("visibility","visible");
      }
    }
  });
}

function idconfirm(){
  if(confirm("아이디를 사용하시겠습니까?")){
    text1.disabled=true;
    lb1.innerHTML = "";
  } else {
    text1.focus();
  }
  
}
</script>


<input type="text" id="text1">
<input type="button" id="btn" onclick="idconfirm()" value="사용" style="visibility: hidden;">
<br>
<label id="lb1"></label>


<script type="text/javascript">
document.getElementById("text1").onkeyup = function() {
  check();
};
</script>



</body>
</html>