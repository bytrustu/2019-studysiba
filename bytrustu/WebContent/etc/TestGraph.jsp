<%@page import="com.bytrustu.member.model.MemberDAO"%>
<%@page import="java.io.PrintWriter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<link href="css/chatcustom.css" rel="stylesheet">
<link href="css/bootstrap.css" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Nanum+Gothic" rel="stylesheet">
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>




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
          chartArea:{left:25,top:10,width:"80%",height:"80%"}
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));

        chart.draw(data, options);
      }
    </script>

</head>


<body>
	<div id="curve_chart" style="width: 280px; height: 210px"></div>
</body>





</html>


