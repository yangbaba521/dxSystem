package staffInfoAction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import partInfoAction.getPartInfo;
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
 * Created by sunyuyang on 17/8/7.
 */
@WebServlet(name = "getSelectTypeInfo")
public class getSelectTypeInfo extends HttpServlet {
    Logger logger = Logger.getLogger(getSelectTypeInfo.class);
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

            try{
                mysql.mysqlStart();
                mysqlStr = "select levelCode,levelCN from levelMap";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("level",result);
                    result.clear();
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
                }
                mysqlStr = "select levelCode,levelEN from levelMap";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("levelStart",result);
                    result.clear();
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
                }
                mysqlStr = "select stateCode,stateName from staffStateMap";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("state",result);
                    result.clear();
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
                }
                mysqlStr = "select channelCode,channelName from staffChannelMap";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("channel",result);
                    result.clear();
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
                }
                mysqlStr = "select eduCode,eduName from staffEduMap";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("edu",result);
                    result.clear();
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
                }
                mysqlStr = "select natureCode,natureName from staffNatureMap";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("nature",result);
                    result.clear();
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法找到筛选信息,请再次尝试,或者联系管理员");
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


