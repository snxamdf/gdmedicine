//img focus
function show_sidebar(div){
	//initialize
	var selector = div;
	var bgs = $(selector + '>ul li');
	var isHover = false;
	var srcTime;
	
	$(bgs).css({
		position:'absolute'
	}).hide().eq(0).show();
	
	if(bgs.length <= 1) {
		return;
	}
	
	for (var i=0; i<bgs.length; i++) {
		$(selector + ' div.switch').append('<span>'+(i+1)+'</span>');
	}
	
	//hover event
	$(selector + ">ul").add(selector + ' a.next').hover(function(){
		isHover = true;
		clearInterval(srcTime);
	},function(){
		isHover = false;
		clearInterval(srcTime);
		srcTime = setTimeout(function(){
			setContent(getIdx($(selector + " div.switch span[class=curr]").index()));
		},500);
	});
	$(selector + ' a.next').click(function(){
		setContent(getIdx($(selector + " div.switch span[class=curr]").index()));
		return false;
	});
	$(selector + " div.switch span").hover(function() {
		if ($(this).hasClass('curr')) {
			return;
		}
		isHover = true;
		clearInterval(srcTime);
		setContent($(this).index());
	},function(){
		isHover = false;
		clearInterval(srcTime);
		var _this = this;
		srcTime = setTimeout(function(){
			setContent(getIdx($(_this).index()));
		},5000);
	});
	
	//method
	function getIdx(n){
		if(n == bgs.length-1){
			n=-1;
		}
		n++;
		return n;
	}
	function setContent(n){
		if (n!=-1) {
			$(selector + " >ul li:visible").stop(true,true).fadeOut(500).parent().children().eq(n).stop(true,true).fadeIn(500);
		}else{
			n = 0;
		}
		$(selector + ' div.switch span').removeClass('curr').eq(n).addClass('curr');
		if (!isHover) {
			srcTime = setTimeout(function(){
				setContent(getIdx(n));
			},5000);
		}
	};
	setContent(-1);
}
$(function () {
	//focus
	show_sidebar("#scrollPic");
});