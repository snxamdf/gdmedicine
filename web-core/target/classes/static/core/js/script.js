$(function (){
	$('.slider').slider_index();

	fnTab();
});

function fnTab(){
	var oTab = $('.tab');
	var sEvent = 'click';
	oTab.each(function (){
		var aTabLi = $('.tab-hd li', this);
		var aTabCon = $('.tab-con', this);
		aTabLi[sEvent](function (){
			var i = $(this).index();
			aTabLi.removeClass('current').eq(i).addClass('current');
			aTabCon.hide().eq(i).show();
			return false;
		});
	});
}

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