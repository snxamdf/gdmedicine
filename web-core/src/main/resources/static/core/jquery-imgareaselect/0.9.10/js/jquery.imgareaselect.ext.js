(function($) {
	var ias = {
		aspectRatio : "3:2",
		hasSelection : true,
		path : "cms/test",
		file : ".file",
		form : ".form",
		srcDiv : ".uploadimg",
		srcImg : ".srcimg",
		midDiv : ".midDiv",
		midImg : ".midimg",
		fewDiv : ".fewDiv",
		fewImg : ".fewimg",
		x : 0,
		y : 0,
		w : 0,
		h : 0,
		ratio : 1,
		fileName : "",
		filePath : "",
		isUpload : 0, // 图片上传标志
		isSelection : 0, // 图片区域选择标志
		init : function(obj, args) {
			return (function() {
				ias.aspectRatio = args.aspectRatio;
				ias.hasSelection = args.hasSelection;
				ias.path = args.path;
				ias.file = args.file;
				ias.form = args.form;
				ias.srcDiv = args.srcDiv;
				ias.srcImg = args.srcImg;
				ias.midDiv = args.midDiv;
				ias.midImg = args.midImg;
				ias.fewDiv = args.fewDiv;
				ias.fewImg = args.fewImg;
				obj.find(args.srcImg).imgAreaSelect({
					aspectRatio : args.aspectRatio,
					fadeSpeed : 200,
					handles : true,
					onSelectChange : function(img, selection) {
						// 重新设置x、y、w、h的值
						ias.x = selection.x1;
						ias.y = selection.y1;
						ias.w = selection.x2 - selection.x1;
						ias.h = selection.y2 - selection.y1;
						ias.isSelection = 1; // 图片区域选择后，设置图片区域选择标志为1
						//ias.preview(obj, args, img, selection, ias.midDiv, ias.midImg);
						//ias.preview(obj, args, img, selection, ias.fewDiv, ias.fewImg);
					}
				});
			})();
		},
		preview : function(obj, args, img, selection, previewDiv, previewImg) {
			if (!selection.width || !selection.height) {
				return;
			}

			var $srcDiv = obj.find(ias.srcDiv);
			var $srcImg = obj.find(ias.srcImg);
			var $previewDiv = obj.find(previewDiv);
			var $previewImg = obj.find(previewImg);

			// 预览图片支持按缩放比例显示
			var ratio = 1;
			var ratios = ias.aspectRatio.split(":");
			if (ratios.length == 2) {
				ratio = ratios[0] / ratios[1];
			}
			if (!isNaN(ratio) && ratio != 1) {
				$previewDiv.height($previewDiv.width / ratio);
			}

			var srcImgW = $srcImg.width();
			var srcImgH = $srcImg.height();
			var previewDivW = $previewDiv.width();
			var previewDivH = $previewDiv.height();

			var scaleX = previewDivW / selection.width;
			var scaleY = previewDivH / selection.height;

			$previewImg.css({
				width : Math.round(scaleX * srcImgW),
				height : Math.round(scaleY * srcImgH),
				marginLeft : -Math.round(scaleX * selection.x1),
				marginTop : -Math.round(scaleY * selection.y1)
			});
		},
		clear : function(obj, args) {
			obj.find(args.srcImg).attr("src", ctl.statics + "/stat/web/images/uploadimg.jpg");
			// 将input file的选择的文件清空
			var file = obj.find(args.file);
			if (file != null && file.length > 0) {
				file[0].outerHTML = file[0].outerHTML;
			}
			ias.isUpload == 0; // 头像窗口打开时设置图片上传标志为0
			ias.isSelection == 0; // 设置图片区域选择标志为0
		},
		crop : function(obj, args, callback) {
			// 如果没有选择图片提示
			if (ias.isUpload == 0) {
				alert('请点击“本地照片”选择图片！');
				callback(false);
				return false;
			}
			// 如果没有区域选择图片提示
			if (ias.hasSelection) {
				if (ias.isSelection == 0) {
					alert('请在左侧图片上按住左键后拖动鼠标，进行区域选择图片！');
					callback(false);
					return false;
				}
			}
			if (ias.fileName != null && $.trim(ias.fileName) != '') {
				$.post(ctl.path + "/comm/crop/3b2", {
					'_csrf' : $("input[name='_csrf']").val(),
					'fileName' : ias.fileName,
					'path' : ias.path,
					'x' : Math.round(ias.x * ias.ratio),
					'y' : Math.round(ias.y * ias.ratio),
					'w' : Math.round(ias.w * ias.ratio),
					'h' : Math.round(ias.h * ias.ratio)
				}, function(result) {
					if (result.success) {
						ias.filePath = result.data;
						$this.trigger("oncropped", [ ias.filePath ]);
						callback(true);
						return true;
					} else {
						callback(false);
						return false;
					}
				}, "json");
			} else {
				callback(false);
				return false;
			}
		},
		getImgNatural : function(img, callback) {
			var nWidth, nHeight;
			if (img.naturalWidth) { // 现代浏览器
				nWidth = img.naturalWidth;
				nHeight = img.naturalHeight;
				callback(nWidth, nHeight);
			} else { // IE6/7/8
				var image = new Image();
				image.src = img.src;
				image.onload = function() {
					callback(image.width, image.height);
				}
			}
		}
	}
	$.fn.openAvatarDialog = function(options) {
		var args = $.extend({
			aspectRatio : "3:2",
			hasSelection : true,
			path : "comm/crop",
			file : ".file",
			form : ".form",
			srcDiv : ".uploadimg",
			srcImg : ".srcimg",
			midDiv : ".midDiv",
			midImg : ".midimg",
			fewDiv : ".fewDiv",
			fewImg : ".fewimg"
		}, options);
		$this = this;
		$this.modal({
			onApprove : function() {
				return ias.crop($this, args, function(isOk) {
				});
			},
			onHide : function() {
				$this.find(args.srcImg).imgAreaSelect({
					instance : true
				}).cancelSelection();
			}
		}).modal('show');
		ias.clear($this, args);
		ias.init($this, args);
	}
	$.fn.avatarUpload = function() { // 头像上传
		// 限制上传文件格式
		var obj = this;
		var file = obj.find(ias.file).val();
		var fileType = file.substring(file.lastIndexOf('.') + 1, file.length)
		if (fileType.toLowerCase() != 'jpg' && fileType.toLowerCase() != 'gif' && fileType.toLowerCase() != 'png') {
			alert("只能上传JPG、GIF、PNG格式的图片");
			return false;
		}
		// 关闭头像区域选择模式
		obj.find(ias.srcImg).imgAreaSelect({
			instance : true
		}).cancelSelection();
		obj.find(ias.form).ajaxSubmit({
			dataType : "json",
			url : ctl.path + "/comm/crop/upload",
			data : {
				'path' : ias.path
			},
			success : function(result) {
				if (!result.success) {
					alert(result.data);
					return;
				}
				ias.fileName = result.data.id;
				ias.isUpload = 1; // 图片上传成功后，设置图片上传标志为1
				ias.isSelection = 0; // 设置图片区域选择标志为0
				obj.find(ias.srcImg).attr("src", ctl.uploads + result.data.path);
				//obj.find(ias.midImg).attr("src", ctl.uploads + result.data.path);
				//obj.find(ias.fewImg).attr("src", ctl.uploads + result.data.path);
			},
			error : function(XmlHttpRequest, textStatus, errorThrown) {
				ias.isUpload = 0; // 图片上传失败后，设置图片上传标志为0
				ias.isSelection = 0; // 设置图片区域选择标志为0
				alert("上传图片失败！");
			}
		});
	}
	$.fn.avatarImgOnLoad = function() {
		var $this = this;
		var $srcdiv = $this.find(ias.srcDiv);
		var $srcimg = $this.find(ias.srcImg);
		ias.getImgNatural($srcimg[0], function(w, h) {
			var srcdivw = $srcdiv.width(), srcdivh = $srcdiv.height();
			var srcimgw = $srcimg.width(), srcimgh = $srcimg.height();
			if (srcimgw > srcimgh) {
				ias.ratio = w / srcdivw;
			} else {
				ias.ratio = h / srcdivh;
			}
		})
	}
})(jQuery);
