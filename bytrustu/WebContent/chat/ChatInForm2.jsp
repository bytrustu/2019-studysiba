<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%
  String userID = null;
			String toID = null;
			if (session.getAttribute("userID") != null) {
				userID = (String) session.getAttribute("userID");
				//받는사람 값
				if (request.getParameter("toID") != null) {
					toID = (String) request.getParameter("toID");
				}
				// 자기자신에게 메세지를 보낼려고 할 경우
				if (userID.equals(toID)) {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('본인에게는 메세지를 보낼수 없습니다.');");
					script.println("history.back();");
					script.println("</script>");
				}

			} else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('로그인 정보가 없습니다.');");
				script.println("location.href='member/LoginForm.jsp'");
				script.println("</script>");
			}
%>



<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>

<style type="text/css">
body {
		font-family: 'Nanum Gothic', sans-serif;
}
</style>


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


    // 친구찾기 기능
	function findFunction() {
		var userID = $('#findID').val();
		$.ajax({
			type : "POST",
			url : 'ChatUserCheckAction.ch',
			data : {
				userID : userID
			},
			success : function(result) {
				if (result == 1) {
					alert('친구찾기에 성공했습니다.');
					getFriend(userID);
				} else {
					alert('친구찾기에 실패했습니다.');
					failFriend();
				}
			}
		});
	}
    
    // 친구찾기 기능에서 성공시 추가 될 html
	function getFriend(findID) {
		$('#friendResult').html('<thead>' +
			'<tr>' +
			'<th><div width="450px;"><h4>검색결과</h4></div></th>' +
			'</tr>' +
			'</thead>' +
			'<tbody>' +
			'<tr>' +
			'<td style="text-align: center;"><h3><img src="img/profile_siba.png" width="50px" height="50px" style="margin-right: 20px;">' + findID + '</h3><a href="ChatInForm2.ch?toID=' + encodeURIComponent(findID) + '" class="btn btn-primary pull-right">' + '메세지 보내기</a></td>' +
			'</tr>' +
			'</tbody>');
	}
    
    // 친구찾기 기능에서 실패시 html 요소를 클리어 해준다.
	function failFriend() {
		$('#friendResult').html('');
	}



	//메세지 출력 : selector(메세지타입) , delay(보여지는 시간)
	function autoClosingAlert(selector, delay) {
		var alert = $(selector).alert();
		alert.show();
		window.setTimeout(function() {
			alert.hide()
		}, delay);
	}

	// 메세지 정보 처리
	function submitFunction() {
		var fromID = '<%=userID%>';
		var toID = '<%=toID%>';
		var chatContent = $('#chatContent').val();
		$.ajax({
			type : "POST",
			url : "ChatSubmitAction",
			data : {
				fromID : encodeURIComponent(fromID),
				toID : encodeURIComponent(toID),
				chatContent : encodeURIComponent(chatContent),
			},
			success : function(result) {
				if (result == 1) {
					autoClosingAlert('#successMessage', 2000);
				} else if (result == 0) {
					autoClosingAlert('#dangerMessage', 2000);
				} else {
					autoClosingAlert('#warningMessage', 2000);
				}
			}
		});
		$('#chatContent').val('');
	}

	
	// 메세지함 서로간에 나눈 내용의 데이터를 가져온다.
	var lastID = 0;
	function chatContentListFunction(type) {
		var fromID = '<%=userID%>';
		var toID = '<%=toID%>';
		$.ajax({
			type : "POST",
			url : "ChatViewAction",
			data : {
				fromID : encodeURIComponent(fromID),
				toID : encodeURIComponent(toID),
				listType : type
			},
			success : function(data) {
				if (data == "") {
					return;
				}
				$('#chatList').html('');
				var parsed = JSON.parse(data);
				var result = parsed.result;
				for (var i = 0; i < result.length; i++) {
					if (result[i][0].value == fromID) {
						result[i][0].value = '나';
					}
					addChat(result[i][0].value, result[i][2].value, result[i][3].value, result[i][4].value);
				}
				lastID = Number(parsed.last);
			}
		});
	}

	// 메세지함의 메세지 내용에 추가될 html
	function addChat(chatName, chatContent, chatTime, fileName) {
		if (fileName == 'null') {
			fileName = 'siba-default.gif';
		}
		$('#chatList').append('<div class="row">' +
			'<div class="col-lg-12">' +
			'<div class="media">' +
			'<a class="pull-left" href="#">' +
			'<img class="media-object img-circle" src="/local_img/' + fileName + '" alt="" style="width:40px; height:40px;">' +
			'</a>' +
			'<div class="media-body">' +
			'<h4 class="media-heading"><b>' +
			chatName +
			'</b><span class="small pull-right">' +
			chatTime +
			'</span>' +
			'</h4>' +
			'<p>' +
			chatContent +
			'</p>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'</div>' +
			'<hr>');
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}

	// 지연시간
	function getInfiniteChat() {
		setInterval(function() {
			chatContentListFunction(lastID);
		}, 3000);
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



		<div class="container bootstrap snippet" style="width: 40%; float: left; margin-left: 5%;">
			<div class="row">
				<div class="col-xs-12">
					<div class="portlet portlet-default">

						<div class="portlet-heading">
							<div class="portlet-title">
								<h4>
									<i class="fa fa-circle text-green" id="chatSubject" style="padding-right: 5px;"></i> ${param.toID} 메세지함
								</h4>
							</div>
							<div class="clearfix"></div>
						</div>
						<div id="chat" class="panel-collapse collapse in">
							<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto; width: auto; height: 450px;"></div>
							<div class="portlet-footer">
								<div class="row" style="height: 45px;">
									<div class="form-group col-xs-8">
										<input type="text" style="padding: 10px; width: 300px;" id="chatContent" class="form-control" placeholder="메세지를 입력하세요" maxlength="100">
									</div>
									<div class="form-group col-xs-4">
										<button type="button" class="btn btn-default pull-right" onclick="location.href='ChatForm.ch'"  style="margin-bottom: 10px; margin-left:5px; float: left;">검색</button>
										<button type="button" class="btn btn-danger pull-right" onclick="submitFunction()" style="margin-bottom: 10px; float: left; margin-left:5px; ">전송</button>
										<div class="clearfix"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


            <!-- 메세지함 내용 입력시 메세지 전송 상태에 따라 표시될 부분 -->
			<div class="alert alert-success" id="successMessage" style="display: none;">
				<strong>메세지 전송에 성공했습니다.</strong>
			</div>
			<div class="alert alert-danger" id="dangerMessage" style="display: none;">
				<strong>내용을 입력해주세요.</strong>
			</div>
			<div class="alert alert-warning" id="warningMessage" style="display: none;">
				<strong>데이터베이스 오류가 발생했습니다.</strong>
			</div>

		</div>





	</div>


    <!-- 화면 실시간 갱신을 위한 조정 -->
	<script type="text/javascript">
		$(document).ready(function() {
			chatListFunction();
			getInfiniteList();
	
			chatContentListFunction('view');
			getInfiniteChat();
		});
	</script>


</body>
</html>