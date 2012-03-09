$(function() {
	$("#submit_btn").click(function() {
		var note = $("#note").val();
		var sender = $("#sender").val();
		var receiver = $("#receiver").val();
		var dataString = 'note='+ note + '&sender=' + sender + '&receiver=' + receiver;

		if(note=='')
		{
			$('.error').fadeOut(200).show();
		}
		else
		{
			$.ajax({
				type: "POST",
				url: "newNote.jsp",
				data: dataString,
				success: function(){
					$('#note').hide();
					$('.error').fadeOut(200).hide();
					$('#submit_btn').html("<input type='submit' id='submit_btn' value='note sent'/>");
				}
			});
		}
		return false;
	});
});