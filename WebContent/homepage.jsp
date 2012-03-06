<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quizweb.*"%>
<%@ page import="quizweb.accountmanagement.*"%>
<%@ page import="quizweb.achievement.*"%>
<%@ page import="quizweb.announcement.*"%>
<%@ page import="quizweb.database.*"%>
<%@ page import="quizweb.message.*"%>
<%@ page import="quizweb.question.*"%>
<%@ page import="quizweb.record.*"%>
<%@ page import="servlet.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>toQuiz.Me</title>
	<meta name="Description" content="A smart quiz website" />
	<meta name="robots" content="all, index, follow" />
	<meta name="distribution" content="global" />
	<link rel="shortcut icon" href="/favicon.ico" />
	
	<link rel="stylesheet" href="resources/css/main.css" type="text/css" />
	<link rel="stylesheet" href="resources/css/three_column_layout.css" type="text/css" />
	
	<script type="text/javascript" src="resources/scripts/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="resources/scripts/functions.js"></script>
</head>
<body>
<div class="container" >
	<div class="header">
		<h1>toQuiz.Me</h1>
	</div>
	<div class="content-container">
		<div class="three_column_left">
		    <ul>
				<h4>
				<% 
	               String username = request.getParameter("username");
	               out.println("Hi, " + username);
	            %>
				</h4>
				
			</ul>
			<ul>
				<h4 class="title_style_minor">FAVORITES</h4>
				<li><a class="link-style-dominant" onclick="showNewsFeed()">News Feed</a></li>
				<li><a class="link-style-dominant" onclick="showNotes()">Notes</a></li>
			</ul>
			<ul>
				<h4 class="title_style_minor">NOTIFICATIONS</h4>
				<li><a class="link-style-dominant" onclick="showFriendRequests()">Friend Requests</a></li>
				<li><a class="link-style-dominant" onclick="showChallenges()">Challenges</a></li>
			</ul>
			<ul>
				<h4 class="title_style_minor">MY QUIZZES</h4>
				<li><a class="link-style-dominant" onclick="showQuizTaken()">Took</a></li>
				<li><a class="link-style-dominant" onclick="showQuizCreated()">Created</a></li>
			</ul>

		</div>
		
		<div class="three_column_content">		
			<iframe id="contentFrame" class="dynamicFrame" frameborder="0" src="home_feed.jsp"></iframe>
		</div>
	
		<div class="three_column_right">
			<div class="right_block">
				<h4 class="title-style-minor">Announcement</h4>
				<hr />
				<ol>
				<%
				if(Announcement.allAnnouncements == null){
					out.print("no announcement.");
				}else{
					if(Announcement.allAnnouncements.size()<=2){
						for(int i=0;i<Announcement.allAnnouncements.size();i++){
							out.println("<li>" + Announcement.allAnnouncements.get(i).title + ": " + Announcement.allAnnouncements.get(i).content + "</li>");
				        }
				    }else{  
				    	out.println(Announcement.allAnnouncements.get(0).title + ": " + Announcement.allAnnouncements.get(0).content);
					    out.println(Announcement.allAnnouncements.get(1).title + ": " + Announcement.allAnnouncements.get(1).content);
					    out.println("<a class=\"link-style-dominant\" onclick=\"showMoreAnnouncements()\">More Announcements</a>");
				    }
				}
				%>
				</ol>
			</div>
			<div class="right_block">
				<h4 class="title-style-minor">Popular Quizzes</h4>
				<hr />
				<ol>
				<%-- do not delete
				Quiz quiz = new Quiz();
				if(quiz.getTopRecord() == null){
					out.print("no popular quiz.");
				}else{
					if(quiz.getTopRecord().size()<=2){
						for(int i=0;i<quiz.getTopRecord().size();i++){
							out.println("<li>" + quiz.getTopRecord().get(i).quiz.quizID + "</li>");
				        }
				    }else{  
				    	out.println("<li>" + quiz.getTopRecord().get(0).quiz.quizID + "</li>");
				    	out.println("<li>" + quiz.getTopRecord().get(1).quiz.quizID + "</li>");
				    	out.println("<a class=\"link-style-dominant\" onclick=\"showMorePopularQuizs()\">More popular quizzes</a>");
				    }
				}
				--%>
				</ol>
			</div>
			<div class="right_block">
				<h4 class="title-style-minor">Recently Created</h4>
				<hr />
				<ol>
				<%-- do not delete
				Quiz quiz = new Quiz();
				if(quiz.getHistory() == null){
					out.print("no popular quiz.");
				}else{
					if(quiz.getHistory().size()<=2){
						for(int i=0;i<quiz.getHistory().size();i++){
							out.println("<li>" + quiz.getHistory().get(i).quiz.quizID + "</li>");
				        }
				    }else{  
				    	out.println("<li>" + quiz.getHistory().get(0).quiz.quizID + "</li>");
				    	out.println("<li>" + quiz.getHistory().get(1).quiz.quizID + "</li>");
				    	out.println("<a class=\"link-style-dominant\" onclick=\"showMoreRecentlyQuizs()\">More recently quizzes</a>");
				    }
				}
				--%>
				</ol>
			</div>
		</div>
		<div class="footer">
			Copyright � toQuiz.me, 2012
		</div>
	</div>
</div>
</body>