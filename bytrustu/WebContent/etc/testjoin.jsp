<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko"><head>
    <title>plaync 회원가입</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta name="viewport" content="user-scalable=no, maximum-scale=1.0, minimum-scale=1.0, width=device-width, initial-scale=1.0">
    <meta name="autocomplete" content="off">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-capable" content="no">
    <meta name="apple-touch-fullscreen" content="YES">
    <meta name="url" content="">
    <meta name="description" content="">
    
    <link rel="shortcut icon" type="image/x-icon" href="https://wstatic-cdn.plaync.com/common/plaync.ico">
    <link rel="icon" type="image/x-icon" href="https://wstatic-cdn.plaync.com/common/plaync.ico">
    <link rel="apple-touch-icon" type="image/x-icon" href="https://wstatic-cdn.plaync.com/common/plaync.ico">
    <link rel="stylesheet" type="text/css" media="screen" href="https://wstatic-cdn.plaync.com/id/member/css/member.css">
    
    <script async="" src="https://www.google-analytics.com/analytics.js"></script><script type="text/javascript" src="https://wstatic-cdn.plaync.com/id/common/js/lib/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="https://wstatic-cdn.plaync.com/id/common/js/lib/jquery-ui-1.10.3.min.js"></script>
    <script type="text/javascript" src="https://wstatic-cdn.plaync.com/id/common/js/lib/jquery.custom.forms.js"></script>
    <script type="text/javascript" src="https://wstatic-cdn.plaync.com/common/js/passwordCheck.min.js"></script>
    <script type="text/javascript" src="https://wstatic-cdn.plaync.com/id/member/js/member.js?v=201602291635"></script>

    <!-- common-utils -->
    <script type="text/javascript" src="/member/js/common-utils.js"></script>
    <script type="text/javascript" src="/member/js/common-validator.js"></script>

    <!-- skip nav -->
    <script type="text/javascript" src="https://wstatic-cdn.plaync.com/common/skipnav/skipnav.js"></script>

    <!-- (s) gnb -->
    <script type="text/javascript">
        //<![CDATA[

        function GNBLogin() {

            var location = document.location.href;
            if (location.length > 0 && location.indexOf("#") > 0) {
                location = location.substring(0, location.indexOf("#"));
            }
            var returnurl = encodeURIComponent(location);
            var loginURL = 'https://mlogin.plaync.com/login/signin?return_url=';

            document.location.href = loginURL + returnurl;

        }
        function GNBLogout() {
            redirect_url = document.location.href;

            $("#return_url").val(redirect_url);
            $("#FrmGNBLogout").submit();
        }
        function checkLoginPlugin(loginFormUrl) {
            try {
                if (!isSupportedBrowser()) {
                    moveNotSupportedBrowserInfoPage();
                } else if (IsPluginInstalled() == NOT_EXIST_ACTIVEX) {
                    moveNotInstalledLoginPluginLauncherPage(loginFormUrl);
                } else {
                    if (loginFormUrl != null && loginFormUrl.length > 0)
                        location.href = loginFormUrl;
                }
            } catch (e) {
            }
        }

        //]]>
    </script>

<script type="text/javascript" src="/member/js/member.js"></script>

<link rel="stylesheet" type="text/css" media="screen" href="https://wstatic-cdn.plaync.com/gnb/v5/gnbs_min.css"></head>
<body style="">
<div class="skipNav" id="skipNav">
    <!-- <a href="#navigation" id="skipNavFirst" style="font-size:0; line-height:0;"><span>메뉴 바로가기</span></a> -->
    <a href="#contents" style="font-size:0; line-height:0;"><span>본문 바로가기</span></a>
</div>
<!-- (s) gnb -->

<form name="FrmGNBLogout" id="FrmGNBLogout" method="post" action="https://mlogin.plaync.com/login/signout">
    <input type="hidden" id="return_url" name="return_url" value="https://id.plaync.com/member/join">
</form>
<script type="text/javascript">
    $('[name="return_url"]').val(location.href);
    var siteFlag = "mypage";
    var loginLinkStyle = "js";
    var isLoginFlag = "N";
    var loginLinkURL = "GNBLogin()";
</script>


<div id="div_nctop">
    <script type="text/javascript" src="https://wstatic-cdn.plaync.com/gnb/gnbload.js?v=1107181000"></script><script type="text/javascript" src="https://wstatic-cdn.plaync.com/gnb/v5/gnb_min.js?d=201203318216"></script> <div class="gnbContainer" id="gnbContainerG" style="display: block; height: 33px; width: 1100px;">
        <div class="gnbLogo"><a href="http://kr.plaync.com/" title="plaync" onmousedown="GNB_Trk('logo')">plaync</a></div>

        <div class="gnbGamesBtn"><a class="gnbGamesBtn_off" title="plaync 서비스" id="gnbGamesBtn" onmousedown="GNB_Trk('whole_btn')">전체게임</a></div>
        <div class="gnbGames" id="gnbGames"></div>
        <div id="gnbLoginWrap">
    <div class="gnbIsLogin" id="gnbIsLogin"><a href="#login" onclick="GNBLogin(); return false;" title="로그인" onmousedown="GNB_Trk('login')" class="gnbIsLogin_off">로그인</a></div>        <div class="gnbServicesGe">
            <a href="https://cs.plaync.com/" class="gnbServicesGeC" title="고객센터" onmousedown="GNB_Trk('login_cscenter')">고객센터</a>
            <a href="https://cs.plaync.com/security/main/intro" title="보안" onmousedown="GNB_Trk('login_security')">보안</a>
            <a href="http://go.plaync.co.kr/Account/Join" title="회원가입" onmousedown="GNB_Trk('login_register')">회원가입</a>
        </div>
    
    </div></div>


</div>
<!-- (e) gnb -->
<div id="container">
    <header id="header">
        <h1>회원가입</h1>
        
    </header>
    <div id="contents">
<section class="join">
    <header>
        <h2><span class="icon"></span>개인정보를 정확하게 입력해 주세요.</h2>
    </header>
    <article>
        <form id="" name="" method="post" action="">
        <fieldset>
            <legend>정보입력</legend>
            <div class="inputInfo">
                <div class="input-group wrap_id">
                    <label>계정 (이메일)</label>
                    <input type="email" name="account" id="account" maxlength="64" autocomplete="off">
                    <div id="resetInputId" class="btn-close">아이디 초기화</div>
                    <span class="msg">실제 사용하는 아이디 혹 이메일 주소를 입력해 주세요.</span>
                <div id="DomainList" class="domainList" style="display: none;"><ul></ul></div><span class="ph_label">계정 (이메일)</span></div>
                <div class="input-group wrap_nickname">
                    <label>이름 </label>
                    <input type="text" name="nickname" id="nickname" maxlength="16">
                    <span class="msg">2~16자 한글, 영문, 숫자</span>
                    <span class="tip_icon"></span>
                    <p class="tip"><span class="tip_left"></span>2 ~ 16자 한글, 영문, 숫자의 조합만 사용할 수 있습니다.<br>예) plaync123    </p>
                <span class="ph_label">이름</span></div>
                <div class="input-group wrap_pwd">
                    <label>비밀번호</label>
                    <input type="password" name="password" id="password" maxlength="16">
                    <span id="msg_password" class="msg">8~16자 영문, 숫자, 특수문자 조합</span>
                    <span class="tip_icon" id="layer_password_info"></span>
                    <p class="tip"><span class="tip_left"></span>안전한 비밀번호 만들기</p>
                    <div id="securityLayer">
                        <span class="bar_top"></span>
                        <p class="grade"></p>
                        <div class="capslock">&lt;Caps Lock&gt; 켜짐</div>
                    </div>
                <span class="ph_label">비밀번호</span></div>
                <div class="input-group">
                    <label>비밀번호 확인</label>
                    <input type="password" name="passwordRepeat" id="passwordRepeat" maxlength="16" disabled="disabled">
                    <span id="msg_password_confirm" class="msg"></span>
                <span class="ph_label">비밀번호 확인</span></div>
            <div class="ly_help" id="ly_idSaved" style="display: none;"><span class="ic_arrow"></span><div class="content"> <a class="close" href="#">닫기</a>    개인정보 보호를 위해<br> 공공장소(PC방, 학교, 도서관 등)가 아닌 <br>   <strong>개인 PC에서만 사용하세요.</strong></div><span class="shadow"></span></div><div class="ly_help" id="ly_capsLock" style="display: none;"><span class="ic_arrow"></span><div class="content">    <a id="btnCapsLockClose" class="close" href="#">닫기</a>  <strong>Caps Lock</strong>이 켜져 있습니다.</div><span class="shadow"></span></div><div id="ly_domainError" class="ly_help" style="display: none;"><span class="ic_arrow"></span><div class="content"> <a class="close" href="#">도메인 제안 닫기</a> <em style="cursor:pointer">@gmail.com</em> 이 아닌가요?</div><span class="shadow"></span></div></div>
            <div class="button">
                <input type="button" value="가입하기" id="join" class="btn btn-blue">
                <input type="button" value="이전" id="btnPrev" class="btn btn-white" onclick="location.href='/member/agreement'">
            </div>
        </fieldset>
        </form>
    </article>
</section>
<!-- 안전한 비밀번호 만들기 layer popup -->
<div id="layer_pop">
    <div class="pop_container">
        <section class="contents passowrd_info">
            <header>
                <p>고객님의 계정과 개인정보를 더욱 안전하게 보호하기 위해 보다 복잡한 비밀번호를 사용해 주세요.</p>
            </header>
            <div class="">
                <ul class="ul_security">
                    <li class="step4">
                        <dl>
                            <dt><span>4단계 (매우 좋음)</span></dt>
                            <dd><strong>매우 안전한 비밀번호입니다.</strong>(영어, 숫자, 특수문자 조합의 15자 이상 사용)</dd>
                        </dl>
                    </li>
                    <li class="step3">
                        <dl>
                            <dt><span>3단계 (좋음)</span></dt>
                            <dd><strong>안전한 비밀번호입니다.</strong>(영어, 숫자, 특수문자 조합의 12자 이상 사용)</dd>
                        </dl>
                    </li>
                    <li class="step2">
                        <dl>
                            <dt><span>2단계 (보통)</span></dt>
                            <dd><strong>적정한 보안수준의 비밀번호입니다.</strong>(영어, 숫자, 특수문자 조합의 8자 이상 사용)</dd>
                        </dl>
                    </li>
                    <li class="step1">
                        <dl>
                            <dt><span>1단계 (낮음)</span></dt>
                            <dd><strong class="t_warn">사용 가능하지만, 보안에 취약한 비밀번호입니다.</strong>(영어, 숫자, 특수문자 조합의 8자 이상을 권장)</dd>
                        </dl>
                    </li>
                    <li class="step0">
                        <dl>
                            <dt><span>0단계 (사용불가)</span></dt>
                            <dd><strong class="t_warn">비밀번호로 사용할 수 없습니다.</strong>(스페이스, 한글, 연속된 숫자 제외한 영어, 숫자, 특수문자 혼합 형태의 최소 8자 이상 사용)</dd>
                        </dl>
                    </li>
                </ul>
            </div>
        </section>
    
        <footer>
            <div class="button">
                <input type="button" id="btn_cancel" class="btn btn-blue" value="닫기">
            </div>
        </footer>
    </div>
</div>
<script type="text/javascript" src="https://wstatic-cdn.plaync.com/common/js/login.js"></script>
</div>
    <!-- (s) footer -->
    <footer id="footer">
        <address>Copyright © NCSOFT Corperation. All Rights Reserved.</address>
    </footer>
    <!-- (e) footer -->
</div>
<!-- logger -->
<script type="text/javascript" src="https://wstatic-cdn.plaync.com/common/logger/plaync_logger_footer.js"></script><script type="text/javascript" src="https://wstatic-cdn.plaync.com/common/logger/pna_ncsoft_net.js"></script>

<script type="text/javascript">
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date();
        a = s.createElement(o),
            m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'https://www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-9714319-72', 'auto');
    ga('send', 'pageview');
</script>




</body></html>