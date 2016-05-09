require(['moment', 'datetimepicker-js', 'css!datetimepicker-css'], function() {
	$('.dateInput').datetimepicker({
	    format: "DD/MM/YYYY"
	});
});