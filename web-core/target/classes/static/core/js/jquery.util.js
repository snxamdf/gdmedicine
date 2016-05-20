/**
 * jQuery util
 * 
 */
(function($) {
	
	/* 人民币金额转大写 */
	/* var out = $("#inputId").amountToChinese() */
	$.fn.amountToChinese = function() {
		var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); // 汉字的数字
		var cnIntRadice = new Array("", "拾", "佰", "仟"); // 基本单位
		var cnIntUnits = new Array("", "万", "亿", "兆"); // 对应整数部分扩展单位
		var cnDecUnits = new Array("角", "分", "毫", "厘"); // 对应小数部分单位
		var cnInteger = "整"; // 整数金额时后面跟的字符
		var cnIntLast = "元"; // 整型完以后的单位
		var maxNum = 999999999999999.9999; // 最大处理的数字

		var IntegerNum; // 金额整数部分
		var DecimalNum; // 金额小数部分
		var ChineseStr = ""; // 输出的中文金额字符串
		var parts; // 分离金额后用的数组，预定义

		var $this = $(this);
		var money = $this.val();
		if (money == "") {
			return "";
		}

		money = parseFloat(money);
		// alert(money);
		if (money >= maxNum) {
			$.alert('超出最大处理数字');
			return "";
		}
		if (money == 0) {
			ChineseStr = cnNums[0] + cnIntLast + cnInteger;
			// document.getElementById("show").value=ChineseStr;
			return ChineseStr;
		}
		money = money.toString(); // 转换为字符串
		if (money.indexOf(".") == -1) {
			IntegerNum = money;
			DecimalNum = '';
		} else {
			parts = money.split(".");
			IntegerNum = parts[0];
			DecimalNum = parts[1].substr(0, 4);
		}
		if (parseInt(IntegerNum, 10) > 0) {// 获取整型部分转换
			zeroCount = 0;
			IntLen = IntegerNum.length;
			for (i = 0; i < IntLen; i++) {
				n = IntegerNum.substr(i, 1);
				p = IntLen - i - 1;
				q = p / 4;
				m = p % 4;
				if (n == "0") {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						ChineseStr += cnNums[0];
					}
					zeroCount = 0; // 归零
					ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
				}
				if (m == 0 && zeroCount < 4) {
					ChineseStr += cnIntUnits[q];
				}
			}
			ChineseStr += cnIntLast;
			// 整型部分处理完毕
		}
		if (DecimalNum != '') {// 小数部分
			decLen = DecimalNum.length;
			for (i = 0; i < decLen; i++) {
				n = DecimalNum.substr(i, 1);
				if (n != '0') {
					ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
				}
			}
		}
		if (ChineseStr == '') {
			ChineseStr += cnNums[0] + cnIntLast + cnInteger;
		} else if (DecimalNum == '') {
			ChineseStr += cnInteger;
		}
		return ChineseStr;
	}

	/* 启用按钮 */
	$.fn.enabledButton = function() {
		$(this).removeClass("btn_none").removeAttr("disabled");
	}

	/* 禁用按钮 */
	$.fn.disabledButton = function() {
		$(this).addClass("btn_none").attr("disabled", "true");
	}

	/* 显示正确提示 */
	$.fn.showCorrectMsg = function(msg) {
		var $wrong = $("<span class='correct tc ml0'>" + msg + "</span>");
		var $this = $(this);
		$this.children().remove();
		$this.append($wrong);
	}

	/* 显示错误提示 */
	$.fn.showWrongMsg = function(msg) {
		var $wrong = $("<span class='wrong tc ml0'>" + msg + "</span>");
		var $this = $(this);
		$this.children().remove();
		$this.append($wrong);
	}

	/* 移除正确或错误提示 */
	$.fn.removeMsg = function() {
		var $this = $(this);
		$this.children().remove();
	}

	/* 60秒倒计时按钮 */
	$.fn.setInterval = function(text) {
		var $this = $(this);
		$this.attr("disabled", "disabled");
		var limit = 60;
		var time = limit;
		var interval = setInterval(function() {
			if (time > 1) {
				time--;
				$this.val(time + "秒后重新发送");
			} else {
				time = limit;
				clearInterval(interval);
				$this.val(text);
				$this.removeAttr("disabled");
			}
		}, 1000);
	}

	/*
	 * 根据长网址获取短网址. $.getShortUrl("http://www.yinongdai.com/cms/article/aboutUs",
	 * function(data) { alert(data[0].url_short); });
	 */
	$.getShortUrl = function(longUrl, callback) {
		var url = "http://api.t.sina.com.cn/short_url/shorten.json?source=1681459862&url_long=" + longUrl;
		$.ajax({
			async : false,
			type : 'get',
			url : url,
			dataType : 'jsonp',
			jsonpCallback : "getShortUrlCallback",
			success : callback
		});
	}

	/**
	 * 根据IP获取相关信息包括城市、提供商等 $.getIpInfo("114.113.223.34", function(){ var
	 * resultData = $.getIpInfoBack(remote_ip_info); //中国北京北京 alert(resultData);
	 * });
	 * 
	 */
	$.getIpInfo = function(ip, callback) {
		var url = 'http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=' + ip;
		$.getScript(url, callback);
	}
	$.getIpInfoBack = function(ipInfo, split) {
		if (split == undefined) {
			split = "-";
		}
		var resultData = (ipInfo.country + split + ipInfo.province + split + ipInfo.city);
		return resultData;
	}

	/* 验证身份证 */
	/* var out = $.checkIdCard("431381198809122734") */
	$.checkIdCard = function(idCard) {
		idCard = idCard.toString();
		// var Errors=new
		// Array("验证通过!","身份证号码位数不对!","身份证号码出生日期超出范围或含有非法字符!","身份证号码校验错误!","身份证地区非法!");
		var Errors = new Array(true, false, false, false, false);
		var area = {
			11 : "北京",
			12 : "天津",
			13 : "河北",
			14 : "山西",
			15 : "内蒙古",
			21 : "辽宁",
			22 : "吉林",
			23 : "黑龙江",
			31 : "上海",
			32 : "江苏",
			33 : "浙江",
			34 : "安徽",
			35 : "福建",
			36 : "江西",
			37 : "山东",
			41 : "河南",
			42 : "湖北",
			43 : "湖南",
			44 : "广东",
			45 : "广西",
			46 : "海南",
			50 : "重庆",
			51 : "四川",
			52 : "贵州",
			53 : "云南",
			54 : "西藏",
			61 : "陕西",
			62 : "甘肃",
			63 : "青海",
			64 : "宁夏",
			65 : "新疆",
			71 : "台湾",
			81 : "香港",
			82 : "澳门",
			91 : "国外"
		}
		var idCard, Y, JYM;
		var S, M;
		var idCard_array = new Array();
		idCard_array = idCard.split("");
		// 地区检验
		if (area[parseInt(idCard.substr(0, 2))] == null)
			return Errors[4];
		// 身份号码位数及格式检验
		switch (idCard.length) {
		case 15:
			if ((parseInt(idCard.substr(6, 2)) + 1900) % 4 == 0 || ((parseInt(idCard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idCard.substr(6, 2)) + 1900) % 4 == 0)) {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
			} else {
				ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
			}
			if (ereg.test(idCard))
				return Errors[0];
			else
				return Errors[2];
			break;
		case 18:
			// 18 位身份号码检测
			// 出生日期的合法性检查
			// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
			// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
			if (parseInt(idCard.substr(6, 4)) % 4 == 0 || (parseInt(idCard.substr(6, 4)) % 100 == 0 && parseInt(idCard.substr(6, 4)) % 4 == 0)) {
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
			} else {
				ereg = /^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
			}
			if (ereg.test(idCard)) {// 测试出生日期的合法性
				// 计算校验位
				S = (parseInt(idCard_array[0]) + parseInt(idCard_array[10])) * 7 + (parseInt(idCard_array[1]) + parseInt(idCard_array[11])) * 9 + (parseInt(idCard_array[2]) + parseInt(idCard_array[12])) * 10 + (parseInt(idCard_array[3]) + parseInt(idCard_array[13])) * 5 + (parseInt(idCard_array[4]) + parseInt(idCard_array[14])) * 8 + (parseInt(idCard_array[5]) + parseInt(idCard_array[15])) * 4 + (parseInt(idCard_array[6]) + parseInt(idCard_array[16])) * 2 + parseInt(idCard_array[7]) * 1 + parseInt(idCard_array[8]) * 6 + parseInt(idCard_array[9]) * 3;
				Y = S % 11;
				M = "F";
				JYM = "10X98765432";
				M = JYM.substr(Y, 1);// 判断校验位
				if (M == idCard_array[17])
					return Errors[0]; // 检测ID的校验位
				else
					return Errors[3];
			} else
				return Errors[2];
			break;
		default:
			return Errors[1];
			break;
		}
	}
})(jQuery);