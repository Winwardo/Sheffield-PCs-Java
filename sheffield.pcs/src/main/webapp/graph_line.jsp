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
		console.log(crunchedTimes);
		
		return crunchedTimes;
	}

	$(function() {
		nv.addGraph(function() {
			var chart = nv.models.lineChart();
			chart.useInteractiveGuideline(true);
			chart.rightAlignYAxis(true);
			//chart.showControls(false);
			chart.margin({
				left : 0
			});

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

			chart.xAxis.showMaxMin(true).tickFormat(function(d) {
				return d3.time.format('%a, %H:%M')(new Date(d));
			});

			chart.yAxis.tickFormat(d3.format('d'));

			var data = [ {
				key : "Information Commons",
				values : crunchData(buildingsData_IC),
				area: true
			}, {
				key : "Mappin Building",
				values : crunchData(buildingsData_Mappin),
				color: "#BAFA9D",
				area: true
			}, {
				key : "Western Bank",
				values : crunchData(buildingsData_Western),
				area: true
			} ]

			d3.select('#chart svg').datum(data).transition().duration(1000)
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