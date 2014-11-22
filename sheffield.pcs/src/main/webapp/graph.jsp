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
			var chart;
			chart = nv.models.stackedAreaChart();

			var data = [ {
				"values" : [ {
					"buildingId" : 86,
					"current" : 39,
					"timeStamp" : 1416611524503
				}, {
					"buildingId" : 86,
					"current" : 39,
					"timeStamp" : 1416610491750
				}, {
					"buildingId" : 86,
					"current" : 39,
					"timeStamp" : 1416610395896
				} ],
				"key" : "Building A"
			}, {
				"values" : [ {
					"buildingId" : 85,
					"current" : 12,
					"timeStamp" : 1416611524503
				}, {
					"buildingId" : 85,
					"current" : 12,
					"timeStamp" : 1416610491750
				}, {
					"buildingId" : 85,
					"current" : 12,
					"timeStamp" : 1416610395896
				} ],
				"key" : "The IC!"
			} ];

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