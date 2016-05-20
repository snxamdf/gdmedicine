(function($){
	var ms = {
		init: function(obj, args){
			return (function(){
				if (args.enabled) {
					var content = '<div class="bdsharebuttonbox">';
					content += '<span style="float: left; padding: '+(args.size/2 - 2)+'px 5px 0 0;"><strong>'+args.title+'</strong></span>';
					for (var i=0;i<args.buttons.length;i++) {
						if (args.buttons[i] == "weixin") {
							content += '<a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>';
						}
						if (args.buttons[i] == "tsina") {
							content += '<a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>';
						}
						if (args.buttons[i] == "tqq") {
							content += '<a href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>';
						}
						if (args.buttons[i] == "renren") {
							content += '<a href="#" class="bds_renren" data-cmd="renren" title="分享到人人网"></a>';
						}
						if (args.buttons[i] == "douban") {
							content += '<a href="#" class="bds_douban" data-cmd="douban" title="分享到豆瓣"></a>';
						}
					}
					content += '</div>';
					$("#"+args.id).html(content);
					window._bd_share_config = {
						"common" : {
							"onBeforeClick" : function(cmd, config) {
								switch (cmd) {
								case "renren":
									this.bdText = args.title;
									this.bdDesc = args.desc;
									break;
								case "douban":
									this.bdText = args.title;
									this.bdDesc = args.desc + " " + args.url;
									break;
								default:
									this.bdText = args.title + " " + args.desc;
									this.bdDesc = " ";
								}
							},
							"bdSnsKey" : {},
							"bdText" : "",
							"bdDesc" : "",
							"bdUrl" : args.url,
							"bdMini" : "2",
							"bdMiniList" : false,
							"bdPic" : args.pic,
							"bdStyle" : "0",
							"bdSize" : args.size
						},
						"share" : {}
					};
					with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
				}
			})();
		}
	}
	$.fn.share = function(options){
		var args = $.extend({
			id : "", // 分享ID
			label : "分享给好友:", // 分享Label,默认值（分享给好友:）
			title: "", // 分享的标题，默认为空
			desc : "", // 分享的摘要
			url : "", // 分享的Url地址，默认为当前页面的url
			pic : "", // 指定分享图片的Url地址，默认为当前页面的图片url
			size : 16, // 分享按钮的尺寸：16｜24｜32,默认为16
			enabled : true, // 是否显示分享，默认显示
			buttons : ["weixin","tsina","tqq","renren","douban"] // 显示那些分享按钮，使用逗号分隔，默认：["weixin","tsina","tqq","renren","douban"]
		}, options);
		ms.init(this, args);
	}
})(jQuery);
