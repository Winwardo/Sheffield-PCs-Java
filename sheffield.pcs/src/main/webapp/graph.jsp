<div id="chart">
	<svg></svg>
</div>

<script>
	$(function() {
		nv.addGraph(function() {
			var chart = nv.models.stackedAreaChart().useInteractiveGuideline(
					true).rightAlignYAxis(true);

			var defaultList = [ <% System.out.println( request.getParameter("buildings") ); out.println( request.getParameter("buildings") );%> ];
			var data = [];

			for (var i = 0; i < defaultList.length; ++i) {				
				if (defaultList[i] == null) { continue; }
				
				var thing = $.ajax({
					type : "GET",
					url : "api/scraper/get/nvd3/" + defaultList[i],
					cache : false,
					async : false
				}).responseText;
				var datum = JSON.parse(thing);
				
				if (datum.values.length == 0) { continue; }				
				data.push(datum);
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