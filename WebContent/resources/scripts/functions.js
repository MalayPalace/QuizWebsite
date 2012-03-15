(function(global) {
	var contentShow = true;
	global.toggleContent = function() {
		if(contentShow == true) {
			$('#contentFrame').fadeOut();
			contentShow = false;
		}
		else {
			$('#contentFrame').fadeIn();
			contentShow = true;
		}
	};
	
	global.showNewsFeed = function() {
		$('#contentFrame').attr('src', 'homepage/home_feed.jsp');
	};
	
	
	global.showNotes = function()  {
		$('#contentFrame').attr('src', 'homepage/home_notes.jsp');
	};
	
	global.showQuizTaken = function() {
		$('#contentFrame').attr('src', 'homepage/home_quizTaken.jsp');
	};
	
	global.showQuizCreated = function() {
		$('#contentFrame').attr('src', 'homepage/home_quizCreated.jsp');
	};
	
	global.showFriendRequests = function() {
		$('#contentFrame').attr('src', 'homepage/home_friendRequests.jsp');
	};
	
	global.showChallenges = function() {
		$('#contentFrame').attr('src', 'homepage/home_challenges.jsp');
	};
	
	global.showMoreAnnouncements = function() {
		$('#contentFrame').attr('src', 'homepage/home_moreAnnouncements.jsp');
	};

	global.sendNotePopup = function() {
		window.open('sendNoteButton.jsp');
	};
		
})(window);


$(".highlight").focusin(function() {
	if(!$(this).hasClass("highlight_background")) {
		$(".highlight").removeClass("highlight_background");
		$(this).addClass("highlight_background");
	}
});

$(".highlight").focusout(function() {
	$(this).removeClass("highlight_background");
});

