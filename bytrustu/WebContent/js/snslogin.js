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