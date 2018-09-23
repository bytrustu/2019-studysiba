<%@page import="com.bytrustu.member.model.MemberDTO"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.bytrustu.visit.model.VisitCountDAO"%>
<%@page import="com.bytrustu.board.model.BoardDAO"%>
<%@page import="com.bytrustu.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
	  String userID = null;
	  if (session.getAttribute("userID") != null) {
	    userID = (String) session.getAttribute("userID");
	  }
	  
	  MemberDAO memberDAO = MemberDAO.getInstance();
	  MemberDTO userInfo = new MemberDTO();
	  BoardDAO boardDAO = BoardDAO.getInstance();
	  VisitCountDAO visitCountDAO = VisitCountDAO.getInstance();
	  HashMap<String, Object> subMap = new HashMap<String, Object>();
	  
	  userInfo = memberDAO.memberInfo(userID);
	  String lastDate = memberDAO.getConnectDate(userID);
	  String fileName = memberDAO.getFile(userID);
	  if(fileName == null ) {
	    fileName = "siba-default.gif";
	  }
	  
	  int visitCount = visitCountDAO.getUserVisitCount(userID);
	  int contentCount = boardDAO.getUserContentCount(userID);
	  int replyCount = boardDAO.getUserReplyCount(userID);
	  
	  subMap.put("lastDate", lastDate);
	  subMap.put("fileName", fileName);
	  subMap.put("visitCount", visitCount);
	  subMap.put("contentCount", contentCount);
	  subMap.put("replyCount", replyCount);
	%>


	<div id="rside-warp">

		<div id="rside-box">

			<div id="rside-userinfo">
				<div id="rside-userimg">
					<img src="/local_img/<%=subMap.get("fileName")%>">
				</div>
				<div id="rside-username" class="fontcolor1">
					<span><%=userInfo.getUserName() %></span>
				</div>
				<!-- <div id="rside-recycle"><span><i class="fas fa-spinner fa-spin"></i></span></div> -->
				<div id="rside-usermail">
					<span><%=userInfo.getUserEmail() %></span>
				</div>
				<div id="rside-usertime">
					<span>최종접속시간 : <%=subMap.get("lastDate") %></span>
				</div>
			</div>

			<div id="rside-menubar">
				<ul>
					<li><p>
							<i class="fas fa-angle-right"></i><a href="BoardListAction.bo"><span>자유게시판</span></a>
						</p></li>
					<li><p>
							<i class="fas fa-angle-right"></i><a href="ChatForm.ch"><span>메세지함</span></a>
						</p></li>
					<li><p>
							<i class="fas fa-angle-right"></i><a href="StudyListAction.st"><span>스터디룸</span></a>
						</p></li>
					<li><p>
							<i class="fas fa-angle-right"></i><a href="StudyGroupListAction.st"><span>스터디그룹</a>
						</p></li>
				</ul>
			</div>


			<div id="rside-community">
				<div id="rside-cotext">
					<span>커뮤니티활동</span>
				</div>
				<div id="rside-coinfo">
					<div id="rside-infogroup">
						<p id="rside-infovalue"><%=subMap.get("visitCount") %></p>
						<p id="rside-infotitle">방문한수</p>
					</div>
					<div id="rside-infogroup">
						<p id="rside-infovalue"><%=subMap.get("contentCount") %></p>
						<p id="rside-infotitle">작성한글</p>
					</div>
					<div id="rside-infogroup">
						<p id="rside-infovalue"><%=subMap.get("replyCount") %></p>
						<p id="rside-infotitle">댓글단글</p>
					</div>
				</div>
			</div>

		</div>


	</div>

</body>
</html>