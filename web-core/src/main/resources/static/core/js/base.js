$(function(){
	//nav
	$('#header .drop').hover(function() {
		$(this).addClass('dropOn');
		$(this).find('.subNav').stop().slideDown();
	},function(){
		$(this).removeClass('dropOn');
		$(this).find('.subNav').stop().slideUp();
	});
	//sidebar   
	var screenWidth = $(window).width();
	if(screenWidth<1120){
		$('#nav').hide();
	}
	$(window).resize(function(){              
		$(window).width()<1120?$('#nav').fadeOut():$('#nav').fadeIn();
	});
	//placeholder
	if(!placeholderSupport()){
	    $('[placeholder]').focus(function() {
	        var input = $(this);
	        if (input.val() == input.attr('placeholder')) {
	            input.val('');
	            input.removeClass('placeholder');
	        }
	    }).blur(function() {
	        var input = $(this);
	        if (input.val() == '' || input.val() == input.attr('placeholder')) {
	            input.addClass('placeholder');
	            input.val(input.attr('placeholder'));
	        }
	    }).blur();
	};
	function placeholderSupport() {
		return 'placeholder' in document.createElement('input');
	}
});
(function() {
	//BrowserDetect
	var BrowserDetect = {
		init: function () {
			this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
			this.version = this.searchVersion(navigator.userAgent)
				|| this.searchVersion(navigator.appVersion)
				|| "an unknown version";
			this.OS = this.searchString(this.dataOS) || "an unknown OS";
		},
		searchString: function (data) {
			for (var i=0;i<data.length;i++)	{
				var dataString = data[i].string;
				var dataProp = data[i].prop;
				this.versionSearchString = data[i].versionSearch || data[i].identity;
				if (dataString) {
					if (dataString.indexOf(data[i].subString) != -1)
						return data[i].identity;
				}
				else if (dataProp)
					return data[i].identity;
			}
		},
		searchVersion: function (dataString) {
			var index = dataString.indexOf(this.versionSearchString);
			if (index == -1) return;
			return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
		},
		dataBrowser: [
			{
				string: navigator.userAgent,
				subString: "Chrome",
				identity: "Chrome"
			},
			{ 	string: navigator.userAgent,
				subString: "OmniWeb",
				versionSearch: "OmniWeb/",
				identity: "OmniWeb"
			},
			{
				string: navigator.vendor,
				subString: "Apple",
				identity: "Safari",
				versionSearch: "Version"
			},
			{
				prop: window.opera,
				identity: "Opera"
			},
			{
				string: navigator.vendor,
				subString: "iCab",
				identity: "iCab"
			},
			{
				string: navigator.vendor,
				subString: "KDE",
				identity: "Konqueror"
			},
			{
				string: navigator.userAgent,
				subString: "Firefox",
				identity: "Firefox"
			},
			{
				string: navigator.vendor,
				subString: "Camino",
				identity: "Camino"
			},
			{		// for newer Netscapes (6+)
				string: navigator.userAgent,
				subString: "Netscape",
				identity: "Netscape"
			},
			{
				string: navigator.userAgent,
				subString: "MSIE",
				identity: "Explorer",
				versionSearch: "MSIE"
			},
			{
				string: navigator.userAgent,
				subString: "Gecko",
				identity: "Mozilla",
				versionSearch: "rv"
			},
			{ 		// for older Netscapes (4-)
				string: navigator.userAgent,
				subString: "Mozilla",
				identity: "Netscape",
				versionSearch: "Mozilla"
			}
		],
		dataOS : [
			{
				string: navigator.platform,
				subString: "Win",
				identity: "Windows"
			},
			{
				string: navigator.platform,
				subString: "Mac",
				identity: "Mac"
			},
			{
				string: navigator.userAgent,
				subString: "iPhone",
				identity: "iPhone/iPod"
		    },
			{
				string: navigator.platform,
				subString: "Linux",
				identity: "Linux"
			}
		]
	
	};
	
	BrowserDetect.init();
	
	window.$.client = { os : BrowserDetect.OS, browser : BrowserDetect.browser };
	
})(jQuery);
//plugin
$.fn.slider_index = function (){
	return $(this).each(function (){
		var aLiPic = $('.img>li', this);
		var aNum = $('.circle>em', this);
		var oTxt = $('.txt', this);
		var oBar_l = $('.bar_l', this);
		var oBar_r = $('.bar_r', this);
		var iNow = 0;
		var iSpeed = 4000;
		this.timer = null;

		$(this).hover(function (){
			clearInterval(this.timer);
		}, function (){
			this.timer = setInterval(tab, iSpeed);
		});

		aNum.mouseover(function (){
			iNow = $(this).index();
			fnSlider();
		});

		oBar_l.click(function (){
			if (iNow <= 0){
				iNow = aNum.length - 1;
			} else {
				iNow--;
			}
			fnSlider();
		});

		oBar_r.click(function (){
			if (iNow >= aNum.length - 1){
				iNow = 0;
			} else {
				iNow++;
			}
			fnSlider();
		});

		function fnSlider(){
			aLiPic.eq(iNow).fadeIn('slow').siblings().hide();
			var txt = aLiPic.eq(iNow).find('img').attr('alt');
			oTxt.html(txt);
			aNum.eq(iNow).addClass('current').siblings().removeClass('current');
		}

		function tab(){
			if (iNow >= aNum.length - 1){
				iNow = 0;
			} else {
				iNow++;
			}
			fnSlider();
		}

		this.timer = setInterval(tab, iSpeed);
		fnSlider();
	});
};
