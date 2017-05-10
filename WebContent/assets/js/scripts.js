
$(document).ready(function() {
	$("input.username").focus();
	
    $('.page-container form').submit(function(){
        var username = $(this).find('.username').val();
        var password = $(this).find('.password').val();
        if(username == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.username').focus();
            });
            return false;
        }
		
        if(password == '') {
            $(this).find('.error').fadeOut('fast', function(){
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function(){
                $(this).parent().find('.password').focus();
            });
            return false;
        }
        
     //   $(this).attr("action","loginManage.login.c?username="+username+"&password="+password);
     //   $(this).submit();
       
    });

    $('.page-container form .username, .page-container form .password').keyup(function(){
        $(this).parent().find('.error').fadeOut('fast');
    });

    var url = window.location.href;
   
	if(url.indexOf("?")>0){
		$(this).find('.Captcha').show().val("账号密码错误！").css({
			"color":"#FF89C0",
		});
	}else{
		 $(this).find('.Captcha').hide();
	}
});
