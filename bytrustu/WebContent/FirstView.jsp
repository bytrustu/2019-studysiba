<%@page import="com.bytrustu.board.model.BoardDTO"%>
<%@page import="com.bytrustu.visit.model.VisitCountDAO"%>
<%@page import="com.bytrustu.board.model.BoardDAO"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.bytrustu.member.model.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="js/snslogin.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


	<%
	  String userID = null;
	  String userName = "";
	  String userProfile = null;
	  MemberDAO dao = MemberDAO.getInstance();
      if (session.getAttribute("userID") != null) {
	   userID = (String) session.getAttribute("userID");
	   userName = dao.memberInfo(userID).getUserName();
	   userProfile = dao.getFile(userID);
	   if (userProfile == null) {
	     userProfile = "siba-default.gif";
		 }
	   }
	%>




		<div class="main_warp">

			<div id="main_image">

				<div id="main_bg">


					<div id="main_text">
						<p>온라인 스터디 그룹</p>
						<strong>STUDY SIBA</strong>
					</div>

					<div id="image_view">
						<img src="img/siro.png">
					</div>
				</div>

			</div>


			<div id="main_content">
				<div id="content_container">



<script type="text/javascript">
window.onload = function(){
  $.ajax({
      type : "POST",
      url : "SibaTotalCount",
      success : function(data) {
        var parsed = JSON.parse(data);
        var result = parsed.result;
        
        var value = new Array(result.length);
        for( var i=0; i<value.length; i++ ){
          value[i] = new Array(result[i].length);
        }
        
        for (var i=0; i<value.length; i++ ) {
          for (var j=0; j<value[i].length; j++ ) {
            value[i][j] = result[i][j].value;
          }
        }
        
        drawChart(value);
      }
  });
}


      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart(value) {
        
        var data = new google.visualization.DataTable(); 
        
        data.addColumn('date', '날짜'); 
        data.addColumn('number', '방문자수');
        data.addColumn('number', '게시물수');
        data.addRows(value.length); 
        
        for (var i=0; i<value.length; i++) {
          for (var j=0; j<value[i].length; j++ ) {
            if ( j == 0 ) {
                data.setCell(i, j, new Date(value[i][j])); 
            } else {
                data.setCell(i, j, value[i][j]); 
            }
          }
        }
        

        var options = {
            curveType: 'function',
            legend: { position: 'bottom' },
            chartArea:{left:35,top:25,width:"80%",height:"80%"}
          };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
        chart.draw(data, options);
      }
  </script>


					<!-- 홈페이지 통계 -->
					<div id="connect-user">
						<div id="connect-header">
							<div id="header-text">
								<i class="fas fa-paw"></i>
								<div id="user-container">
									<div id="user-text" class="font1">사이트 현황</div>
								</div>
							</div>
						</div>
						<div id="connect-body1">

							<div id="curve_chart" style="width: 288px; height: 210px"></div>
						</div>
					</div>






					<div id="connect-user">
						<div id="connect-header">
							<div id="header-text">
								<i class="fas fa-thumbs-up"></i>
								<div id="user-container">
									<div id="user-text" class="font1">
										<span>실시간 </span> <span id="utext-span2">인기 </span> <span>게시글</span>
									</div>
								</div>
							</div>
						</div>
						<div id="connect-body">

							<%
							  ArrayList<BoardDTO> contentRank = new ArrayList<BoardDTO>();
										contentRank = BoardDAO.getInstance().getRankLikeContent();

										for (int i = 0; i < contentRank.size(); i++) {

											String rankProfile = null;
											rankProfile = dao.getFile(contentRank.get(i).getbId());
											if (rankProfile == null) {
												rankProfile = "siba-default.gif";
											}
							%>

							<script type="text/javascript">
                        $(function(){
                            $('#body-backimg<%=i + 1%>').css('background-image', "url('/local_img/<%=rankProfile%>')");
                        });
                        </script>




							<div id="body-backimg<%=i + 1%>"></div>

							<div id="body-text2" class="body-image<%=i + 1%>">
								<div class="body-float text-margin4 text-color1">
									<i class="fas fa-heart"><%=BoardDAO.getInstance().getContentLikeCount(contentRank.get(i).getbNum())%></i>
								</div>
								<div class="body-float text-margin5">
									<a href="BoardContentViewAction.bo?bNum=<%=contentRank.get(i).getbNum()%>"><span> 
									<!-- 제목이 9자 이상이면 9자 까지 출력 후 ... 표시 --> 
									<%
									      if (contentRank.get(i).getbTitle().length() >= 9) {
									            out.print(contentRank.get(i).getbTitle().substring(0, 9) + "...");
									              } else {
									                   out.print(contentRank.get(i).getbTitle());
									                     }
							        %>
									</span> </a>
								</div>
								<div class="clear"></div>
							</div>

							<%
							  }
							%>

							<!-- <div id="body-text2" class="body-image2">
                                <div class="body-float text-margin4 text-color1">
                                    <i class="fas fa-heart">32</i>
                                </div>
                                <div class="body-float text-margin5">누구누구</div>
                                <div class="clear"></div>
                            </div>
                            
                            <div id="body-text2" class="body-margin1 body-image3">
                                <div class="body-float text-margin4 text-color1">
                                    <i class="fas fa-heart">32</i>
                                </div>
                                <div class="body-float text-margin5">누구누구</div>
                                <div class="clear"></div>
                            </div> -->

						</div>
					</div>







					<!-- 접속자 현황   -->


					<div id="connect-user">
						<div id="connect-header">
							<div id="header-text">
								<i class="fas fa-user-clock"></i>
								<div id="user-container">
									<div id="user-text" class="font1">접속자 현황</div>
									<div id="user-num">
										<div><%=MemberDAO.getInstance().getConnectedUserCount()%></div>
										<p>명</p>
									</div>
								</div>
							</div>
						</div>
						<div id="connect-body">

							<%
							  ArrayList<String> list = dao.getConnectedUser();
							  
							  for (int i = 0; i < list.size(); i++) {
							    
							    String fileName = MemberDAO.getInstance().getFile(list.get(i));
							    if (fileName == null) {
							      fileName = "siba-default.gif";
							    }
							%>
							<div id="body-text1">

								<div class="body-float">
									<img class="img-rounded" src="/local_img/<%=fileName%>">
								</div>
								<div class="body-float text-margin2"><%=dao.memberInfo(list.get(i)).getUserName()%></div>
								
								
								<div class="body-float text-margin3">
									<a href="ChatInForm2.ch?toID=<%=list.get(i)%>"><i class="far fa-envelope"></i></a>
								</div>




								<div class="clear"></div>
							</div>
							<%
							  }
							%>

						</div>
					</div>



                    <!-- 우측 사용자정보 페이지 ( 비접속중일 경우 회원가입/로그인 영역으로 표시 ) -->
					<%
					  if (session.getAttribute("userID") == null) {
					%>


					<div id="login-before">
						<div id="nlogin-text">스터디를 참가하려면 !</div>
						<button id="nlogin-btn-warp" type="button" onclick="location.href='member/LoginForm.jsp'">
							<div id="nlogin-btn">
								<div>
									<img src="img/login_logo.png" width="70px" height="35px">
									<p>로그인</p>
								</div>
							</div>
						</button>


						<div class="bottom-line"></div>

						<div id="nlogin-info">
							<p>
								<a href="member/JoinForm.jsp">회원가입</a>
							</p>
							<p>|</p>
							<p>
								<a href="#">계정비밀번호찾기</a>
							</p>
						</div>


						<div id="nlogin-api">
							<button class="login-api" id="api-facebook" type="button" onclick="
							   FB.login(function(response){
							        checkLoginStatus(response);
							         });
						 		     facebookJoin();
						 		      ">
								<div>
									<img src="img/login-facebook.png">
									<p>로그인</p>
								</div>
							</button>


							<button type="button" class="login-api" id="api-google" onclick="
						gauth.signIn().then(function(){
				        gooCheckLoginStatus();
				        googleJoin();
				      });
						">
								<div>
									<img src="img/login-google.png">
									<p>로그인</p>
								</div>
							</button>


						</div>
					</div>


				</div>
			</div>


            <!-- 로그인 중일 경우 사이트 회원 커뮤니티 정보를 표시 -->
			<%
			  } else {
			%>


			<div id="user-info">
				<div id="user-header">
					<div id="uheader-top">
						<div>
							<img src="/local_img/<%=userProfile%>" class="img-rounded">
						</div>
						<div id="uheadtop-name">
							<p><%=userName%></p>
						</div>
						<div id="uheadtop-last">
							<p>님</p>
						</div>
						<!-- <div id="uheadtop-edit">
						<a href="MemberModifyViewAction.do"><i class="fas fa-redo-alt fa-spin"></i></a>
					</div> -->
						<div class="clear"></div>

						<div id="uheadtop-lastdate">
							최종접속시간 :
							<%=dao.getConnectDate(userID)%></div>

					</div>

					<div class="clear"></div>

					<div id="uheader-bottom">
						<div id="uheadbottom-left">
							<div class="height-size1">
								<p>
									<i class="fas fa-user"></i>
								</p>
								<p>출석수</p>
							</div>
							<div class="clear"></div>
							<div class="height-size1">
								<p>
									<i class="fas fa-file-signature"></i>
								</p>
								<p>게시글작성수</p>
							</div>
							<div class="clear"></div>
							<div class="height-size1">
								<p>
									<i class="fas fa-comments"></i>
								</p>
								<p>댓글작성수</p>
							</div>
							<div class="clear"></div>
						</div>
						<div id="uheadbottom-right">
							<div><%=VisitCountDAO.getInstance().getMemberVisit(userID)%>회
							</div>
							<div><%=BoardDAO.getInstance().getUserContentCount(userID)%>개
							</div>
							<div><%=BoardDAO.getInstance().getUserReplyCount(userID)%>개
							</div>
						</div>
					</div>
				</div>

				<div class="clear"></div>

				<div id="user-bottom">
					<div>
						<p>
							<a href="BoardListByIdAction.bo?bId=<%=session.getAttribute("userID")%>">내가 쓴 글</a>
						</p>
					</div>
					<div>
						<p>
							<a href="MemberModifyViewAction.do">정보 수정</a>
						</p>
					</div>
				</div>

			</div>


			<%
			  }
			%>


		</div>


	</form>

</body>
</html>