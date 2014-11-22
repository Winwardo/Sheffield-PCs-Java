<script src="js/d3.v3.min.js" charset="utf-8"></script>
<script src="js/nv.d3.min.js" charset="utf-8"></script>
<script src="js/jquery-2.1.1.min.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css" href="css/nv.d3.css">

<div id="chart">
	<svg></svg>
</div>

<script>
	$(function() {
		nv.addGraph(function() {
			var chart = nv.models.stackedAreaChart();
			
			var defaultList = [95, 96, 97, 100, 102];
			var data = [];
			
			for (var i = 0; i < defaultList.length; ++i) {
				var thing = $.ajax({
			        type: "GET",
			        url: "api/scraper/get/nvd3/" + defaultList[i],
			        cache: false,
			        async: false
			    }).responseText;
				data.push(JSON.parse(thing));
			}
			
			chart.x(function(d) {
				return d.timeStamp;
			}).y(function(d) {
				return d.current;
			});

			chart.xAxis.showMaxMin(true).tickFormat(function(d) {
				return d3.time.format('%a, %H:%M')(new Date(d));
			});

			chart.yAxis.tickFormat(d3.format('d'));
			d3.select('#chart svg').datum(data).transition().duration(500)
					.call(chart);

			nv.utils.windowResize(chart.update);
			return chart;
		});
	});
</script>