package partInfoAction;

import areaInfoAction.getAreaInfo;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import pub.excalAction;
import pub.mysqlAction;
import pub.path;

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
 * Created by sunyuyang on 17/8/2.
 */
@WebServlet(name = "getAreaName")
public class getAreaName extends HttpServlet {
    Logger logger = Logger.getLogger(getAreaName.class);
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

            String[] info = request.getParameterValues("info");

            try{
                mysql.mysqlStart();
                mysqlStr="select areaName from area where areaCode='"+ info[0] +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum > 0){
                    data.put("result","success");
                    data.put("info",result.get(0));
                }else{
                    data.put("result","faill");
                    data.put("cause", "无法找到该区域");
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法找到该区域,请再次尝试,或者联系管理员");
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


