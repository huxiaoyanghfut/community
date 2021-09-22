$(function(){
	$("#sendBtn").click(send_letter);
	$(".close").click(delete_msg);
});

function send_letter() {
	$("#sendModal").modal("hide");

	//获取用户提交表单内容
	var toName = $("#recipient-name").val();
	var content = $("#message-text").val();
	//json异步提交请求

	$.post(//请求路径
		CONTEXT_PATH + "/letter/send",
		//输入参数
		{"toName": toName, "content": content},
		function (data) {
			//解析相应返回的数据
			data = $.parseJSON(data);
			if (data.code == 0) {//发送成功
				$("#hintBody").text("发送成功！")
			} else {
				$("#hintBody").text(data.msg);
			}
			//反馈发送信息，2秒后消失
			$("#hintModal").modal("show");
			setTimeout(function () {
				$("hintModal").modal("hide");
				location.reload();
			}, 2000);
		}
	);
}

function delete_msg() {
	// TODO 删除数据
	$(this).parents(".media").remove();
}