<div id="chart">
	<svg></svg>
</div>

<script>
	$(function() {
		nv.addGraph(function() {
			var chart = nv.models.stackedAreaChart().useInteractiveGuideline(
					true).rightAlignYAxis(true).showControls(false);

			var url = "api/scraper/get/nvd3_many/<%=request.getParameter("buildings")%>";
			var jsonS = $.ajax({
				type : "GET",
				url : url,
				cache : true,
				async : false
			}).responseText;
			
			var data = JSON.parse(jsonS);
			
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