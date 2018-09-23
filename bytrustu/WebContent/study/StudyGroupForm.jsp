<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
ArrayList<HashMap<String,Object>> groupList = new ArrayList<HashMap<String,Object>>();
groupList = (ArrayList<HashMap<String,Object>>)request.getAttribute("groupList");
%>


	<div class="grouplistwarp">

		<div class="position-ab basic-location1">
			<span class="subpage-title">스터디그룹</span>
		</div>

		<div class="subpage-margin"></div>


        <!-- 접속 된 아이디가 참여하고 있는 그룹을 표시 -->
		<div id="grouplist-title">
			<p>
				<i class="far fa-comments"></i><span>참여 목록</span>
			</p>
		</div>

		<div>


             <%
             for (int i=0; i<groupList.size(); i++){
               int gNum = (Integer)groupList.get(i).get("gNum");
             %>
             
			<button class="row" id="grouplist-box" type="button" onclick="location.href='StudyGroupContentViewAction.st?gNum=<%=gNum%>'">

					<div id="box-img">
						<img src="/local_img/study/<%=groupList.get(i).get("image")%>">
					</div>
					<div id="box-divide">
						<span>[<%=groupList.get(i).get("divide")%>]</span>
					</div>
					<div id="box-name">
						<span><%=groupList.get(i).get("groupName")%></span>
					</div>
					<div id="box-address">
						<i class="fas fa-map-marked-alt"></i><span><%=groupList.get(i).get("address")%></span>
					</div>
					<div id="box-time">
						<i class="fas fa-history"></i><span><%=groupList.get(i).get("time")%></span>
					</div>
					<div id="box-count">
						<i class="fas fa-user-alt"></i> <span><%=groupList.get(i).get("currCount")%> / <%=groupList.get(i).get("person")%></span>
					</div>
			</button>
			<%
             }
			%>
			
		</div>
	</div>


</body>
</html>