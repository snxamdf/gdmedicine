//初始化：点击开始设置
$(function() {
	var i = 0;
	$(".chshi_shm a").live("click", function() {
		if (i > 0) {
			$(this).removeAttr("href");
		}
		i++;
	})
});

// 基本信息：点击下一步
$(function() {
	var isCommitted = false;// 表单是否已经提交标识，默认为false
	$("#initialOne").submit(function(e) {
		if (isCommitted == false) {
			isCommitted = true;// 提交表单后，将表单是否已经提交标识设置为true
			return true;// 返回true让表单正常提交
		} else {
			e.preventDefault();
		    //alert("Submit prevented");
		}
	});
});

//初始化第二步科目设置：点击下一步
$(function() {
	var i = 0;
	$(".step_a .input_ui-btn_grey").live("click", function() {
		if (i > 0) {
			$(this).removeAttr("href");
		}
		i++;
	})
});

//新增科目：点击确定
$(function() {
	var isCommitted = false;// 表单是否已经提交标识，默认为false
	$("#initialOne").submit(function(e) {
		if (isCommitted == false) {
			isCommitted = true;// 提交表单后，将表单是否已经提交标识设置为true
			return true;// 返回true让表单正常提交
		} else {
			e.preventDefault();
		    //alert("Submit prevented");
		}
	});
});

