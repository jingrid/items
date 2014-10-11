<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@include file="../portal/common.jsp" %>

	    <ul class="nav nav-list r-sidenav affix">
	      <li class="${sideMenuActive=='scheduling'?'active':''}"><a href="./scheduledjobs"><i class="icon-chevron-right"></i> 作业调度</a></li>
	      <li class="${sideMenuActive=='jobs'?'active':''}"><a href="./jobs"><i class="icon-chevron-right"></i> 作业管理</a></li>
	      <li class="${sideMenuActive=='schedules'?'active':''}"><a href="./schedules"><i class="icon-chevron-right"></i>  计划模板</a></li>
	    </ul>
