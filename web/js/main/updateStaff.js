/**
 * Created by sunyuyang on 17/4/13.
 */
$(document).ready(function () {
   // showTable();
    $('#home_body_action_area_function_newAll_button').on('click',newAll);
    $('#home_body_action_area_function_newAll_file').on('change',newAllFileName);
    $('#home_body_action_area_function_newAll_update').on('click',submitFileNew);
});

function submitFileNew() {
    
}

function newAllFileName() {
    var file = $('#home_body_action_area_function_newAll_span');
    var state = file.attr('name');
    if(state == 'noFile'){
        file.attr('name','fileReady').attr('class','fileReady').text($(this).val());
    }else {
        file.attr('name','noFile').attr('class','noFile');
    }
}

function newAll() {
    var tage = $('#home_body_action_area_function_newAll_file');
    tage.trigger('click');
}

function showTable() {
    $('#table').DataTable({
        searching: true,
        serverSide: false,
        processing: true,
        bSort: true, //排序功能
        bLengthChange: false,
        sAjaxSource: "meetList/showList",
        columnDefs : [
           ],
        oLanguage: {
            sLengthMenu: "每页显示 _MENU_ 条记录",
            sZeroRecords: "抱歉， 没有找到",
            sInfo: "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            sInfoEmpty: "没有数据",
            sInfoFiltered: "(从 _MAX_ 条数据中检索)",
            oPaginate: {
                sFirst: "首页",
                sPrevious: "前一页",
                sNext: "后一页",
                sLast: "尾页"
            },
            sZeroRecords: "没有检索到数据",
            sSearch: "搜索",


        },
    });
}