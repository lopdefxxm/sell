$(document).ready(function() {
	/**
	 * 在输入时修改为密码状态
	 */
	$("input.password").focus(function(){
		this.type="password";
	});
	
	$(".password,.username").keypress(function(event){
		if(event.keyCode == 13){
			if($(this).attr("class")=="username"){
				$(".password").focus();
			}else{
				 $("#btnSubmit").click();
			}
		}else{
			$(".error").hide();
		}
	});
	
	/**
	 * 登录按钮
	 */
    $("#btnSubmit").click(function(){
    	var username = $(".username").val();
        var password = $(".password").val();
        if(!username || username== "") {
            $(".error").fadeOut("fast", function(){
                $(this).css("top", "27px");
            });
            $(".error").fadeIn("fast", function(){
                $(".username").focus();
            });
            return false;
        }
		
        if(password == "") {
            $(".error").fadeOut("fast", function(){
                $(this).css("top", "96px");
            });
            $(".error").fadeIn("fast", function(){
                $(".password").focus();
            });
            return false;
        }
    	$.post(
            	"loginManage.Login.c",
            	{
            		username:username,
            		password:password
            	},
            	function(data){
            		var status = data.status;
            		if(status=="0"){
            			$(".Captcha").show().val(data.msg).css({
            				"color":"#FF89C0",
            			});
            		}else{
            			window.location.href="aero.framework.view.MainView.d";
            		}
            	}
            );
    });
	$(".Captcha").hide();
    
    //初始化完成，在用户名上聚焦
    $("input.username").focus();
});
