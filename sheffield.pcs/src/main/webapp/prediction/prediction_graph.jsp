<%@ page import="java.util.*, data.pcs.*, com.google.gson.Gson"%>
<div id="chart">
	<svg></svg>
</div>

<%
String today = String.format("%tFT%<tRZ",
        Calendar.getInstance(TimeZone.getTimeZone("Z")));
//today = "2015-10-20T23:00:00";
%>

<script>
	$(function() {		
		function drawGraph(predictions) {
			nv.addGraph(function() {
				var chart = nv.models.lineChart();
				chart.useInteractiveGuideline(true);
				chart.rightAlignYAxis(true);
				chart.margin({
					left : 20,
					right: 30
				});
				chart.forceY([0, 100]);
	
				var data1 = predictions;	
				
				chart.x(function(d) {
					return d[0];
				}).y(function(d) {
					return d[1];
				});
	
				var rightNow = new Date();
				var hourGaps = 4;
				var width = $(window).width();
				if (width > 550) {
					hourGaps = 3;
				}
				if (width > 800) {
					hourGaps = 2;
				}
				
				var oneHour = 3600000;
				
				var hours = Math.floor(rightNow.getHours() / hourGaps) * hourGaps; // Round to the axis
				
				rightNow.setHours(hours, 0, 0);
				var now = rightNow.getTime();
				
				var gap = oneHour * hourGaps;
				
				chart.xAxis.showMaxMin(true)
					.tickValues([now - gap*13, now - gap*12, now - gap*11, now - gap*10, now - gap*9, now - gap*8, now - gap*7, now - gap*6, now - gap*5, now - gap*4, now - gap*3, now - gap*2, now - gap, now])
					.tickFormat(function(d) {
					return d3.time.format('%H:%M')(new Date(d));
				});
	
				chart.yAxis.tickFormat(d3.format('d'));
	
				var data = [{
					key : "IC Level 1",
					values : data1.predictions.map(function(x,i) { return [i, x]; }),
					area: true
				}]
				
				console.log(data);
	
				chart.interpolate("basis");
				
				d3.select('#chart svg').datum(data).transition().duration(0)
						.call(chart);
	
				nv.utils.windowResize(function() {
					$("#chart").height($("#chart").width() / 1.618);
					chart.update();
				});
				$(window).resize();
	
				return chart;
			});
		}; //drawGraph
		
		console.log("yay3")
		
		var requestData = {
		    "hour": 1,
		    "current": 50,
		    "dayofweek": 6,
		    "month": 2,
		    "pastValues": {"19": 80, "21": 83, "10": 48, "11": 54, "12": 51, "13": 45, "14": 46, "15": 57, "16": 62, "17": 69, "18": 76, "20": 81, "23": 83, "22": 83, "2": 82, "3": 79, "0": 83, "1": 83, "6": 66, "7": 60, "4": 76, "5": 71, "8": 60, "9": 53}
		};
		
		requestData = <%=PredictionRequest.getOrMake(today, 14).jsonBlock()%>;
		
		var predAjax = $.ajax({
			url: 'https://sheffieldpredictions-topherio.rhcloud.com/things',
			type: 'POST',
		    //crossDomain: true,
		    dataType: 'json',
			data: JSON.stringify(requestData),
			success: function(result) {
				console.log("Success");
				drawGraph(result);
			}
		});
	});
</script>