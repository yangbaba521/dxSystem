package partInfoAction;

import areaInfoAction.getAreaList;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.mysqlAction;

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
 * Created by sunyuyang on 17/8/3.
 */
@WebServlet(name = "getPartList")
public class getPartList extends HttpServlet {
    Logger logger = Logger.getLogger(getPartList.class);
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
            String value = request.getParameter("val")+"";
            String selectVal = "";
            String[] compareType = new String[10];
            if(selectType.equals("partTarget")){
                compareType = value.split(",");
            }else{
                selectVal = value;
            }
            //检索数据库
            try{
                mysql.mysqlStart();
                //查询名下所有活动
                mysqlStr = "select * from part;";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0) {
                    //设置活动总数
                    recordsTotal = resNum;
                    //分辨是否按条件查询
                    int index = 0;
                    if(selectType.equals("partCode")){
                        index = 1;
                    }else if(selectType.equals("partName")){
                        index = 2;
                    }else if(selectType.equals("areaCode")){
                        index = 3;
                    }else if(selectType.equals("areaName")){
                        index = 4;
                    }else if(selectType.equals("leaderCode")){
                        index = 5;
                    }else if(selectType.equals("leaderName")){
                        index = 6;
                    }else if(selectType.equals("partTarget")){
                        index = 7;
                    }
                    if(index != 0 && index != 7){
                        for(int i=0; i<result.size()/8; i++){
                            if(result.get(i*8 + index).equals(selectVal)){
                                for(int k =0; k<8; k++ ){
                                    selectResult.add(result.get(i*8+k));
                                }
                            }
                        }
                        recordsFiltered = selectResult.size()/8;
                    }else if(index == 7){
                        for(int i=0; i<result.size()/8; i++) {
                            if (compareType[0].equals("greater")) {
                                if (Integer.parseInt(result.get(i * 8 + index)) > Integer.parseInt(compareType[1])) {
                                    for (int k = 0; k < 8; k++) {
                                        selectResult.add(result.get(i * 8 + k));
                                    }
                                }
                            } else if (compareType[0].equals("greater&equal")) {
                                if (Integer.parseInt(result.get(i * 8 + index)) >= Integer.parseInt(compareType[1])) {
                                    for (int k = 0; k < 8; k++) {
                                        selectResult.add(result.get(i * 8 + k));
                                    }
                                }
                            } else if (compareType[0].equals("equal")) {
                                if (Integer.parseInt(result.get(i * 8 + index)) == Integer.parseInt(compareType[1])) {
                                    for (int k = 0; k < 8; k++) {
                                        selectResult.add(result.get(i * 8 + k));
                                    }
                                }
                            } else if (compareType[0].equals("less&equal")) {
                                if (Integer.parseInt(result.get(i * 8 + index)) <= Integer.parseInt(compareType[1])) {
                                    for (int k = 0; k < 8; k++) {
                                        selectResult.add(result.get(i * 8 + k));
                                    }
                                }
                            } else if (compareType[0].equals("less")) {
                                if (Integer.parseInt(result.get(i * 8 + index)) < Integer.parseInt(compareType[1])) {
                                    for (int k = 0; k < 8; k++) {
                                        selectResult.add(result.get(i * 8 + k));
                                    }
                                }
                            } else {
                                if (Integer.parseInt(result.get(i * 8 + index)) > Integer.parseInt(compareType[1]) && Integer.parseInt(result.get(i * 8 + index)) < Integer.parseInt(compareType[2])) {
                                    for (int k = 0; k < 8; k++) {
                                        selectResult.add(result.get(i * 8 + k));
                                    }
                                }
                            }
                        }
                        recordsFiltered = selectResult.size()/8;
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
                    if(selectResult.size() / 8 - startIndex < pageSize){
                        num = selectResult.size() / 8 - startIndex;
                    }
                    //读取 从其实位置+每页上限或者不足的剩余
                    String[][] array=new String [num][8];
                    for (int i = startIndex; i < startIndex+num; i++) {
                        array[i-startIndex][0] = selectResult.get(8 * i + 1);
                        array[i-startIndex][1] = selectResult.get(8 * i + 2);
                        array[i-startIndex][2] = selectResult.get(8 * i + 3);
                        array[i-startIndex][3] = selectResult.get(8 * i + 4);
                        array[i-startIndex][4] = selectResult.get(8 * i + 5);
                        array[i-startIndex][5] = selectResult.get(8 * i + 6);
                        array[i-startIndex][6] = selectResult.get(8 * i + 7);
                        array[i-startIndex][7] = selectResult.get(8 * i + 1);
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

