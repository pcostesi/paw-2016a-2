require(['jquery', 'duration'], function() {
	
	$(document).ready(function(){
		$("#beginDate").attr('value', this.value);
		$("#duration").trigger("change");
	});
	
	$('#beginDate').on("dp.change", function(e) {
		$("#beginDate").attr('value', this.value);
		$("#duration").trigger("change");
    });
	
	$('#duration').on('change', function(){
		var duration = 0;
		switch ($("#duration").val()) {
		case "1WEEKS":
			duration = 7;
			break;
		case "2WEEKS":
			duration = 14;
			break;
		case "3WEEKS":
			duration = 21;
			break;
		case "4WEEKS":
			duration = 28;
			break;
		}
		if (duration == 0) {
			$("#endDate").prop('disabled', false);
		} else {
			$("#endDate").prop('disabled', true);
			var beginDateStr = $("#beginDate").val().split('/');
			var beginDate = new Date(beginDateStr[2],beginDateStr[1]-1,beginDateStr[0]);
			$("#endDate").val(calculateEndDate(beginDate, duration));
		}		
	});
	
	$("#iteration-form").submit(function(event){
		$("#endDate").prop('disabled', false);
	});
	
	function calculateEndDate(date, duration) {
		date.setDate(date.getDate() + duration);
		var day = date.getDate();
		if (day < 10) {
			day = "0" + day;
		}
		var month = date.getMonth()+1;
		if (month < 10) {
			month = "0" + month;
		}
		var year = date.getFullYear();
		return day + "/" + month + "/" + year;
	}
	
});