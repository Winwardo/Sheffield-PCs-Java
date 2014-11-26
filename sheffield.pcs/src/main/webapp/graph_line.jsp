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
		
		return crunchedTimes.sort().reverse();
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

			var url = "api/scraper/ic/buildings";
			var jsonS = $.ajax({
				type : "GET",
				url : url,
				cache : true,
				async : false
			}).responseText;
			var ICdata = JSON.parse(jsonS);
						

			chart.x(function(d) {
				return d.timeStamp;
			}).y(function(d) {
				return d.current;
			});

			chart.xAxis.showMaxMin(true).tickFormat(function(d) {
				return d3.time.format('%a, %H:%M')(new Date(d));
			});

			chart.yAxis.tickFormat(d3.format('d'));

			if ($(window).width() < 1024) {
				chart.showLegend(false);
			}

			var data = [ {
				key : "IC",
				values : crunchData(ICdata)
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