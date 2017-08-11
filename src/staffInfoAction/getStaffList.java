package staffInfoAction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.mysqlAction;
import teamInfoAction.getTeamList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunyuyang on 17/8/7.
 */
@WebServlet(name = "getStaffList")
public class getStaffList extends HttpServlet {
    Logger logger = Logger.getLogger(getStaffList.class);
    Thread thread = Thread.currentThread();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //初始化对象
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        PrintWriter out = response.getWriter();
        JSONObject data = new JSONObject();

        try{
            //数据库对象
            mysqlAction mysql = new mysqlAction();
            List<String> result = new ArrayList();
            String mysqlStr = null;
            int resNum = 0;


            List<String> selectResult = new ArrayList();
            List<String> selectMap = new ArrayList();

            //session,获取用户信息
            HttpSession session = request.getSession();
            String sessionId = session.getId();
            String userCode = (String) session.getAttribute("userOnline");

            logger.debug(thread.getId() + ":获取区域列表");

            //直接返回前台
            String draw = request.getParameter("draw");
            //数据起始位置
            int startIndex = Integer.parseInt(request.getParameter("start"));
            //每页显示的条数
            int pageSize = Integer.parseInt(request.getParameter("length"));
            int recordsTotal = 0;//总条数
            int recordsFiltered = 0;//帅选后的条数

            //查询条件
            String selectType = request.getParameter("type")+"";
            String selectVal = request.getParameter("val")+"";
            try{
                mysql.mysqlStart();
                //查询名下所有活动
                mysqlStr = "select * from staff;";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0) {

                    //设置活动总数
                    recordsTotal = resNum;
                    //分辨是否按条件查询
                    int index = 0;
                    if(selectType.equals("staffCode")){
                        index = 1;
                    }else if(selectType.equals("staffName")){
                        index = 2;
                    }else if(selectType.equals("belongCode")){
                        index = 3;
                    }else if(selectType.equals("belongName")){
                        index = 4;
                    }else if(selectType.equals("leaderName")){
                        index = 5;
                    }else if(selectType.equals("staffEdu")){
                        mysqlStr = "select eduName from staffEduMap where eduCode='"+ selectVal +"';";
                        if(mysql.doQuery(mysqlStr,selectMap) > 0){
                            selectVal = selectMap.get(0)+"";
                        }
                        index = 17;
                    }else if(selectType.equals("staffChannel")){
                        mysqlStr = "select channelName from staffChannelMap where channelCode='"+ selectVal +"';";
                        if(mysql.doQuery(mysqlStr,selectMap) > 0){
                            selectVal = selectMap.get(0)+"";
                        }
                        index = 24;
                    }else if(selectType.equals("staffLevel")){
                        mysqlStr = "select levelCN from levelMap where levelCode='"+ selectVal +"';";
                        if(mysql.doQuery(mysqlStr,selectMap) > 0){
                            selectVal = selectMap.get(0)+"";
                        }
                        index = 12;
                    }else if(selectType.equals("staffState")){
                        mysqlStr = "select stateName from staffStateMap where stateCode='"+ selectVal +"';";
                        if(mysql.doQuery(mysqlStr,selectMap) > 0){
                            selectVal = selectMap.get(0)+"";
                        }
                        index = 16;
                    }else if(selectType.equals("staffNature")){
                        mysqlStr = "select natureName from staffNatureMap where natureCode='"+ selectVal +"';";
                        if(mysql.doQuery(mysqlStr,selectMap) > 0){
                            selectVal = selectMap.get(0)+"";
                        }
                        index = 23;
                    }
                    if(index != 0){
                        for(int i=0; i<result.size()/26; i++){
                            if(result.get(i*26 + index).equals(selectVal)){
                                for(int k =0; k<26; k++ ){
                                    selectResult.add(result.get(i*26+k));
                                }
                            }
                        }
                        recordsFiltered = selectResult.size()/26;
                    }else{
                        for(int k =0; k<result.size(); k++ ){
                            selectResult.add(result.get(k));
                        }
                        recordsFiltered = recordsTotal;
                    }
                }else{
                    //没有活动
                    recordsTotal = 0;
                }
                //检验是否查询到结果
                if(recordsTotal != 0) {
                    //设置循环次数,不足每页显示的数量,就用剩余数量代替,超过每页数量,用每页上限代替
                    int num =pageSize;
                    if(selectResult.size() / 26 - startIndex < pageSize){
                        num = selectResult.size() / 26 - startIndex;
                    }
                    //读取 从其实位置+每页上限或者不足的剩余
                    String[][] array=new String [num][9];
                    for (int i = startIndex; i < startIndex+num; i++) {
                        array[i-startIndex][0] = selectResult.get(26 * i + 1);
                        array[i-startIndex][1] = selectResult.get(26 * i + 2);
                        array[i-startIndex][2] = selectResult.get(26 * i + 3);
                        array[i-startIndex][3] = selectResult.get(26 * i + 4);
                        array[i-startIndex][4] = selectResult.get(26 * i + 5);
                        array[i-startIndex][5] = selectResult.get(26 * i + 6);
                        array[i-startIndex][6] = selectResult.get(26 * i + 12);
                        array[i-startIndex][7] = selectResult.get(26 * i + 16);
                        array[i-startIndex][8] = selectResult.get(26 * i + 1);
                    }
                    data.put("data",array);
                }
                data.put("draw",draw);
                data.put("recordsTotal",recordsTotal);
                data.put("recordsFiltered",recordsFiltered);


            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                recordsTotal = 0;
                recordsFiltered = 0;
                data.put("draw",draw);
                data.put("recordsTotal",recordsTotal);
                data.put("recordsFiltered",recordsFiltered);
            }finally{
                mysql.mysqlClose();
            }


        }catch(Exception e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());

        }finally {
            out = response.getWriter();
            out.print(data);
            out.flush();
            if(out != null) {
                out.close();
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}



