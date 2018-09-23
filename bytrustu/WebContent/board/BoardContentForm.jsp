<%@page import="com.bytrustu.board.model.BoardDAO"%>
<%@page import="com.bytrustu.comment.model.CommentDTO"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.bytrustu.board.model.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style type="text/css">
a {
		cursor: pointer;
}
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



	// 댓글등록
	function writeCmt(bNum) {
		var content = document.getElementById("contentsText").value;

		if (!content) {
			alert("내용을 입력하세요.");
			return false;
		} else {
			var param = "bNum=" + bNum + "&cContent=" + content;

			httpRequest = getXMLHttpRequest();
			httpRequest.onreadystatechange = checkFunc;
			httpRequest.open("POST", "CommentWriteAction.co", true);
			httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded;charset=UTF-8');
			httpRequest.send(param);
		}
	}

	function deletCmt(cNum) {
		var param = "cNum=" + cNum;
		if (confirm('댓글을 삭제 하시겠습니까?')) {
			httpRequest = getXMLHttpRequest();
			httpRequest.onreadystatechange = checkFunc;
			httpRequest.open("POST", "CommentDeleteAction.co", true);
			httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8');
			httpRequest.send(param);
		}
	}



	function checkFunc() {
		var resultText = null;
		if (httpRequest.readyState == 4) {
			resultText = httpRequest.responseText;
			if (resultText == 1) {
				document.location.reload();
			}
		}
	}


	var openWriteForm = null;
	var openerModifyForm = null;

	var openerX = (window.screen.width / 2) - (700 / 2);
	var openerY = (window.screen.height / 2) - (150 / 2);

	function openWrite(bNum, precId, cGroup, cStep, cIndent) {
		window.name = Math.random(100).toString();
		var openrLocation = "comment/CommentWriteForm.jsp?bNum=" + bNum + "&precId=" + precId + "&cGroup=" + cGroup + "&cStep=" + cStep + "&cIndent=" + cIndent;
		var openrOption = "width=700, height=150, resizable = no, scrollbars = no, status = no, toolbar = no, location = no, left=" + openerX + ", top=" + openerY;
		var openWrite = window.open(openrLocation, openWriteForm, openrOption);
	}

	function openModify(cNum, cContent) {
		window.name = "CommentModifyForm";
		var openerLocation = "comment/CommentModifyForm.jsp?cNum=" + cNum + "&cContent=" + cContent;
		var openrOption = "width=700, height=150, resizable = no, scrollbars = no, status = no, toolbar = no, location = no, left=" + openerX + ", top=" + openerY;
		var openModify = window.open(openerLocation, openerModifyForm, openrOption);
	}
</script>





<%
  PrintWriter script = response.getWriter();

			String userID = null;

			userID = (String) session.getAttribute("userID");

			BoardDAO dao = BoardDAO.getInstance();

			BoardDTO dto = (BoardDTO) request.getAttribute("dto");
			int bNum = dto.getbNum();
			int bAvailable = dto.getbAvailable();

			pageContext.setAttribute("nbsp", "&nbsp;");
			pageContext.setAttribute("lt", "&lt;");
			pageContext.setAttribute("gt", "&gt;");
			pageContext.setAttribute("br", "<br/>");

			if (session.getAttribute("userID") != null) { // session에 userID가 있을경우
				userID = (String) session.getAttribute("userID");
				request.setAttribute("userID", userID);

				if (bAvailable == 0) { // 삭제 된 글일경우 
					script.println("<script>");
					script.println("alert('접근 할수있는 경로가 아닙니다.')");
					script.println("history.back();");
					script.println("</script>");
				}

			} else { // session에 userID가 없을경우
				script.println("<script>");
				script.println("alert('로그인정보가 없습니다.')");
				script.println("location.href='member/LoginForm.jsp'");
				script.println("</script>");
			}
%>




<div class="position-ab basic-location1">
    <span class="subpage-title">자유게시판</span>
</div>


<section id="contents">
	<div class="subpage-margin"></div>

	<div class="subpage-margin2"></div>
	
	<div class="page">


		<!-- 게시글 본문  -->
		<div class="view_type1" id="bbsTy_free" style="width: 1150px; margin: 0;">
			<div class="viewArticleTitle">
				<div class="subject" id="article_view_subject">${dto.bTitle }</div>
				<div class="writer">
					<a class="nick" href="#">${dto.bId}</a> <a class="game" href="#">[${dto.bId}]</a>
				</div>
				<div class="viewInfo">
					<span class="feedtime"> <time>${dto.bDate}</time>
					</span> <span class="bar">|</span> <span class="hit">조회 <span class="num">${dto.bCount }</span></span> <span class="bar">|</span> <span class="declare" onclick="">신고하기</span>
				</div>
			</div>
			<div class="viewArticle" id="article_contents" style="min-height: 300px;">
				<div data-contents-type="text">${dto.bContent}</div>
			</div>



			<%
			  int likeCount = dao.getContentLikeCount(dto.getbNum());
			%>


        <!-- 좋아요 값에 따른 상태 변화 -->
        <script>
        $(document).ready(function(){
          
          $('.like_btn').mouseover(function(){
            $('#like_text2').text('');
          });
          $('.like_btn').mouseleave(function(){
            $('#like_text2').text('<%=likeCount%>');
          });
          
          
          $('.unlike_btn').mouseover(function(){
            $('#likeheart').removeClass('fa-heart');
            $('#likeheart').addClass('fa-heartbeat');
            $('#unlike_text2').text('');
            $('#unlike_text3').text('취소');
          });
          $('.unlike_btn').mouseleave(function(){
            $('#likeheart').removeClass('fa-heartbeat');
            $('#likeheart').addClass('fa-heart');
            $('#unlike_text2').text('좋아요');
            $('#unlike_text3').text('<%=likeCount%>');
          });
        });
        
        </script>



			<script>
        
	    // 좋아요 업데이트
        function contentLikeFunction(value){
          var num;
          if ( value == 1 ) {
          num = 1;
          } else {
          num = 0;
          }
          $.ajax({
            type : "POST",
            url : "BoardContentLikeAction",
            data : {
              checkACtion : num,
              contentNo : '<%=dto.getbNum()%>',
              userID : '<%=userID%>',
            },
            success : function(result) {
              if ( result == 1) {
                document.location.reload();
              } else {
                document.location.reload();
              }
              
            }
          });
        }
        
        
        </script>


			<form id="frm">


				<div class="like_warp">


					<%
					  if (dao.getCheckContentLike(dto.getbNum(), userID) == 0) {
					%>
					<button class="like_btn" type="button" onclick="contentLikeFunction(1)">
						<div class="">
							<span><i class="fas fa-heart"></i></span><span id="like_text1">좋아요</span><span id="like_text2"><%=likeCount%></span>
						</div>
					</button>

					<%
					  } else {
					%>

					<button class="unlike_btn" type="button" onclick="contentLikeFunction(0)">
						<div class="">
							<span id="unlike_text1"><i id="likeheart" class="fas fa-heart"></i></span><span id="unlike_text2">좋아요</span><span id="unlike_text3"><%=likeCount%></span>
						</div>
					</button>
					<%
					  }
					%>

				</div>


			</form>








			<!-- 개인 영역 -->
			<div id="signature">
				<div class="wrap_signature">
					<div class="wrap_thumb">
						<a class="thumb" href="#"> <!-- 프로필사진 설정 - 파일이름이 null 일 경우 default 사진으로   --> <c:choose>
								<c:when test="${dto.fileName eq null}">
									<img src="/local_img/siba-default.gif" alt="" />
								</c:when>
								<c:otherwise>
									<img src="/local_img/${dto.fileName}" alt="" />
								</c:otherwise>
							</c:choose>



						</a> <span class="writer"> <a class="nick" href="#">${dto.bId}</a> <a class="game" href="#">[${dto.bId}]</a></span>

					</div>

					<div class="wrap_sns" id="wrap_sns">
						<a class="friend" href="ChatInForm2.ch?toID=${dto.bId}">메세지</a> <a class="makebbs" href="BoardListByIdAction.bo?bId=${dto.bId}">작성게시물</a>
					</div>
				</div>
			</div>

			<div style="text-align: right; padding-top: 30px; padding-right: 30px;">

				<a href="BoardListAction.bo" class="btn btn-primary" style="">목록</a> <a href="BoardReplyViewAction.bo?bNum=<%=bNum%>" class="btn btn-primary">답변</a>


				<%
				  // 로그인 사용자와 작성자 한 글 일 경우 수정/삭제 버튼 활성화
				  if (session.getAttribute("userID").equals(dto.getbId())) {
				%>
				<a href="BoardContentModifyViewAction.bo?bNum=<%=bNum%>" class="btn btn-primary">수정</a> <a href="BoardContentDeleteAction.bo?bNum=<%=bNum%>" class="btn btn-primary">삭제</a>
				<%
				  }
				%>
			</div>






			<!-- 댓글영역 -->
			<section class="wrap_comment" id="comment">
				<div class="comment_info">
					<menu class="commentList">
						<li><em id="commentCount">${commentCount }</em>개의 댓글이 있습니다.</li>
					</menu>
				</div>

				<!-- 댓글 작성칸 -->
				<div class="comment_form">
					<form name="commentForm" method="post">
						<div>
							<textarea class="content scrollOff" name="contents" id="contentsText" placeholder="댓글을 작성해주세요."></textarea>
						</div>
						<button class="submit" id="comment_write" onclick="writeCmt('${dto.bNum}')">작성</button>
					</form>
				</div>


				<section class="comment_body">



					<c:forEach items="${commentList}" var="comment">

						<c:choose>

							<%-- 부모 댓글인경우 step=0 --%>
							<c:when test="${comment.cStep eq 0}">

								<article class="comment_thread">
									<article class="comment_article">

										<%-- 프로필사진 --%>
										<div class="character">
											<a href="#"><img class="profile" src="/local_img/${comment.profile}" alt="" /></a>
										</div>
										<%-- ID/이름/댓글내용 --%>
										<div class="article">
											<div class="content">
												<span class="writer"> <a class="nick" href="#">${comment.cId}</a> <a class="game" href="#">[${comment.cId}]</a>
												</span> ${comment.cContent}
											</div>

											<%-- 댓글정보  --%>
											<div class="article_info">
												<%-- 글작성자가 댓글을 쓴 경우 작성자 아이콘 표시 --%>
												<span><c:if test="${dto.bId eq comment.cId }">
														<img src="img/comment-writer.png">
													</c:if></span> <span class="feedtime"><time>${comment.cDate}</time></span>
												<%-- 댓글작성자 인경우 수정/삭제 버튼 활성화 --%>
												<c:if test="${userID eq comment.cId}">
													<span class="bar">&nbsp;|</span>
													<span class="declare" onclick="openModify('${comment.cNum}','${comment.cContent}')">&nbsp;수정&nbsp;</span>
													<span class="bar">|</span>
													<span class="declare" onclick="deletCmt('${comment.cNum}')">삭제</span>
												</c:if>
											</div>

										</div>
										<div class="wrap_feed">
											<div class="reply">
												<p class="count zero">
													<span></span>
												</p>
												<button class="doReply" onclick="openWrite('${comment.bNum}','${comment.cId}','${comment.cGroup}','${comment.cStep}','${comment.cIndent}')">덧글</button>
											</div>
										</div>
									</article>
								</article>

							</c:when>


							<%-- 자식 댓글인경우 step>0 --%>
							<c:otherwise>

								<div class="reply_thread">
									<article class="reply_article">



										<div class="character">
											<a href="#"><img class="profile" src="/local_img/${comment.profile }" alt="" /></a>
										</div>
										<div class="article">
											<div class="content">
												<span class="writer"> <a class="nick" href="#">${comment.cId}</a> <a class="game" href="#">[${comment.cId}]</a>
												</span> <a class="reNick" href="#">${comment.precId}</a> ${comment.cContent}
											</div>

											<div class="article_info">
												<span><c:if test="${dto.bId eq comment.cId }">
														<img src="img/comment-writer.png">
													</c:if></span> <span class="feedtime"><time>${comment.cDate}</time></span>
												<c:if test="${userID eq comment.cId}">
													<span class="bar">&nbsp;|</span>
													<span class="declare" onclick="openModify('${comment.cNum}','${comment.cContent}')">&nbsp;수정&nbsp;</span>
													<span class="bar">|</span>
													<span class="declare" onclick="deletCmt('${comment.cNum}')">삭제</span>
												</c:if>
											</div>

										</div>
										<div class="wrap_feed">
											<div class="reply">
												<p class="count zero">
													<span></span>
												</p>
												<button class="doReply" onclick="openWrite('${comment.bNum}','${comment.cId}','${comment.cGroup}','${comment.cStep}','${comment.cIndent}')">덧글</button>
											</div>
										</div>
									</article>

								</div>

							</c:otherwise>
						</c:choose>
					</c:forEach>
				</section>
			</section>
		</div>
	</div>
</section>






