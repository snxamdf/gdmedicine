$.validator.setDefaults({
	submitHandler: function(form) {
		$("#messageBox").removeClass("show");
		$("#messageBox").addClass("hidden");
		form.submit();
	},
	highlight: function(element) {
		$(element).closest('.field').addClass("error");
	},
	unhighlight: function(element) {
		$(element).closest('.field').removeClass("error");
		$(element).qtip('destroy');
	},
	errorContainer: "#messageBox",
	errorPlacement: function(error, element) {
		$("#messageBox").removeClass("hidden");
		$("#messageBox").addClass("show");
		$("#messageBox").children("strong").text("输入有误，请先更正。");
		$(element).qtip({
			position: {
				my: 'bottom left',
				at: 'top center'
			},
			style: {
				classes: 'qtip-red'
			},
			content: $(error).text()
		});
	}
});