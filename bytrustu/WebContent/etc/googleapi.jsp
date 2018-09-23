<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">

<head>
<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script>


	function checkLoginStatus() {
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
				checkLoginStatus();
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
			url : "MemberSnsJoinAciton",
			data : {
				userID : encodeURIComponent(userID),
				userName : encodeURIComponent(userName),
				userEmail : encodeURIComponent(userEmail),
				imagePath : encodeURIComponent(imagePath),
			},
			success : function(result) {

				if (result == 1) {
					$("form").attr("method", "POST").attr("action", "MemberSnsLoginAction.do?userID=" + userID).attr("target", "_parent").submit();
				}
				if (result == 0) {
					$("form").attr("method", "POST").attr("action", "MemberSnsLoginAction.do?userID=" + userID).attr("target", "_parent").submit();
				}

			}
		});

	}
</script>
</head>

<body>
	<form>
		<input type="button" data-onsuccess="onSignIn" value="checked..." onclick="
	  gauth.signIn().then(function(){
	    checkLoginStatus();
	    googleJoin();
	  });
	  ">
	</form>

</body>

</html>