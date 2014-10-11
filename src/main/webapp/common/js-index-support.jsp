<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>

function showWindow(){
	$.get(
		'ui/showWindow.do',
		function(o){	
			$("#showWindow").append(o);	
			render();			
		}
	);
}

function showPictureInfor(){
	// transition effect
		style = 'easeOutQuart';
		// if the mouse hover the image
		$('.photo').hover(
			function() {
				//display heading and caption
				$(this).children('div:first').stop(false,true).animate({top:0},{duration:200, easing: style});
				$(this).children('div:last').stop(false,true).animate({bottom:0},{duration:200, easing: style});
			},
			function() {
				//hide heading and caption
				$(this).children('div:first').stop(false,true).animate({top:-50},{duration:200, easing: style});
				$(this).children('div:last').stop(false,true).animate({bottom:-50},{duration:200, easing: style});
			}
	);
}

function render(){

	$.get(
		'pages/ProductCatalogue/flashImageShow.do',
		function(o){	
			$("#flashImage").append(o);
			$.init_slide('imgstore1','showhere1',1,0,0,1,5000,1);			
		}
	);

	$.get(
		'pages/News/list.do',
		function(o){	
			$("#newsList").append(o);					
		}
	);

	$.get(
		'pages/ProductCatalogue/newestProducts.do',
		function(o){	
			$("#newProducts").append(o);	
			$("#slider").easySlider({
				controlsBefore:	'<p id="controls">',
				controlsAfter:	'</p>',
				auto: true, 
				continuous: true
			});
			showPictureInfor();			
		}
	);

	$.get(
		'pages/ProductCatalogue/hotProducts.do',
		function(o){	
			$("#hotProduct").append(o);			
		}
	);
	
	$.get(
		'pages/ProductCatalogue/productsShow.do',
		function(o){	
			$("#productShow").append(o);
			$('#gallery a').lightBox();			
		}
	);

}

function init(){
	$('#dock').Fisheye(
				{
					maxWidth: 50,
					items: 'a',
					itemsText: 'span',
					container: '.dock-container',
					itemWidth: 50,
					proximity: 150,
					halign : 'center'
				}
		);
		 
		$.get(
			'pages/ProductCatalogue/list.do',
			function(o){
			    $("#pc").append(o);
			     render();	 				
			}
		);

	ymPrompt.alert({message:"欢迎观临-飞茗茶叶公司",title:"welcome",winPos:'rb',showMask:false,useSlide:true,slideCfg:{increment:0.1,interval:50},fixPosition:true});
	setTimeout(function(){ymPrompt.doHandler('close',true)},6000);	
}
$(document).ready(init);