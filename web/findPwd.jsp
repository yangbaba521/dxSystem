<%--
  Created by IntelliJ IDEA.
  User: sunyuyang
  Date: 17/3/17
  Time: 上午11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>会务签到管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/findPwd.css" rel="stylesheet" type="text/css"/>
    <link href="css/public.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="findPwd_bg">
    <div id="findPwd">
        <div id="findPwd_info_text" class="hide">
            <span id="findPwd_info_text_span" class=""></span>
            <div id="findPwd_info_text_answer" class="">
                <span id="findPwd_info_text_answer_span" class="">问题答案:</span>
                <input type="text" id="findPwd_info_text_answer_input" class="normal_input"/>
            </div>
        </div>
        <div id="findPwd_user" class="">
            <div id="findPwd_user_info" class="">
                <label for="findPwd_user_info_input">您的用户名:</label>
                <input type="text" id="findPwd_user_info_input" class="normal_input"/>
            </div>
        </div>
        <div id="findPwd_button" class="">
            <input type="button" id="findPwd_button_submitQ" value="提交" class="hide"/>
            <input type="button" id="findPwd_button_submitUser" value="找回"/>
            <input type="button" id="findPwd_button_back" value="返回"/>
        </div>
    </div>
</div>
<div class="hide_box" id="Box">
    <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>提示</h4>
    <p></p>
</div>

<div class="hide_box" id="ComBox">
    <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>确认操作</h4>
    <p></p>
    <input type="button" id="ComBox_sure" value="确定"/>
    <input type="button" id="ComBox_nosure" value="不确定"/>
</div>

<div class="hide_box" id="sureBox">
    <h4><a href="javascript:easyDialog.close();" title="关闭窗口">&times;</a>确认操作</h4>
    <p></p>
    <input type="button" id="sureBox_sure" value=""/>
</div>

<script src="js/pub/jquery-1.10.2.js"></script>
<script src="js/pub/easydialog.js"></script>
<script src="js/findPwd.js"></script>

</body>
</html>
