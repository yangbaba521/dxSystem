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
    <title>电销业务查询管理系统</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/main/home.css" rel="stylesheet" type="text/css"/>
    <link href="css/public.css" rel="stylesheet" type="text/css"/>
    <link href="css/main/pub/publicForMain.css" rel="stylesheet" type="text/css"/>
</head>
<body>
<div id="home_title" class="">
    <img src="img/logo.png" id="home_title_logo">
    <span id="home_title_span">电销业务查询管理系统</span>
    <img src="img/quite.png" id="home_title_quite">
    <span id="home_title_userInfo"></span>
</div>
<div id="home_body" class="">
    <div id="home_body_guide" class="">
        <ul id="home_body_guide_area" class="">
            <li id="home_body_guide_area_report">
                <div name="close">
                    <img src="img/report.png" name="signLogo"/>
                    <span>查阅报表</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_report_pages" class="hide">
                    <li id="home_body_guide_area_report_pages_sum" class="guide_unselected">
                        <span>总结报表</span>
                    </li>
                </ul>
            </li>
            <li id="home_body_guide_area_rank">
                <div name="close">
                    <img src="img/rank.png" name="signLogo"/>
                    <span>排名结算</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_rank_pages" class="hide">
                    <li id="home_body_guide_area_rank_pages_persion" class="guide_unselected">
                        <span>个人排名</span>
                    </li>
                    <li id="home_body_guide_area_rank_pages_team" class="guide_unselected">
                        <span>小组排名</span>
                    </li>
                </ul>
            </li>
            <li id="home_body_guide_area_update">
                <div name="close">
                    <img src="img/update.png" name="signLogo"/>
                    <span>数据更新</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_update_pages" class="hide">
                    <li id="home_body_guide_area_update_pages_area" class="guide_unselected">
                        <span>区域维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_part" class="guide_unselected">
                        <span>区部维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_team" class="guide_unselected">
                        <span>部组维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_staff" class="guide_unselected">
                        <span>组员维护</span>
                    </li>
                </ul>

            </li>
            <li id="home_body_guide_area_set">
                <div name="close">
                    <img src="img/set.png" name="signLogo"/>
                    <span>用户设置</span>
                    <img src="img/open.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_set_pages" class="hide">
                    <li id="home_body_guide_area_set_pages_baseInfo" class="guide_unselected">
                        <span>用户信息设置</span>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
    <div id="home_body_action" class="">
        <div id="home_body_action_area" class="border">
            <img src="img/home_introduction.png"/>
        </div>
    </div>
</div>
<div id="back_board" class="hide">

</div>
<div id="home_buttom" class="">
    <span>copyright  &copy  2017  华夏保险江苏分公司  版权所有</span>
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
    <h4><a href="" title="关闭窗口">&times;</a>确认操作</h4>
    <p></p>
    <input type="button" id="sureBox_sure" value="确定"/>
</div>

<script src="js/pub/jquery-1.10.2.js"></script>
<script src="js/pub/easydialog.js"></script>
<script src="js/main/home.js"></script>
<script src="js/main/pub/publicForMain.js"></script>

</body>
</html>
