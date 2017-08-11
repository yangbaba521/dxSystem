<%--
  Created by IntelliJ IDEA.
  User: sunyuyang
  Date: 17/8/2
  Time: 上午9:34
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
    <link href="css/main/operationTeam.css" rel="stylesheet" type="text/css"/>
    <link href="css/public.css" rel="stylesheet" type="text/css"/>
    <link href="css/main/pub/publicForMain.css" rel="stylesheet" type="text/css"/>
    <link href="css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
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
                <div name="open">
                    <img src="img/update.png" name="signLogo"/>
                    <span>数据更新</span>
                    <img src="img/close.png" name="oAc_button"/>
                </div>
                <ul id="home_body_guide_area_update_pages" class="">
                    <li id="home_body_guide_area_update_pages_area" class="guide_unselected">
                        <span>区域维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_part" class="guide_unselected">
                        <span>区部维护</span>
                    </li>
                    <li id="home_body_guide_area_update_pages_team" class="guide_selected">
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
        <div id="home_body_action_area" class="">
            <div id="home_body_action_area_select" class="">
                <div id="home_body_action_area_select_type" name="type">
                    <span>筛选种类:</span>
                    <select>
                        <option value="none">不筛选</option>
                        <option value="teamCode">部组编号</option>
                        <option value="teamName">部组名称</option>
                        <option value="partCode">所属编号</option>
                        <option value="partName">所属名称</option>
                        <option value="leaderCode">主管编号</option>
                        <option value="leaderName">主管名称</option>
                        <option value="teamTarget">部组目标</option>
                    </select>
                </div>
                <div id="home_body_action_area_select_value" name="value">
                    <span>筛选值:</span>
                    <div name="normal">
                        <input type="text" class="readyInput"/>
                    </div>
                    <div name="teamTarget">
                        <select>
                            <option value="greater">大于</option>
                            <option value="greater&equal">大于等于</option>
                            <option value="equal">等于</option>
                            <option value="less&equal">小于等于</option>
                            <option value="less">小于</option>
                            <option value="custom">自定义</option>
                        </select>
                        <input type="text" name="min" class="readyInput "/>
                        <span class="hide">---</span>
                        <input type="text" name="max" class="readyInput"/>
                    </div>
                </div>
                <div id="home_body_action_area_select_action" name="button">
                    <input type="button" name="submit" class="list_button" value="查询"/>
                </div>
            </div>
            <table id="teamInfoList" class="">
                <thead>
                <tr>
                    <th>部组编号</th>
                    <th>部组名称</th>
                    <th>所属编号</th>
                    <th>所属名称</th>
                    <th>主管编号</th>
                    <th>主管名称</th>
                    <th>部组目标</th>
                    <th>操作</th>
                </tr>
                </thead>
            </table>
            <div id="home_body_action_area_infoAction" class="">
                <div id="home_body_action_area_infoAction_download">
                    <input type="button" id="home_body_action_download_button" value="数据导出"/>
                </div>
                <div id="home_body_action_area_infoAction_function">
                    <input type="button" id="home_body_action_reviseAll_button" name="reviseAll" value="整体重载"/>
                    <input type="button" id="home_body_action_batchAdd_button" name="addBatch" value="批量新增"/>
                    <input type="button" id="home_body_action_area_infoAction_function_singleAdd_button" name="addSign" value="新增"/>
                    <input type="file" id="home_body_action_file" class="hide"/>
                </div>
            </div>
            <div id="home_body_action_area_infoAction_add_single">

            </div>
        </div>
    </div>
</div>
<div id="back_board" class="back">

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

<div id="wait">
    <img src="img/wait.png"/>
    <span>正在处理。。。。</span>
</div>

<div id="teamInfo">
    <div id="teamInfo_title" name="title">
        <span name="line">----------</span>
        <span name="title">部组信息</span>
        <span name="line">----------</span>
    </div>
    <div id="teamInfo_info" name="info">
        <div id="teamInfo_info_teamCode" name="teamCode">
            <label for="teamInfo_info_teamCode_input">部组编号:</label>
            <input type="text" id="teamInfo_info_teamCode_input" name="teamCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="teamInfo_info_teamName" name="teamName">
            <label for="teamInfo_info_teamName_input">部组名称:</label>
            <input type="text" id="teamInfo_info_teamName_input" name="teamName" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="teamInfo_info_areaCode" name="areaCode">
            <label for="teamInfo_info_partCode_input">所属编号:</label>
            <input type="text" id="teamInfo_info_partCode_input" name="partCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="teamInfo_info_areaName" name="areaName">
            <label for="teamInfo_info_partName_input">所属名称:</label>
            <input type="text" id="teamInfo_info_partName_input" name="partName" disabled="true" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="teamInfo_info_leaderCode" name="leaderCode">
            <label for="teamInfo_info_leaderCode_input">负责人工号:</label>
            <input type="text" id="teamInfo_info_leaderCode_input" name="leaderCode" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="teamInfo_info_leaderName" name="leaderName">
            <label for="teamInfo_info_leaderName_input">负责人名称:</label>
            <input type="text" id="teamInfo_info_leaderName_input" name="leaderName" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
        <div id="teamInfo_info_teamTarget" name="teamTarget">
            <label for="teamInfo_info_teamTarget_input">部组目标:</label>
            <input type="text" id="teamInfo_info_teamTarget_input" name="teamTarget" class="readyInput"/>
            <img name="warning" src="img/warning.png"/>
        </div>
    </div>
    <div id="teamInfo_button">
        <input type="button" id="teamInfo_button_cancel" name="cancel" value="取消"/>
        <input type="button" id="teamInfo_button_submit" name="submit" value="提交"/>
    </div>
</div>

<script src="js/pub/jquery-1.10.2.js"></script>
<script src="js/pub/jquery.dataTables.js"></script>
<script src="js/pub/jquery.rotate.min.js"></script>
<script src="js/pub/easydialog.js"></script>
<script src="js/main/pub/publicForInfo.js"></script>
<script src="js/main/pub/publicForMain.js"></script>
<script src="js/main/operationTeam.js"></script>
</body>
</html>

