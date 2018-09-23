<%@page import="com.bytrustu.member.model.MemberDAO"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>


<style type="text/css">
body {
		font-family: 'Nanum Gothic', sans-serif;
}

.btn-default {
background-color: #eeeeee;
}
</style>



<%
  String userID = null;
			MemberDAO dao = MemberDAO.getInstance();
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

<script type="text/javascript">

	// ChatList 를 JSON 으로 가져온다.
	function chatListFunction() {
		var userID = '<%=userID%>';
		$.ajax({
			type : "POST",
			url : "ChatListAction",
			data : {
				userID : encodeURIComponent(userID),
			},
			success : function(data) {
				if (data == "") {
					return;
				}
				$('#listTable').html('');
				var parsed = JSON.parse(data);
				var result = parsed.result;
				for (var i = 0; i < result.length; i++) {
					if (result[i][0].value == userID) {
						result[i][0].value = result[i][1].value;
					} else {
						result[i][1].value = result[i][0].value;
					}
					addList(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value, result[i][4].value);
				}
			}
		});
	}

	// #listTable에 해당 html을 추가
	function addList(lastID, toID, chatContent, read, chatTime) {
		var userID = '<%=userID%>';
		if (read == 2) {
			$('#listTable').append('<tr onclick="location.href=\'ChatInForm2.ch?toID=' + encodeURIComponent(toID) + '\'">' +
				'<td style="width: 200px;"><h5> ' + lastID + '</h5></td>' +
				'<td>' +
				'<div class="pull-left" style="margin-left : 40px;"><h5><i class="far fa-envelope" style="margin-right: 20px;"></i>' + chatContent + '<img src="img/list-today.png" style="margin-left : 15px";></h5></div>' +
				'<div class="pull-right">' + chatTime + '</div>' +
				'</td>' +
				'</tr>');
		} else {
			$('#listTable').append('<tr onclick="location.href=\'ChatInForm2.ch?toID=' + encodeURIComponent(toID) + '\'">' +
				'<td style="width: 150px;"><h5>' + lastID + '</h5></td>' +
				'<td>' +
				'<div class="pull-left" style="margin-left : 40px;"><h5><i class="far fa-envelope-open" style="margin-right: 20px;"></i>' + chatContent + '</h5></div>' +
				'<div class="pull-right">' + chatTime + '</div>' +
				'</td>' +
				'</tr>');
		}

	}

	// 3초 지연시간
	function getInfiniteList() {
		setInterval(function() {
			chatListFunction();
		}, 3000);
	}


    // 친구 찾기 기능
	function findFunction() {
		var userID = $('#findID').val();
		$.ajax({
			type : "POST",
			url : 'ChatUserCheckAction',
			data : {
				userID : userID
			},
			success : function(result) {

				var value = result.split(',');

				if (value[0] == 1) {
					alert('친구찾기에 성공했습니다.');
					getFriend(userID, value[1]);
				} else {
					alert('친구찾기에 실패했습니다.');
					failFriend();
				}
			}
		});
	}
    
    // 친구를 찾았을 경우 #friendResult에 해당 html 추가
	function getFriend(findID, fileName) {
		if (fileName == 'null') {
			fileName = 'siba-default.gif';
		}

		$('#friendResult').html('<thead>' +
			'<tr>' +
			'<th style="width: 448px;"><div style="margin-left: 10px;"><h4>검색결과</h4></div></th>' +
			'</tr>' +
			'</thead>' +
			'<tbody>' +
			'<tr>' +
			'<td style="text-align: center;"><h3><img class="img-circle" src="/local_img/'
			+ fileName +
			'" width="50px" height="50px" style="margin-right: 20px;">' + findID + '</h3><a href="ChatInForm2.ch?toID=' + encodeURIComponent(findID) + '" class="btn btn-default pull-right">' + '메세지 보내기</a></td>' +
			'</tr>' +
			'</tbody>');
	}
    
    // 친구찾기를 실패한 경우 html 요소를 없애준다.
	function failFriend() {
		$('#friendResult').html('');
	}
</script>


<title>Insert title here</title>
</head>
<body>

	<div class="position-ab basic-location1">
		<span class="subpage-title">메세지함</span>
	</div>


	<div>

		<div class="subpage-margin"></div>

		<div class="container" style="width: 45%; float: left;">

			<table class="table" style="margin: 0 auto;">
				<thead>
					<tr>
						<th><i class="fas fa-spin fa-redo-alt" style="margin-right: 10px;"></i>메세지 목록</th>
					</tr>
				</thead>
				<div style="overflow-y: auto; width: 100%; max-height: 450px;">
					<table class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd; margin: 0 auto;">
						<tbody id="listTable">

						</tbody>
					</table>
				</div>
			</table>

		</div>


		<div class="container" style="width: 40%; float: left; margin-left: 5%;">
			<table class="table table-hover" style="margin: 0 auto;">
				<thead>
					<tr>
						<th colspan="2"><i class="far fa-address-card" style="margin-right: 10px;"></i>친구 찾기</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="width: 50px;"><div">
								<h5>아이디</h5>
							</div></td>
						<td><input class="form-control" type="text" id="findID" maxlength="20" placeholder="찾을 아이디를 입력하세요." style="width: 100%; vertical-align: middle;"></td>
					</tr>
					<tr>
						<td colspan="2"><button class="btn btn-default pull-right" onclick="findFunction();">검색</button></td>
					</tr>
				</tbody>
			</table>

			<div class="container" style="width: 100%; margin-left: -20px; margin-top: 30px;">
				<table id="friendResult" class="table table-bordered table-hover" style="text-align: center; border: 1px solid #dddddd;">
				</table>
			</div>

		</div>


	</div>


    <!-- chatList 항목을 Interval 시간을 통해 계속해서 갱신해준다. -->
	<script type="text/javascript">
		$(document).ready(function() {
			chatListFunction();
			getInfiniteList();
		});
	</script>


</body>
</html>