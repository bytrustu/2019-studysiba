<%@ page import="java.io.File"%>
<%@ page import="java.io.*"%>
<%@page import="com.bytrustu.study.model.GroupFileDTO"%>
<%@page import="com.bytrustu.study.model.StudyDTO"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


<style>

/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
		width: 250px;
		height: 150px;
		float: left;
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
			if (session.getAttribute("userID") != null) {
				userID = (String) session.getAttribute("userID");
			}
			ArrayList<HashMap<String, Object>> groupList = new ArrayList<HashMap<String, Object>>();
			groupList = (ArrayList<HashMap<String, Object>>) request.getAttribute("groupList");
			StudyDTO content = new StudyDTO();
			content = (StudyDTO) request.getAttribute("content");

			int pageNum = (Integer) request.getAttribute("pageNum");
			if (pageNum == 0) {
				pageNum = 1;
			}

			int totalPage = (Integer) request.getAttribute("totalPage");
			if (totalPage == 0) {
				totalPage = 1;
			}

			ArrayList<GroupFileDTO> fileList = new ArrayList<GroupFileDTO>();
			if (request.getAttribute("fileList") != null) {
				fileList = (ArrayList<GroupFileDTO>) request.getAttribute("fileList");
			}
%>



<script type="text/javascript">
  
    //메세지 출력 : selector(메세지타입) , delay(보여지는 시간)
    function autoClosingAlert(selector, delay) {
        var alert = $(selector).alert();
        alert.show();
        window.setTimeout(function() {
            alert.hide()
        }, delay);
    }

    // 메세지 정보 처리
    function submitFunction() {
        var userID = '<%=userID%>';
        var gNum = '<%=content.getgNum()%>';
        var chatContent = $('#chatContent').val();
        $.ajax({
            type : "POST",
            url : "StudyChatSubmitAction",
            data : {
                userID : encodeURIComponent(userID),
                gNum : encodeURIComponent(gNum),
                chatContent : encodeURIComponent(chatContent),
            },
            success : function(result) {
                if (result == 1) {
                    autoClosingAlert('#successMessage', 2000);
                } else if (result == 0) {
                    autoClosingAlert('#dangerMessage', 2000);
                } else {
                    autoClosingAlert('#warningMessage', 2000);
                }
            }
        });
        $('#chatContent').val('');
    }

    // 그룹 채팅 정보를 가져온다.
    var lastID = 0;
    function chatContentListFunction(type) {
        var userID = '<%=userID%>';
        var gNum = '<%=content.getgNum()%>';
        $.ajax({
            type : "POST",
            url : "StudyChatViewAction",
            data : {
              gNum : encodeURIComponent(gNum),
              listType : type
            },
            success : function(data) {
                if (data == "") {
                    return;
                }
                $('#chatList').html('');
                var parsed = JSON.parse(data);
                var result = parsed.result;
                for (var i = 0; i < result.length; i++) {
                    if (result[i][0].value == userID) {
                        result[i][0].value = '나';
                    }
                    addChat(result[i][0].value, result[i][1].value, result[i][2].value, result[i][3].value);
                }
                lastID = Number(parsed.last);
            }
        });
    }

    // 그룹 채팅에 추가될 html
    function addChat(chatName, chatContent, chatTime, fileName) {
        if (fileName == 'null') {
            fileName = 'siba-default.gif';
        }
        $('#chatList').append('<div class="row">' +
            '<div class="col-lg-12">' +
            '<div class="media">' +
            '<a class="pull-left" href="#">' +
            '<img class="media-object img-circle" src="/local_img/' + fileName + '" alt="" style="width:40px; height:40px;">' +
            '</a>' +
            '<div class="media-body">' +
            '<h4 class="media-heading"><b>' +
            chatName +
            '</b><span class="small pull-right">' +
            chatTime +
            '</span>' +
            '</h4>' +
            '<p>' +
            chatContent +
            '</p>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<hr>');
        $('#chatList').scrollTop($('#chatList')[0].scrollHeight);
    }

    // 지연시간
    function getInfiniteChat() {
        setInterval(function() {
            chatContentListFunction(lastID);
        }, 3000);
    }
</script>















</style>



<div class="studygroupwarp">

	<div class="position-ab basic-location1">
		<span class="subpage-title">스터디그룹</span>
	</div>


	<!-- 중간마진 -->
	<div class="subpage-margin"></div>

	<div id="grouplist-title">
		<p>
			<i class="far fa-comments"></i><span><%=groupList.get(0).get("groupName")%></span>
		</p>
	</div>


	<div class="subpage-margin2"></div>



	<section>
	<div id="groupcontent-warp">
		<div id="groupcontent-box1">

			<div id="contentgroup-warp">
				<div id="contentgroup-img1">
					<img class="img-rounded" src="/local_img/study/<%=groupList.get(0).get("image")%>" style="width: 300px; height: 150px; float: left;">
				</div>
				<div id="contentgroup-img2">
					<div id="map"></div>
					<div id="infowindow-content">
						<img id="place-icon" src="" height="16" width="16"> <span id="place-name" class="title"></span><br> Place ID <span id="place-id"></span><br> <span id="place-address"></span>
					</div>
				</div>

				<div id="contentgroup-text">
					<p>
						<i class="fas fa-map-marked-alt"></i>
						<%=groupList.get(0).get("address")%>
					</p>
					<p>
						<i class="fas fa-history"></i>
						<%=groupList.get(0).get("time")%>
					</p>
				</div>
			</div>



            <!-- 그룹 첨부자료 게시판 -->
			<div id="contentgroup-notice">

				<div class="position-ab basic-location3">
					<span class="notice-title">첨부자료</span>
				</div>

				<div id="notice-btn1">
					<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#registerFile">등록</a>
				</div>

				<div class="row" style="margin: 80px 0 0 0;"></div>


				<%
				  if (request.getAttribute("fileList") != null) {
								for (int i = 0; i < fileList.size(); i++) {
				%>
				<div class="row" style="margin: 10px 0 0 25px; padding: 8px; border: 1px solid #d4d4d4; border-radius: 10px; width: 90%;">
					<div class="col-3 text-left"><%=fileList.get(i).getgDate().substring(6,11).replace("-", "/")%></div>
					<div class="col-5 text-left"><%=fileList.get(i).getContent()%></div>
					<div class="col-4 text-left">
						<a href='FileDownloadAction.st?fileName=<%=fileList.get(i).getgFile()%>'><%=fileList.get(i).getgFile()%></a>
					</div>
				</div>
				<%
				  }
							}
				%>


				<div class="row margin-top3 margin-bottom1">
					<div class="col-12 text-center">

						<%
						  if (pageNum != 1) {
						%>
						<button type="button" class="btn btn-default" onclick="location.href='StudyGroupContentViewAction.st?gNum=<%=groupList.get(0).get("gNum")%>&pageNum=<%=pageNum - 1%>'">이전</button>
						<%
						  }
						%>
						<%
						  if (pageNum != totalPage) {
						%>
						<button type="button" class="btn btn-default" onclick="location.href='StudyGroupContentViewAction.st?gNum=<%=groupList.get(0).get("gNum")%>&pageNum=<%=pageNum + 1%>'">다음</button>
						<%
						  }
						%>
					</div>
				</div>



			</div>


		</div>
	</div>


	<div id="groupcontent-warp">
		<div id="groupcontent-box3">


			<div class="container bootstrap snippet" style="width: 90%; float: left; margin: 0 0 0 70px;">
				<div class="row">
					<div class="col-xs-12">
						<div class="portlet portlet-default">

							<div class="portlet-heading">
								<div class="portlet-title">
									<h4>
										<i class="fa fa-circle text-green" id="chatSubject" style="padding-right: 5px;"></i> ${param.toID} 그룹대화
									</h4>
								</div>
								<div class="clearfix"></div>
							</div>
							<div id="chat" class="panel-collapse collapse in">
								<div id="chatList" class="portlet-body chat-widget" style="overflow-y: auto; width: auto; height: 450px;"></div>
								<div class="portlet-footer">
									<div class="row" style="height: 45px;">
										<div class="form-group col-xs-8">
											<input type="text" style="padding: 10px; width: 300px;" id="chatContent" class="form-control" placeholder="메세지를 입력하세요" maxlength="100">
										</div>
										<div class="form-group col-xs-4">
											<button type="button" class="btn btn-default pull-right" onclick="location.href='ChatForm.ch'" style="margin-bottom: 10px; margin-left: 5px; float: left;">검색</button>
											<button type="button" class="btn btn-danger pull-right" onclick="submitFunction()" style="margin-bottom: 10px; float: left; margin-left: 5px;">전송</button>
											<div class="clearfix"></div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="alert alert-success" id="successMessage" style="display: none;">
					<strong>메세지 전송에 성공했습니다.</strong>
				</div>
				<div class="alert alert-danger" id="dangerMessage" style="display: none;">
					<strong>내용을 입력해주세요.</strong>
				</div>
				<div class="alert alert-warning" id="warningMessage" style="display: none;">
					<strong>데이터베이스 오류가 발생했습니다.</strong>
				</div>



			</div>

		</div>
	</div>

	</section>




	<!-- 첨부자료 등록 모달팝업창 -->
	<div class="modal fade" id="registerFile" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: auto;">
				<div class="modal-header">
					<h4 class="modal-title" id="modal" style="font-weight: bold;">첨부자료</h4>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body" style="height: 200px;">
					<form action="StudyGroupFileWriteAction.st?gNum=<%=content.getgNum()%>" method="post" enctype="multipart/form-data">
						<div class="form-group">
							<label>메모</label> <input type="text" name="groupContent" class="form-control" maxlength="30">
						</div>
						<div class="modal-footer">
							<span class="btn btn-default btn-file"><input type="file" name="gFile">첨부파일</span>
							<button type="button" class="btn btn-default" data-datamiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">등록</button>
						</div>
					</form>
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
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyByjX-fIiEVgNTofLuWWpxgGqQADaoNSWk&libraries=places&callback=initMap" async defer>
    </script>



<script type="text/javascript">
        $(document).ready(function() {
    
            chatContentListFunction('view');
            getInfiniteChat();
        });
    </script>
