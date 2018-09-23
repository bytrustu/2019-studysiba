<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Facebook Login JavaScript Example</title>
<meta charset="UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<script>
	
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
				FB.api('/me', {fields : 'name,email'}, function(response) {
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

    <form>
    

	<input type="button"  value="checking..." onclick="
	  FB.login(function(response){
	    checkLoginStatus(response);
	  });
	facebookJoin();
	">

	<div id="status"></div>

    </form>
</body>
</html>