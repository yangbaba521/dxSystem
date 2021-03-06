package areaInfoAction;
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
 * Created by sunyuyang on 17/7/28.
 */
@WebServlet(name = "singleAdd")
public class singleAdd extends HttpServlet {
    Logger logger = Logger.getLogger(singleAdd.class);
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

            path path = new path();
            excalAction excel = new excalAction();

            String[] areaInfo = request.getParameterValues("areaInfo");

            try{
                mysql.mysqlStart();
                mysqlStr = "select level from user where userCode='"+ userCode +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum <= 0 || (!(result.get(0).equals("root")) && !(result.get(0).equals("charge")) )){
                    data.put("result","faill");
                    data.put("cause","您没有修改人力信息的权限");
                }else{
                    mysqlStr = "select areaCode from area where areaCode='"+ areaInfo[0] +"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum == 0) {
                        mysqlStr = "insert into area value(null";
                        for (int i = 0; i < areaInfo.length; i++) {
                            mysqlStr += ",'" + areaInfo[i] + "'";
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
                        data.put("cause", "该区域已经存在");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法写入区域,请再次尝试,或者联系管理员");
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
