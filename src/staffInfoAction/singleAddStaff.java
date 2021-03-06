package staffInfoAction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.mysqlAction;
import teamInfoAction.singleAddTeam;

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

import pub.md5;

/**
 * Created by sunyuyang on 17/8/10.
 */
@WebServlet(name = "singleAddStaff")
public class singleAddStaff extends HttpServlet {
    Logger logger = Logger.getLogger(singleAddStaff.class);
    Thread thread = Thread.currentThread();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject data = new JSONObject();
        try{
            mysqlAction mysql = new mysqlAction();
            List<String> result = new ArrayList();
            String mysqlStr = null;
            int resNum = 0;

            HttpSession session = request.getSession();
            String userCode = (String) session.getAttribute("userOnline");

            md5 enc = new md5();

            String[] staffInfo = request.getParameterValues("staffInfo");
            int belongType = staffInfo[2].length();
            String belongTypeStr = "";
            if(belongType == 12){
                belongTypeStr = "area";
            }else if(belongType == 15){
                belongTypeStr = "part";
            }else{
                belongTypeStr = "team";
            }

            try{
                mysql.mysqlStart();
                mysqlStr = "select level from user where userCode='"+ userCode +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum <= 0 || (!(result.get(0).equals("root")) && !(result.get(0).equals("charge")) )){
                    data.put("result","faill");
                    data.put("cause","您没有修改人力信息的权限");
                }else{
                    mysqlStr = "select "+belongTypeStr+"Code from "+belongTypeStr+" where "+belongTypeStr+"Code='"+ staffInfo[2] +"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum > 0) {
                        mysqlStr = "select staffCode from staff where staffCode='"+ staffInfo[0] +"';";
                        resNum = mysql.doQuery(mysqlStr,result);
                        if(resNum == 0) {
                            mysqlStr = "insert into staff value(null";
                            for (int i = 0; i < staffInfo.length; i++) {
                                if(i == 7 || i == 17 || i == 20 ){
                                    staffInfo[i] = enc.convertMD5(staffInfo[i]);
                                }
                                mysqlStr += ",'" + staffInfo[i] + "'";
                            }
                            mysqlStr += ");";
                            if (mysql.doUpdate(mysqlStr)) {
                                data.put("result", "success");
                            } else {
                                data.put("result", "faill");
                                data.put("cause", "写入失败,请再次尝试,或者联系管理员");
                            }
                        }else{
                            data.put("result", "faill");
                            data.put("cause", "组员已经存在");
                        }
                    }else{
                        data.put("result", "faill");
                        data.put("cause", "所属编号不存在");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法写入组员,请再次尝试,或者联系管理员");
                data.put("debug",e.toString());
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

