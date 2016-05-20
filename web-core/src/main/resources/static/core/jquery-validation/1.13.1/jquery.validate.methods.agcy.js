// 汉字和括号 全角和半角对汉字无影响 最多100
jQuery.validator.addMethod("agcyChineseAndBrackets", function(value, element) {
//	var tel = /^([\u4e00-\u9fa5]+|\（?[\u4e00-\u9fa5]+\）?|[\u4e00-\u9fa5]+\（?[\u4e00-\u9fa5]+\）?){1,100}$/;
	var tel = /^([\u4e00-\u9fa5]+|\（?[\u4e00-\u9fa5]+\）?|[\u4e00-\u9fa5]+\（?[\u4e00-\u9fa5]+\）?)$/;
	return this.optional(element) || (tel.test(value));
}, "您输入的内容有误");

//机构手机号码验证
jQuery.validator.addMethod("agcyMobile", function(value, element) {
    var length = value.length;
    return this.optional(element) || (length == 11 && /^1[3|5|7|8]\d{9}$/.test(value));
}, "请正确填写您的手机号码");

//密码，数字，字母，符号
//jQuery.validator.addMethod("agcyPasswd",function(value, element) {
//	return this.optional(element) || /^([a-zA-Z0-9]|[@\-_.,]|){6,18}$/.test(value);
//}, "请输入6-18位字符组成的密码，可由字母、数字或符号(@-_.,)组成");
jQuery.validator.addMethod("agcyPasswd",function(value, element) {
	return this.optional(element) || /^([a-zA-Z0-9]|[@\-_.]|[\u4E00-\u9FA5]){6,18}$/.test(value);
}, "请输入6-16位字符组成的密码，可由字母、数字或符号(@-_.)组成");


//正整数且不得为0
jQuery.validator.addMethod("agcyPositiveInteger",function(value, element) {
	return this.optional(element) || /^[1-9]\d*$/.test(value);
}, "请填写正整数且不得为0");


//组织机构代码证件号
jQuery.validator.addMethod("agcyAgencyNo",function(value, element) {
	return this.optional(element) || /^[A-Z0-9]{8}-[A-Z0-9]{1}$/.test(value);
}, "组织机构代码证号错误");


//累计盈利    /^[1-9]\d{0,5}|[1-9]\d{0,5}\.\d{0,2}$/
jQuery.validator.addMethod("agcyProfit",function(value, element) {
	return this.optional(element) || /^((([1-9]{1}\d{0,5}))|([0]{1}))((\.(\d){1,2}))?$/.test(value);
}, "请填写数字，小数点后最多填写两位");


//社员数量
jQuery.validator.addMethod("agcyMembers",function(value, element) {
	return this.optional(element) || /^\d+$/.test(value);
}, "只能填写数字，社员数量可为0");


//入股金额
jQuery.validator.addMethod("agcyMoney",function(value, element) {
	return this.optional(element) || /^\d+$/.test(value);
}, "只能填写股金系数的倍数，仅新开户可输入为0");

