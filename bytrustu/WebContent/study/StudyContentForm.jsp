<%@page import="java.util.ArrayList"%>
<%@page import="com.bytrustu.study.model.StudyDAO"%>
<%@page import="com.bytrustu.study.model.StudyDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>




<style>
#sub-leftside {
		width: 0px;
}

#sub-centercontent {
		width: 1350px;
		border: 0px;
}

.card {
		border: 0px;
}


#sub-leftside, #sub-centercontent {
        background: #f6f7f8;
        border: 0px;
        height: 100%;
}


/*
 *  구글 맵 설정
 */
#map {
		width: 340px;
		height: 200px;
}

html, body {
		height: 100%;
		margin: 0;
		padding: 0;
}

.title {
		font-weight: bold;
}

#infowindow-content {
		display: none;
}

#map #infowindow-content {
		display: inline;
}

</style>


<%
  String userID = null;
			if (session.getAttribute("userID") != null) {
				userID = (String) session.getAttribute("userID");
			} else {
				session.setAttribute("msgSession", "로그인 정보가 없습니다.");
%>
<script>
location.href='MainForm.do';
</script>




<%
  }
			StudyDAO studyDAO = StudyDAO.getInstance();
			StudyDTO content = new StudyDTO();
			content = (StudyDTO) request.getAttribute("content");
			String userFileName = (String) request.getAttribute("userFileName");
			int checkMember = (Integer) request.getAttribute("checkMember");
			ArrayList<String> memberList = new ArrayList<String>();
			memberList = (ArrayList<String>) request.getAttribute("memberList");
			int memberCount = (Integer) request.getAttribute("memberCount");
%>


<script>

// 스터디 그룹 참여
function insertMember(){
  $.ajax({
    type : "POST",
    url : "StudyInsertMemberAction",
    data : {
      gNum : encodeURIComponent('<%=content.getgNum()%>'),
    },
    success: function(data){
      if ( data == 1 ) {
        alert('<%=content.getGroupName()%> 스터디에 참여 되었습니다.');
        document.location.reload();
      } else {
        alert('스터디참여 오류입니다.');
      }
    }
    
  });
}

function checkMember(){
  alert("이미 참여 중인 멤버 입니다.");
}
function accessDeniedMember(){
  alert("스터디 모집 인원을 초과 했습니다.")
}
</script>



<div id="study-warp">
	<div id="study-header">
		<div class="card" id="study-org">
			<div class="row">
				<div class="col-12 text-center padding-bottom1">

					<div id="study-box">
						<div id="study-img">
							<img src="/local_img/study/<%=content.getSfile()%>" class="img-rounded">
						</div>
						<div id="study-text">
							<p id="study-groupname"><%=content.getGroupName()%></p>
							<img class="img-circle" id="org-img" src="/local_img/<%=userFileName%>">
							<div id="org-info">
								<p>리더 :</p>
								<p><%=content.getUserID()%></p>
							</div>
						</div>
						<div class="clear"></div>

						<%
						  if (content.getUserID().equals(userID)) {
						%>
						<button type="button" class="btn btn-danger" id="study-btn" onclick="checkMember()">참여중</button>
						<%
						  } else if (checkMember == 0) {
						%>
						<button type="button" class="btn btn-danger" id="study-btn" onclick="insertMember()">참여하기</button>
						<%
						  } else if (memberCount >= content.getPerson()) {
						%>
						<button type="button" class="btn btn-danger" id="study-btn" onclick="accessDeniedMember()">참여불가</button>
						<%
						  } else {
						%>
						<button type="button" class="btn btn-danger" id="study-btn" onclick="checkMember()">참여중</button>
						<%
						  }
						%>
					</div>
				</div>
			</div>
		</div>
	</div>



	<div id="study-body">
		<div id="content-center">



			<div id="center-body">

				<p>세부 사항</p>
				<p><%=content.getTitle()%></p>
				<p><%=content.getContent().replace("\r\n", "<br>")%>
				<p>
			</div>


			<div class="clear"></div>


			<div id="group-people">
				<div id="people-tilte">
					<p>그룹인원</p>
				</div>
				<div id="people-attend">
					<div id="attend-box">
						<ul>
							<li class="attend-user"><img class="img-circle" src="/local_img/<%=userFileName%>">
								<p>
									<%
									  if (content.getUserID().length() > 7) {
									%>
									<%=content.getUserID().substring(0, 7) + "..."%>
									<%
									  } else {
									%>
									<%=content.getUserID()%>
									<%
									  }
									%>
								</p>
								<p>리더</p></li>

							<%
							  for (int i = 0; i < memberList.size() / 2; i++) {
							%>
							<li class="attend-user"><img class="img-circle" src="/local_img/<%=memberList.get(i * 2 + 1)%>">
								<p>
									<%=memberList.get(i * 2)%>
								</p>
								<p>회원</p></li>
							<%
							  }
							%>
						</ul>
					</div>
				</div>

			</div>
		</div>






		<div id="content-rightside">
			<div id="map-box">
				<div id="rightside-map">
					<div id="map-viewbox">
						<div id="map"></div>
						<div id="infowindow-content">
							<img id="place-icon" src="" height="16" width="16"> <span id="place-name" class="title"></span><br> Place ID <span id="place-id"></span><br> <span id="place-address"></span>
						</div>
					</div>
					<div class="map-textbox">
						<div class="map-text" clss="margin-top3">
							<i class="far fa-clock"></i>
							<div class="map-date">
								<p><%=content.getPeriod()%></p>
								<p><%=content.getStime()%></p>
							</div>
						</div>



						<div class="clear"></div>
						<div class="map-text">
							<i class="fas fa-map-marked-alt"></i>
							<div class="map-date">
								<p><%=content.getArea()%></p>
								<p><%=content.getAddress()%></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


	</div>
</div>





<!-- 구글맵 -->

<script>
      function initMap() {
        
        var lat = <%=content.getLat()%>;
        var lng = <%=content.getLng()%>;
       /*  $.ajax({
          type : "POST",
          url:"MapGetData",
          async: false,
          success:function(data){
            var mappoint = data.split(",");
            lat = Number(mappoint[0]);
            lng = Number(mappoint[1]);
            console.log(typeof lat);
            console.log(typeof lng);
            console.log(lat+" "+lng);
          }
        }); */
        var origin = {lat: lat, lng: lng};

        var map = new google.maps.Map(document.getElementById('map'), {
          zoom: 15,
          center: origin
        });
        
        
        var marker = new google.maps.Marker({
          position: origin,
          map: map,
          title: 'Hello World!'
      });
        
        var clickHandler = new ClickEventHandler(map, origin);
      }

      /**
       * @constructor
       */
      var ClickEventHandler = function(map, origin) {
        this.origin = origin;
        this.map = map;
        this.directionsService = new google.maps.DirectionsService;
        this.directionsDisplay = new google.maps.DirectionsRenderer;
        this.directionsDisplay.setMap(map);
        this.placesService = new google.maps.places.PlacesService(map);
        this.infowindow = new google.maps.InfoWindow;
        this.infowindowContent = document.getElementById('infowindow-content');
        this.infowindow.setContent(this.infowindowContent);

        // Listen for clicks on the map.
        this.map.addListener('click', this.handleClick.bind(this));
      };
      

      ClickEventHandler.prototype.handleClick = function(event) {
        console.log('click ' + event.latLng);
        
        
        $("#mapData").val(event.latLng);
        console.log($("#mapData").val());
        
        var textval = event.latLng;
        document.getElementById("textval").innerHTML = "<i class='fas fa-map-marker-alt' style='font-size:15px; margin-right: 5px; vertical-align: 1px;'></i>"+"선택된 위치 : "+event.latLng;
        
        
/*         $.ajax({
          type:"POST",
          url:"MapData",
          async: false,
          data:{mapvalue: encodeURIComponent(event.latLng)},
          success:function(data){
            
          }
        });
 */        
        
        // If the event has a placeId, use it.
        if (event.placeId) {
          console.log('You clicked on place:' + event.placeId);
          
          var substr = event.latLng;

          // Calling e.stop() on the event prevents the default info window from
          // showing.
          // If you call stop here when there is no placeId you will prevent some
          // other map click event handlers from receiving the event.
          event.stop();
          this.calculateAndDisplayRoute(event.placeId);
          this.getPlaceInformation(event.placeId);
        }
      };

      ClickEventHandler.prototype.calculateAndDisplayRoute = function(placeId) {
        var me = this;
        this.directionsService.route({
          origin: this.origin,
          destination: {placeId: placeId},
          travelMode: 'WALKING'
        }, function(response, status) {
          if (status === 'OK') {
            me.directionsDisplay.setDirections(response);
          } else {
            window.alert('Directions request failed due to ' + status);
          }
        });
      };

      ClickEventHandler.prototype.getPlaceInformation = function(placeId) {
        var me = this;
        this.placesService.getDetails({placeId: placeId}, function(place, status) {
          if (status === 'OK') {
            me.infowindow.close();
            me.infowindow.setPosition(place.geometry.location);
            me.infowindowContent.children['place-icon'].src = place.icon;
            me.infowindowContent.children['place-name'].textContent = place.name;
            me.infowindowContent.children['place-id'].textContent = place.place_id;
            me.infowindowContent.children['place-address'].textContent =
            place.formatted_address;
            me.infowindow.open(me.map);
          }
        });
      };
    </script>
<script src="https://maps.googleapis.com/maps/api/js?key=GOOGLE_API_KEY&libraries=places&callback=initMap" async defer>
    </script>
