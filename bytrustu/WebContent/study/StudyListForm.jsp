<%@page import="com.bytrustu.study.model.StudyDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
<meta charset="utf-8">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<style>
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
		width: 560px;
		height: 200px;
}
/* Optional: Makes the sample page fill the window. */
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

#sub-leftside, #sub-centercontent {
		background: #f6f7f8;
		border: 0px;
		height: 100%;
}


</style>


<%
String userID = null;
if ( session.getAttribute("userID") != null ){
  userID = (String)session.getAttribute("userID");
} else {
  session.setAttribute("msgSession", "로그인 정보가 없습니다.");
%>
<script>
location.href='MainForm.do';
</script>
<%
}
ArrayList<StudyDTO> list = new ArrayList<StudyDTO>();
list = (ArrayList<StudyDTO>) request.getAttribute("list");
int pageNum = (Integer) request.getAttribute("pageNum");
int totalPage = (Integer) request.getAttribute("totalPage");
%>


<script>

function updateList(listNum){
  var gNum = listNum;
  $.ajax({
    type:"POST",
    url:"StudyUpdateList",
    async: false,
    data:{gNum: gNum},
    success:function(data){
      if( data == "1"){
      alert('게시글이 업데이트 되었습니다.');
      document.location. reload();
      } else {
        alert('업데이트실패');
      }
    }
  });
}

</script>



<!-- 스터디 등록 게시판 메인페이지 -->

<div class="studylistwarp">

	<div class="position-ab basic-location1">
		<span class="subpage-title">스터디룸</span>
	</div>

	<div class="subpage-margin"></div>


	<section class="container" style="margin-bottom: 30px;">
		<form method="post" action="StudyContentSearchAction.st" class="form-inline mt-3">
			<select name="divide" class="form-control mx-1 mt-2">
				<option value="전체">전체</option>
				<option value="프로그래밍">프로그래밍</option>
				<option value="외국어">외국어</option>
				<option value="자격증">자격증</option>
				<option value="취미">취미</option>
				<option value="자기계발">자기계발</option>
				<option value="기타">기타</option>
			</select> <input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-default mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록</a> <a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
		</form>
	</section>



	<%
	  for (int i = 0; i < list.size(); i++) {
	%>
	<div class="card bg-light mt-3" style="width: 90%; margin: 20px 0 0 0;">
		<div class="card-header bg-light">
			<div class="row padding-size2">
				<div class="col-2 text-left" style="text-align: center;"><%=list.get(i).getDivide()%></div>
				<div class="col-6 text-left">
					<%=list.get(i).getGroupName()%>
					<small>( <%=list.get(i).getUserID()%> )
					</small>
				</div>
				<div class="col-4 text-right">
					등록일 <span style="color: red;"><%=list.get(i).getDate().substring(0, 11)%></span>
				</div>
			</div>
		</div>
		<div class="row" style="margin: 10px 0 0 0; padding: 8px;">
			<div class="col-12 text-left">
				<i class="fas fa-pencil-alt" style="margin: 0 10px 0 0;"></i>
				<%=list.get(i).getTitle()%>
				<button id ="update-cycle" id="update-cycle" onclick="updateList(<%=list.get(i).getgNum()%>)">
				    <i class="fas fa-sync fa-spin"></i>
			    </button>
			</div>
		</div>

		<div class="row" style="margin: 0; padding: 8px;">
			<div class="col-4 text-left">
				<i class="fas fa-globe-americas" style="margin: 0 15px 0 0;"></i><%=list.get(i).getArea()%>
				<%=list.get(i).getAddress()%>
			</div>
			<div class="col-8 text-left">
				<i class="far fa-clock" style="margin: 0 15px 0 0;"></i>
				<%=list.get(i).getPeriod()%>
				[<%=list.get(i).getStime()%>]
			</div>
		</div>

		<div class="row" style="margin: 0; padding: 8px;">
			<div class="col-4 text-left">
				<i class="fas fa-user" style="margin: 0 10px 0 0;"></i> 최대인원(<span style="color: red;"><%=list.get(i).getPerson()%></span>)
			</div>
			<div class="col-6 text-left">
				<i class="fas fa-spinner fa-spin" style="margin: 0 15px 10px 0;"></i>참여인원(<span style="color: red;"><%=list.get(i).getListFile().size() %></span>)<span style="margin-right: 15px;"></span>
				
				<img class="img-circle" src="/local_img/<%=list.get(i).getUserFile() %>" style="width: 20px; height: 20px; margin: 0 10px 0 0;">
			    
	            <%
	            for (int j=0; j<list.get(i).getListFile().size(); j++){
	            
	            %>	    
			    <img class="img-circle" src="/local_img/<%=list.get(i).getListFile().get(j) %>" style="width: 20px; height: 20px; margin: 0 10px 0 0;">
                <%
	            }
                %>
			
			</div>
			<div class="col-2 text-right">
				<a href="StudyContentViewAction.st?num=<%=list.get(i).getNum()%>">자세히보기</a>
			</div>
		</div>
	</div>

	<%
	  }
	%>

	<div class="row margin-top3 margin-bottom1">
		<div class="col-12 text-center">
			<%
			  if (pageNum != 1) {
			%>
			<button type="button" class="btn btn-default" onclick="location.href='StudyListAction.st?pageNum=<%=pageNum - 1%>'">이전</button>
			<%
			  }
			%>
			<%
			  if (pageNum != totalPage) {
			%>
			<button type="button" class="btn btn-default" onclick="location.href='StudyListAction.st?pageNum=<%=pageNum + 1%>'">다음</button>
			<%
			  }
			%>
		</div>
	</div>





<!-- 빈칸 있을시 메세지 validation checking -->
<script type="text/javascript">
function reportSubmit(){
  if ( $('#studyName').val() == '' ) {
    alert('스터디명을 입력해 주세요.');
    return false;
  } else if ( $('#studyDate').val() == '' ) {
    alert('기간을 입력해 주세요.');
    return false;
  } else if ( $('#studyTime').val() == '' ) {
    alert('시간대를 입력해 주세요.');
    return false;
  } else if ( $('#studyAdress').val() == '' ) {
    alert('주소를 입력해 주세요.');
    return false;
  } else if ( $('#studyTitle').val() == '' ) {
    alert('제목을 입력해 주세요..');
    return false;
  } else if ( $('#studyContent').val() == '' ) {
    alert('내용을 입력해 주세요..');
    return false;
  } 
}
</script>




	<!-- 스터디 등록 모달 팝업창 -->
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: auto;">
				<div class="modal-header">
					<h4 class="modal-title" id="modal" style="font-weight: bold;">스터디 등록</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" style="height: 900px;">

					<form action="StudyContentWrite.st" method="post" id="insertFrm" onsubmit="return check()" enctype="multipart/form-data">
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>분류</label> <select name="studyDivide" class="form-control">
									<option value="프로그래밍">프로그래밍</option>
									<option value="외국어">외국어</option>
									<option value="자격증">자격증</option>
									<option value="취미">취미</option>
									<option value="자기계발">자기계발</option>
									<option value="기타">기타</option>
								</select>
							</div>
							<div class="form-group col-sm-8">
								<label>스터디명</label> <input type="text" name="studyName" id="studyName" class="form-control" maxlength="50">
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>참가인원</label> <select name="studyPerson" class="form-control">
									<option value="1">1명</option>
									<option value="2">2명</option>
									<option value="3">3명</option>
									<option value="4">4명</option>
									<option value="5">5명</option>
									<option value="6">6명</option>
									<option value="7">7명</option>
									<option value="8">8명</option>
									<option value="9">9명</option>
									<option value="99">제한없음</option>
								</select>
							</div>
							<div class="form-group col-sm-4">
								<label>기간</label> <input type="text" name="studyDate" class="form-control" maxlength="20">
							</div>
							<div class="form-group col-sm-4">
								<label>시간대</label> <input type="text" name="studyTime" class="form-control" maxlength="20">
							</div>
						</div>
						<div class="form-row">
							<div class="form-group col-sm-4">
								<label>지역</label> <select name="studyArea" class="form-control">
									<option value="서울">서울</option>
									<option value="경기">경기</option>
									<option value="강원">강원</option>
									<option value="충북">충북</option>
									<option value="전남">전남</option>
									<option value="경북">경북</option>
									<option value="경남">경남</option>
									<option value="부산">부산</option>
									<option value="기타">기타</option>
								</select>
							</div>
							<div class="form-group col-sm-8">
								<label>주소</label> 
								<input type="text" name="studyAdress" id="studyAdress" class="form-control" maxlength="40">
							</div>
                                <input id="pac-input" class="controls form-control" type="text" placeholder="장소 검색" style="width: 300px; top: 10px;">
						</div>
						<div class="form-group">
							<label>장소지정</label>
							<div id="map"></div>
							<div id="infowindow-content">
								<img id="place-icon" src="" height="16" width="16"> <span id="place-name" class="title"></span><br> Place ID <span id="place-id"></span><br> <span id="place-address"></span>
							</div>
						</div>
						<div class="form-group">
							<label id="textval"><i class="fas fa-map-marker-alt" style="font-size: 15px; margin-right: 5px; vertical-align: 1px;"></i>스터디 장소를 지정해 주세요. </label>
						</div>
						<div class="form-group">
							<label>제목</label> <input type="text" name="studyTitle" class="form-control" maxlength="30">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea type="text" name="studyContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
						</div>
						<div class="modal-footer">
							<span class="btn btn-default btn-file"><input type="file" name="studyImage">그룹 이미지 파일</span>
							<button type="button" class="btn btn-default" data-datamiss="modal">취소</button>
							<button type="submit" class="btn btn-primary">등록</button>
							<input type="hidden" id="mapData" name="mapData">
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>



<script type="text/javascript">
function reportSubmit(){
  if ( $('#reportTitle').val() == '' ) {
    alert('제목을 입력해 주세요.');
    return false;
  } else if ( $('#reportContent').val() == '' ) {
    alert('내용을 입력해 주세요.');
    return false;
    }
}
</script>


	<!-- 신고버튼 모달팝업창 -->
	<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: auto;">
				<div class="modal-header">
					<h4 class="modal-title" id="modal" style="font-weight: bold;">신고하기</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" style="height: 400px;">
					<form action="StudyMailSend" method="post" onsubmit="return reportSubmit();">
						<div class="form-group">
							<label>제목</label> <input type="text" name="reportTitle" id="reportTitle" class="form-control" maxlength="30">
						</div>
						<div class="form-group">
							<label>내용</label>
							<textarea type="text" name="reportContent" id="reportContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-datamiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">신고</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>


</div>


<script>
      function initMap() {
        
        var lat = 35.16273177463202;
        var lng = 129.04514129669803;
        
        
   /*      $.ajax({
          type : "POST",
          url:"MapGetData",
          async: false,
          success:function(data){
            if( data != null ) {
            var mappoint = data.split(",");
            lat = Number(mappoint[0]);
            lng = Number(mappoint[1]);
            console.log(typeof lat);
            console.log(typeof lng);
            console.log(lat+" "+lng);
            } else {
              lat = 35.16273177463202;
              lng = 129.04514129669803;
              console.log(typeof lat);
              console.log(typeof lng);
              console.log(lat+" "+lng);
            }
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

 function initAutocomplete() {
        
   var lat = 35.16273177463202;
   var lng = 129.04514129669803;
   
   
/*         var lat;
        var lng;
        $.ajax({
          type : "POST",
          url:"MapGetData",
          async: false,
          success:function(data){
            if( data != null ) {
            var mappoint = data.split(",");
            lat = Number(mappoint[0]);
            lng = Number(mappoint[1]);
            console.log(typeof lat);
            console.log(typeof lng);
            console.log(lat+" "+lng);
            } else {
              lat = 35.16273177463202;
              lng = 129.04514129669803;
              console.log(typeof lat);
              console.log(typeof lng);
              console.log(lat+" "+lng);
            }
          }
        }); */
        
        
        
        var map = new google.maps.Map(document.getElementById('map'), {
          center: {lat: lat, lng: lng},
          zoom: 13,
         /*  mapTypeId: 'roadmap' */
        });

        // Create the search box and link it to the UI element.
        var input = document.getElementById('pac-input');
        var searchBox = new google.maps.places.SearchBox(input);
        map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

        // Bias the SearchBox results towards current map's viewport.
        map.addListener('bounds_changed', function() {
          searchBox.setBounds(map.getBounds());
        });

        var markers = [];
        // Listen for the event fired when the user selects a prediction and retrieve
        // more details for that place.
        searchBox.addListener('places_changed', function() {
          var places = searchBox.getPlaces();

          if (places.length == 0) {
            return;
          }

          // Clear out the old markers.
          markers.forEach(function(marker) {
            marker.setMap(null);
          });
          markers = [];

          // For each place, get the icon, name and location.
          var bounds = new google.maps.LatLngBounds();
          places.forEach(function(place) {
            if (!place.geometry) {
              console.log("Returned place contains no geometry");
              return;
            }
            var icon = {
              url: place.icon,
              size: new google.maps.Size(10, 10),
              origin: new google.maps.Point(0, 0),
              anchor: new google.maps.Point(0, 0),
              scaledSize: new google.maps.Size(25, 25)
            };

            // Create a marker for each place.
            markers.push(new google.maps.Marker({
              map: map,
              icon: icon,
              title: place.name,
              position: place.geometry.location
            }));

            if (place.geometry.viewport) {
              // Only geocodes have viewport.
              bounds.union(place.geometry.viewport);
            } else {
              bounds.extend(place.geometry.location);
            }
          });
          map.fitBounds(bounds);
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
        
        
        $.ajax({
          type:"POST",
          url:"MapData",
          async: false,
          data:{mapvalue: encodeURIComponent(event.latLng)},
          success:function(data){
            
          }
        });
        
        // If the event has a placeId, use it.
        if (event.placeId) {
          console.log('You clicked on place:' + event.placeId);

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
    
    <script src="https://maps.googleapis.com/maps/api/js?key=GOOGLE_API_KEY&libraries=places&callback=initAutocomplete"
         async defer></script>




