function check_email() {
	var filter = /^([0-9A-Za-z\-_\.]+)@([0-9a-z]+\.[a-z]{2,3}(\.[a-z]{2})?)$/g;
	var email = $("#email").val();
	if (email == "") {
		$("#emailError").html("邮箱不能为空！").css("color", "red");
		;
		return false;
	} else if (!filter.test(email)) {
		$("#emailError").html("邮箱格式不正确，请重新输入!").css("color", "red");
		;
		return false;
	}
	//邮箱格式正确时
	else {
		$("#emailError").html("");
		return true;
	}
}

function check_password() {
	var password1 = $("#password1").val();
	var password2 = $("#password2").val();
	if (password1 != password2) {
		$("#passwordError").html("两次密码输入不一致，请重新输入！");
		return false;
	} else {
		$("#passwordError").html("");
		return true;
	}
}

function check_passNotNull() {
	var pass = $.trim($("#password1").val());
	if (pass == null || pass == "") {
		$("#passNotNullError").html("密码不能为空！");
		return false;
	} else {
		$("#passNotNullError").html("");
		return true;
	}
}

function check_nickName() {
	var nickName = $.trim($("#nickName").val());
	if (nickName == null || nickName == "") {
		$("#nickNameError").html("昵称不能为空！");
		return false;
	} else {
		$("#nickNameError").html("");
		return true;
	}
}

function validate_email() {
	$.post("validateEmail", $('#email').serializeArray(), function(data) {
		if (data.exists == "true") {
			$("#emailError").html(data.msg).css("color", "red");
			return false;
		} else if (data.exists == "false") {
			$("#emailError").html(data.msg).css("color", "green");
			return true;
		}
	}, "json");
}

function check_all() {
	if (!check_email())
		return false;
	if (!check_password())
		return false;
	if (!check_passNotNull())
		return false;
	if (!check_nickName())
		return false;
	else
		return true;
}

function check_login() {
	if (!check_email())
		return false;
	if (!check_passNotNull())
		return false;
	else
		return true;
}
