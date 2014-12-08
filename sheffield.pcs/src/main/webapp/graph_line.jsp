<div id="chart">
	<svg></svg>
</div>

<script>
	function crunchData(dataIn) {
		var crunchedTimesObject = {};
		var crunchedTimes = [];
		for (var i = 0; i < dataIn.length; ++i) {
			var building = dataIn[i];
			for (var j = 0; j < building.pcsInfo.length; ++j) {
				var current = building.pcsInfo[j];
				var key = "t_" + current.timeStamp;
				crunchedTimesObject[key] = crunchedTimesObject[key] || {timeStamp: current.timeStamp, current: 0}; // Set a default value
				crunchedTimesObject[key].current += current.current;
			}
		}
		
		var building1 = dataIn[0];
		for (var i = 0; i < building1.pcsInfo.length; ++i) {
			var current = building.pcsInfo[i];
			var key = "t_" + current.timeStamp;
			var val = crunchedTimesObject[key];

			crunchedTimes.push({timeStamp: val.timeStamp, current: val.current});
		}
		
		// Sort
		var crunchedTimes = _.sortBy(crunchedTimes, function(data) { return data.timeStamp });
		
		return crunchedTimes;
	}

	$(function() {
		nv.addGraph(function() {
			var chart = nv.models.lineChart();
			chart.useInteractiveGuideline(true);
			chart.rightAlignYAxis(true);
			chart.margin({
				left : 20,
				right: 30
			});
			chart.forceY([0, 450]);

			var buildingsUrl_IC = "api/scraper/ic/buildings";
			var buildingJson_IC = $.ajax({
				type : "GET",
				url : buildingsUrl_IC,
				cache : true,
				async : false
			}).responseText;
			var buildingsData_IC = JSON.parse(buildingJson_IC);
						
			var buildingsUrl_Mappin = "api/scraper/mappin/buildings";
			var buildingJson_Mappin = $.ajax({
				type : "GET",
				url : buildingsUrl_Mappin,
				cache : true,
				async : false
			}).responseText;
			var buildingsData_Mappin = JSON.parse(buildingJson_Mappin);
			
			var buildingsUrl_Western = "api/scraper/western/buildings";
			var buildingJson_Western = $.ajax({
				type : "GET",
				url : buildingsUrl_Western,
				cache : true,
				async : false
			}).responseText;
			var buildingsData_Western = JSON.parse(buildingJson_Western);
			
			
			chart.x(function(d) {
				return d.timeStamp;
			}).y(function(d) {
				return d.current;
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

			var data = [ {
				key : "IC",
				values : crunchData(buildingsData_IC),
				area: true
			}, {
				key : "Mappin",
				values : crunchData(buildingsData_Mappin),
				color: "hsl(120, 65%, 75%)",
				area: true
			}, {
				key : "Western Bank",
				values : crunchData(buildingsData_Western),
				area: true
			} ]

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
	});
</script>