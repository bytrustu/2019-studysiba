<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.bytrustu.member.model.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<style>
a {
		text-decoration: none;
}

a:HOVER {
		text-decoration: none;
}
</style>

<%
  PrintWriter script = response.getWriter();
  String userID = null;
  Date date = new Date();
  SimpleDateFormat simpleDate = new SimpleDateFormat("yy-MM-dd");
  String todayDate = (String) simpleDate.format(date);
  
  pageContext.setAttribute("strnbsp", "&nbsp;");
  pageContext.setAttribute("strlt", "&lt;");
  pageContext.setAttribute("strgt", "&gt;");
  pageContext.setAttribute("strbr", "<br/>");
  
  if (session.getAttribute("userID") != null) {
    userID = (String) session.getAttribute("userID");
    request.setAttribute("userID", userID);
  } else {
    script.println("<script>");
    script.println("alert('로그인정보가 없습니다.')");
    script.println("location.href='member/LoginForm.jsp'");
    script.println("</script>");
  }
%>





<div class="position-ab basic-location1">
	<span class="subpage-title">자유게시판</span>
</div>


<div class="page" style="min-height: 800px; position: relative;">

	<div class="subpage-margin"></div>


	<table class="bbs_type1 bbsS" style="width: 95%;">

		<colgroup>
			<col width="*" />
			<col width="72" />
			<col width="49" />
		</colgroup>
		<thead>
			<tr>
				<th class="subject">제목</th>
				<th class="hit">조회</th>
				<th class="like">좋아요</th>
			</tr>
		</thead>
		<tbody>



			<c:forEach items="${dtos}" var="dtos">
				<%-- ArrayList 값 dtos에 저장 --%>

				<c:choose>
					<c:when test="${dtos.bAvailable eq 0}">


						<tr>
							<td class="subject">
								<div class="wrap_thumb">
									<a class="thumb" href="#"><img src="img/profile_blank.png" alt="" /></a>
									<div class="articleInfo">
										<div class="warp_title">

											<strong class="title"> <span style="color: #F07171;"> 삭제된 게시글 입니다. </span> <em class="cmt"> </em>
											</strong> <img class="new">
										</div>

										<span class="writer"> <a class="nick" href="#"></a> <a class="game" href="#"></a></span> <span class="bar"></span> <span class="feedtime"> <time>
												<span></span>
											</time>
										</span>
									</div>
								</div>
							</td>
							<td class="hit">-</td>
							<td class="scrap">-</td>
						</tr>

					</c:when>
					<c:otherwise>


						<tr>
							<td class="subject">
								<div class="wrap_thumb">

									<!-- 프로필사진 설정 - 파일이름이 null 일 경우 default 사진으로   -->
									<c:choose>
										<c:when test="${dtos.fileName eq null}">
											<a class="thumb" href="#"><img src="/local_img/siba-default.gif" alt="" /></a>
										</c:when>
										<c:otherwise>
											<a class="thumb" href="#"><img src="/local_img/${dtos.fileName}" alt="" /></a>
										</c:otherwise>
									</c:choose>


									<div class="articleInfo">

										<div class="warp_title">

											<strong class="title"> <a href="BoardContentViewAction.bo?bNum=${dtos.bNum}"> <c:if test="${dtos.bIndent > 0 }">
														<c:forEach begin="1" end="${dtos.bIndent }">
															<span>&nbsp;&nbsp;</span>
														</c:forEach>
														<span><img src="img/list-re.png"></span>
														<%-- 답글표시 이미지 --%>
													</c:if> <%-- 크로스사이트스크립트 처리를 위한 문자변환 replaceAll / jstl의 경우 fn:replace() 함수, 다 해야되지만 일단 스크립팅만 막을 제목표시줄 < 만 치환 --%> <span style="color: black; text-decoration: none;">${fn:replace(dtos.bTitle, "<", strlt)}</span>
											</a> <em class="cmt"><c:if test="${dtos.commentCount > 0}">[${dtos.commentCount}]</c:if></em>
											</strong>



											<%-- jstl 값을 jsp 변수로 값을 받아와서 sysdate 값만큼 substring 해서 오늘 bDate와 비교해서 오늘일 경우 new표시 --%>
											<c:set var="contentDate" value="${dtos.bDate}" />
											<%
											  String contentDate = (String) pageContext.getAttribute("contentDate");
											        String resultDate = contentDate.substring(0, 8);
											        if (resultDate.equals(todayDate)) {
											%>
											<img class="new" src="https://i.imgur.com/yc326w1.png">
											<%-- 오늘 게시물 표시 이미지 --%>
											<%
											  }
											%>

										</div>

										<span class="writer"> <a class="nick" href="BoardListByIdAction.bo?bId=${dtos.bId}">${dtos.bId}</a> <a class="game" href="BoardListByIdAction.bo?bId=${dtos.bId}">[${dtos.bId}]</a></span> <span class="bar">|</span> <span class="feedtime"> <time>
												<span>${dtos.bDate}</span>
											</time>
										</span>
									</div>
								</div>
							</td>
							<td class="hit">${dtos.bCount}</td>
							<td class="scrap">${dtos.contentLike }</td>
						</tr>


					</c:otherwise>

				</c:choose>
			</c:forEach>

		</tbody>
	</table>


	<button type="button" onclick="location.href='BoardWriteForm.bo'"class="btn btn-primary" style="position: absolute; bottom: 90px; right: 80px;">등록</button>


	<div class="pager">
		<div class="paging">


			<!-- 					[이전] < 표시					 -->
			<c:if test="${startPage != 1 }">
				<a href="BoardListAction.bo?pageNum=${startPage-1}" class="prev">이전</a>&nbsp;
				</c:if>

			<!-- 					startPage~endPage 까지 표시 , 현재페이지 인 경우 링크없이, bold 설정					 -->
			<c:forEach var="page" begin="${startPage}" end="${endPage}">

				<!-- 					현재 페이지 인 경우					 -->
				<c:if test="${page == pageNum}">
					<span class="current">${page}</span>
				</c:if>


				<!-- 					현재 페이지가 아닐경우 눌렀을때 링크					 -->
				<c:if test="${page != pageNum}">
					<span><a href="BoardListAction.bo?pageNum=${page}">${page}</a></span>
				</c:if>

			</c:forEach>


			<!-- 					[다음] > 표시					 -->
			<c:if test="${endPage != totalPage}">
				<a href="BoardListAction.bo?pageNum=${endPage+1}" class="next">다음</a>
			</c:if>

		</div>
	</div>


    <!--  검색바 -->
	<!-- <div class="wrap_search">
		<form id="searchForm" name="searchForm">
			<div class="wrap_select">
				<span id="btn_searchSelect" class="lyBtn"></span>
				<div id="ly_searchSelect" class="lySelect">
					<ul class="list">
						<li><label><input type="radio" name="st" value="content" checked="checked">제목+내용</label></li>
						<li><label><input type="radio" name="st" value="title">제목</label></li>
						<li><label><input type="radio" name="st" value="writer">작성자</label></li>
					</ul>
				</div>
			</div>
			<input type="text" class="inputform" name="q" maxlength="20" value="" /> <input type="image" src="img/btn_search.png" alt="검색" class="btn_search" /> -->
	</form>
</div>
</div>


