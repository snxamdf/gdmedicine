/*<![CDATA[*/
$(function (){
	fnTab();
	function fnTab(){
		var oTab = $('.tab'),sEvent = 'click';
		oTab.each(function (){
			var aTabLi = $('.tab-hd li', this),
				aTabCon = $('.tab-con', this);
			aTabLi[sEvent](function (){
				var i = $(this).index();
				aTabLi.removeClass('current').eq(i).addClass('current');
				aTabCon.hide().eq(i).show();
				return false;
			});
		});
	}
	
	function GetQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return 0;
	}
	
	var tabOrder = GetQueryString("tagId");
	$('#tabs li').eq(tabOrder).trigger("click");
	//增加新科目
	$('.subjectAdd').click(function(e){
		e.preventDefault();
		//获取当前行科目数据
		var curTr = $(this).closest("tr");
		var curSub = curTr.find('.subjectName').text();
		var curSubCode = curTr.find('.subjectCode').text();
		//父科目id
		var curSubId = curTr.find('input[name="subjectid"]').val();
		var type = curTr.find('input[name="type"]').val();
		var isLoss = curTr.find('input[name="isLoss"]').val();
		var isAdd = curTr.find('input[name="isAdd"]').val();
		var isShow = curTr.find('input[name="isShow"]').val();
		var direction = curTr.find('input[name="direction"]').val();
		var leaf = curTr.find('input[name="leaf"]').val();
		var grade = curTr.find('input[name="grade"]').val();
		var genreId = curTr.find('input[name="genreId"]').val();
		var subTypes = curTr.find('input[name="subTypes"]').val();
		var subCode ;
		//弹出新增科目界面标识
		var showFlag = false;
		//从后台获取科目编号
		$.ajax({
			type: 'POST',
			url: ctl.path +'/subj/subject/getSubjCode',
			async: false,
			data: {
				"booksId" : $("input[name='booksId']").val(),
				"psubjId" : curSubId,
				"_csrf" : $("input[name='_csrf']").val()
			},
			dataType: 'json',
			success: function(res){
				if(res.status == 200){
					subCode = res.data;
					showFlag = true;
				}else{
					$.dialog({
						title : '系统提示',
						content : 'url:' + ctl.path + '/dialog?content=' + encodeURIComponent(encodeURIComponent(res.message)),
						data:{},
						width : 435,
						height : 130,
						max : false,
						min : false,
						cache : false,
						lock: true,
						ok: function(){
						},
						cancel: false,
						cancelVal: '确定'
					});
				}
				//window.location.href="/";
			}
		});
		if(showFlag){
			$(".tab-bd .subject").remove();
			//加载科目类别
			$("#subj_genreId_add").empty().append(subTypes);
			$("#subj_genreId_add option[value='"+genreId+"']").attr("selected", "selected"); 
			$('#subjectAdd input[name="code"]').val(subCode);
			$('#subjectAdd input[name="subject_prev"]').val(curSubCode+' '+curSub);
			$('#subjectAdd input[name="parentId"]').val(curSubId);
			$('#subjectAdd input[name="parentLeaf"]').val(leaf);
			$('#subjectAdd input[name="type"]').val(type);
			$('#subjectAdd input[name="isLoss"]').val(isLoss);
			$('#subjectAdd input[name="isAdd"]').val(isAdd);
			$('#subjectAdd input[name="isShow"]').val(isShow);
			$('#subjectAdd input[name="grade"]').val(grade);
			$('#subjectAdd input[name="direction"][value='+direction+']').attr("checked",true);
			var addHtml = $('#subjectAdd tr').clone(true);
			addHtml.insertAfter(curTr);
		}
	});
	//点击保存按钮
	var addFlag = false;
	$("#addSave").live('click', function(e){
		e.preventDefault();
		//验证科目名称必填
		if($("#subj_name_add").val() == ''){
			$.dialog.tips("科目名称不能为空");
			return;
		}
		if($("#subj_name_add").val().length > 30 || $("#subj_name_add").val().length < 2){
			$.dialog.tips("科目名称长度为2个字符或汉字 至 30个字符或汉字");
			return;
		}
		// 提示弹窗
		var leaf = $('#subjectAdd input[name="parentLeaf"]').val();
		var grade = $('#subjectAdd input[name="grade"]').val();
		//三级科目不能新增子科目
		if(grade == 3){
			$.dialog.tips("不允许增加下级科目！");
			return;
		}
		var pid = $('#subjectAdd input[name="parentId"]').val();
		var initFlag = false;
		//如果是叶子节点，有如下提示
		if(leaf == 1){
			//如果科目余额已被初始化，给予相应提示
			$.ajax({
				type: 'POST',
				async: false,
				url: ctl.path +'/subj/balance/hasInitBal',
				data: {
					"subjectId":pid,
					"_csrf" : $("input[name='_csrf']").val()
					},
				dataType: 'json',
				success: function(res){
					if(res.data){
						initFlag = true;
					}
				}
			});
			//余额以及初始化给出相应提示信息
			if(initFlag){
				var subjName = $('#subjectAdd input[name="subject_prev"]').val();
				var msg = '您正在为科目【'+subjName+'】增加一个下级科目，系统将把该科目的数据全部转移到新增的下级科目中，您是否继续？';
				$.dialog({
					title : '系统提示',
					content : 'url:' + ctl.path + '/dialog?content=' + encodeURIComponent(encodeURIComponent(msg)),
					width : 455,
					height : 180,
					max : false,
					min : false,
					cache : false,
					lock : true,
					cancel : true,
					ok : function(data) {
						$.ajax({
							type: 'POST',
							//url: 'php/init_subject_add.php',
							url: ctl.path +'/subj/subject/addSubj',
							data: $('#form-subjectAdd').serialize(),
							dataType: 'json',
							success: function(res){
								$("input[name='_token ']").val(res.token);
								if(res.status == 200){
									var curTag = $('#tabs li.current').index();
									var backUrl = "&tagid=" + curTag;
									window.location.href="/subj/subject/setSubj?booksId="+res.data+backUrl;
								}
								$.dialog.tips(res.message);
							}
						});
					},
					okVal : '确定',
					cancelVal : '取消'
				});
			}else{
				if(!addFlag){
					addFlag = true;
					$.ajax({
						type: 'POST',
						//url: 'php/init_subject_add.php',
						url: ctl.path +'/subj/subject/addSubj',
						data: $('#form-subjectAdd').serialize(),
						dataType: 'json',
						success: function(res){
							$("input[name='_token ']").val(res.token);
							if(res.status == 200){
								var curTag = $('#tabs li.current').index();
								var backUrl = "&tagid=" + curTag;
								window.location.href="/subj/subject/setSubj?booksId="+res.data+backUrl;
							}else{
								addFlag = false;
							}
							$.dialog.tips(res.message);
						},
						complete : function (XMLHttpRequest, textStatus) {
							if(textStatus != 'success'){
								addFlag = false;
							}
						}
					});
				}
			}
		}else{
			if(!addFlag){
				addFlag = true;
				$.ajax({
					type: 'POST',
					//url: 'php/init_subject_add.php',
					url: ctl.path +'/subj/subject/addSubj',
					data: $('#form-subjectAdd').serialize(),
					dataType: 'json',
					success: function(res){
						$("input[name='_token ']").val(res.token);
						if(res.status == 200){
							var curTag = $('#tabs li.current').index();
							var backUrl = "&tagid=" + curTag;
							window.location.href="/subj/subject/setSubj?booksId="+res.data+backUrl;
						}else{
							addFlag = false;
						}
						$.dialog.tips(res.message);
					},
					complete : function (XMLHttpRequest, textStatus) {
						if(textStatus != 'success'){
							addFlag = false;
						}
					}
				});
			}
		}
	});
	
	$("#addClose").live('click', function(e){
		e.preventDefault();
		$(".tab-bd .subjectAddLine").remove();
	});
	//修改科目
	$('.subjectFix').click(function(e){
		e.preventDefault();
		$(".tab-bd .subject").remove();
		var curTr = $(this).closest("tr");
		//当前科目名称
		var curSub = curTr.find('.subjectName').text();
		var curCode = curTr.find('.subjectCode').text();
		var curSubFname = curTr.find('input[name="fullname"]').val();
		var direction = curTr.find('input[name="direction"]').val();
		var genreId = curTr.find('input[name="genreId"]').val();
		var subTypes = curTr.find('input[name="subTypes"]').val();
		var isDefault = curTr.find('input[name="isDefault"]').val();
		//当前科目id
		var curSubId = curTr.find('input[name="subjectid"]').val();
		var arr = curSubFname.split("/");
		var len = arr.length;
		//加载科目类别
		$("#subj_genreId_fix").empty().append(subTypes);
		$("#subj_genreId_fix option[value='"+genreId+"']").attr("selected", "selected"); 
		$('#subjectFix input[name="subject_prev"]').val(arr[len-2]);
		$('#subjectFix input[name="name"]').val(curSub);
		$('#subjectFix input[name="code"]').val(curCode);
		$('#subjectFix input[name="id"]').val(curSubId);
		$('#subjectFix input[name="isDefault"]').val(isDefault);
		$('#subjectFix input[name="direction"][value='+direction+']').attr("checked",true);
		var addHtml = $('#subjectFix tr').clone(true);
		addHtml.insertAfter(curTr);
		$('.subjectCopy').eq(0).attr("id","form-subjectFix");
	});
	
	var fixFlag = false;
	$("#fixSave").live('click', function(e){
		e.preventDefault();
		//验证科目名称必填
		if($("#subj_name_fix").val() == ''){
			$.dialog.tips("科目名称不能为空");
			return;
		}
		if($("#subj_name_fix").val().length > 30 || $("#subj_name_fix").val().length < 2){
			$.dialog.tips("科目名称长度为2个字符至30个字符之间");
			return;
		}
		var isDefault = $('#subjectFix input[name="isDefault"]').val();
		if(isDefault == 1){
			$.dialog.tips("系统科目不能修改！");
			return;
		}
		if(!fixFlag){
			fixFlag = true;
			$.ajax({
				type: 'POST',
				url: ctl.path +'/subj/subject/updateSubj',
				data: $('#form-subjectFix').serialize(),
				dataType: 'json',
				success: function(res){
					$("input[name='_token']").val(res.token);
					if(res.status == 200){
						if(res.success){
							var curTag = $('#tabs li.current').index();
							var backUrl = "&tagid=" + curTag;
							window.location.href="/subj/subject/setSubj?booksId="+res.data+backUrl;
						}else{
							fixFlag = false;
						}
					}else{
						fixFlag = false;
					}
					$.dialog.tips(res.message);
				},
				complete : function (XMLHttpRequest, textStatus) {
					if(textStatus != 'success'){
						fixFlag = false;
					}
				}
			});
		}
		
	});
	$("#fixClose").live('click', function(e){
		e.preventDefault();
		$(".tab-bd .subjectFixLine").remove();
	});
	//删除科目
	$(".subjectDel").live('click', function(e){
		e.preventDefault();
		var subjectId = $(this).closest("tr").find('input[name="subjectid"]').val();
		var curSubject =$(this).closest("tr").find('.subjectName').text();
		var _this = $(this).closest("tr");
		
		var rmHtml ='您确认要删除【'+curSubject+'】科目吗？';			
		$.dialog({
			title : '系统提示',
			content : 'url:' + ctl.path + '/dialog?content=' + encodeURIComponent(encodeURIComponent(rmHtml)),
			data:{},
			width : 435,
			height : 140,
			max : false,
			min : false,
			cache : false,
			lock: true,
			ok: function(){
				$.ajax({
					type: 'POST',
//					url: 'php/init_subject_del.php',
					url: ctl.path +'/subj/subject/delSubj',
					data: {
						"subjectId":subjectId,
						"_csrf" : $("input[name='_csrf']").val()
						},
					dataType: 'json',
					success: function(res){
						/*_this.remove();*/
						$.dialog.tips(res.message);
						if(res.status == 200){
							if(res.success){
								_this.remove();
							}
						}
					}
				});
			},
			cancel: true,
			cancelVal: '确定',
			cancelVal: '关闭'
		});
	});
});
/*]]>*/