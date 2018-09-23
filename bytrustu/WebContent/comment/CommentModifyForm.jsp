<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>

<style type="text/css">
.scrollOff{overflow:hidden}
.readOnly{text-decoration:underline;font-weight:700}
.comment_form{clear:both;position:relative;margin:20px;padding:10px;background-color:#f5f5f5}
.comment_form textarea{width:530px;}
.comment_form .length{display:none;width:100%;margin-top:2px;font-size:11px;font-family:Roboto,sans-serif;color:#ababab;text-align:right}
.comment_form .length span{color:#46909b}
.comment_form textarea,.wrap_comment .reply_form textarea{height:82px;margin:0;padding:5px;color:#989898;border:1px solid #e0e0e0;background:#fff;resize:none;vertical-align:middle;line-height:14px}
.comment_form button{display:block;position:absolute;top:10px;right:9px;width:89px;height:82px;border:1px solid #e0e0e0;color:#fff;font-weight:700;background:#b6c0c8 url(/../../img/comment-button.png) repeat-x left top}
</style>

<script type="text/javascript">
	var httpRequest = null;

	// httpRequest 객체 생성
	function getXMLHttpRequest() {
		var httpRequest = null;
		if (window.ActiveXObject) {
			try {
				httpRequest = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e2) {
					httpRequest = null;
				}
			}
		} else if (window.XMLHttpRequest) {
			httpRequest = new window.XMLHttpRequest();
		}
		return httpRequest;
	}

	function writeCmt(cNum) {
		var parentForm = opener.document;
		var cContent = frm.cContent.value;
		if (!cContent) {
			alert("내용을 입력하세요");
			
			return false;
		} else {
			var param = "cNum=" + cNum + "&cContent=" + cContent;
			httpRequest = getXMLHttpRequest();
			httpRequest.onreadystatechange = checkFunc;
			httpRequest.open("POST", "CommentModifyAction.co", true);
			httpRequest.setRequestHeader('Content-Type',	'application/x-www-form-urlencoded;charset=UTF-8');
			httpRequest.send(param);
			alert('댓글이 수정되었습니다.');
			window.close();
			parentForm.location.reload();
		}
	}

	function checkFunc() {
		if (httpRequest.readyState == 4) {
			var resultText = httpRequest.responseText;
			if (resultText == 1) {
			}
		}
	}
</script>


	<form name="frm">


		<div class="comment_form">
		<textarea name="cContent">${param.cContent}</textarea>
		
		<button type="button" onclick="writeCmt('${param.cNum}')">수정</Button>
		</div>

	</form>

