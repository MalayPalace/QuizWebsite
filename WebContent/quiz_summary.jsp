<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
	<title>Quiz Summary<!-- fetch quiz title --></title>
	<meta name="Description" content="A smart quiz website" />
	<meta name="robots" content="all, index, follow" />
	<meta name="distribution" content="global" />
	<link rel="shortcut icon" href="/favicon.ico" />
	
	<link rel="stylesheet" href="resources/css/main.css" type="text/css" />
	<link rel="stylesheet" href="resources/css/two_column_layout.css" type="text/css" />
	
	<script type="text/javascript" src="resources/scripts/jquery-1.4.2.min.js"></script>
	<script type="text/javascript" src="resources/scripts/functions.js"></script>
</head>
<body>
<div class="container" >
	<div class="header">
		<h1>toQuiz.Me</h1>
	</div>
	
	<div class="content-container">
		<div class="two_column_left">
			<h1>Quiz Title</h1>
			<p>Creator: <a class="link-style-dominant" href="http://toquiz.me/username=anthony">Anthony</a>
				<br>Created on Dec 12, 2012</p>
		</div>
		<div class="two_column_right">
			<h2>Rate This Quiz</h2>
		</div>

	</div>
	
	<div class="content-container">
		<div class="two_column_left">
			<p>Text description of quiz</p>
		</div>
		<div class="two_column_right">
			<button type="submit" class="button_large" name="start_quiz">
					Start Quiz
			</button>
			<button type="submit" class="button_grey" name="start_quiz">
					Practice Mode
			</button>
		</div>
	</div>
	
	<div class="content-container">
		<div class="two_column_left">
			<h2>Quiz Summary</h2>
			<p><span class="dominant_text">33</span> users have taken this quiz</p>
			<p>They spend an average of <span class="dominant_text">65 minutes</span> on the quiz</p>
			<p>Average score is <span class="dominant_text">65.5</span></p>
		</div>
		<div class="two_column_right">
			<h2>Top Performers</h2>
			<ol>
				<li><a href="http://toquiz.me/username=john">John</a></li>
				<li><a href="http://toquiz.me/username=kennedy">Kennedy</a></li>
			</ol>
		</div>
		<div class="two_column_left">
			<h2>My Statistics</h2>
			<p>I haven't taken this test yet.</p>
		</div>
		<div class="two_column_left">
			<h2>Past Performances</h2>
			<p>Jack scored 92 in the test.</p>
		</div>
		

	</div>
	
	<div class="footer">
			Copyright � toQuiz.me, 2012
	</div>
	
</div>
</body>