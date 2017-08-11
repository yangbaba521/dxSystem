package teamInfoAction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import partInfoAction.singleAddPart;
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
 * Created by sunyuyang on 17/8/4.
 */
@WebServlet(name = "singleAddTeam")
public class singleAddTeam extends HttpServlet {
    Logger logger = Logger.getLogger(singleAddTeam.class);
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

            String[] teamInfo = request.getParameterValues("teamInfo");

            try{
                mysql.mysqlStart();
                mysqlStr = "select level from user where userCode='"+ userCode +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum <= 0 || (!(result.get(0).equals("root")) && !(result.get(0).equals("charge")) )){
                    data.put("result","faill");
                    data.put("cause","您没有修改人力信息的权限");
                }else{
                    mysqlStr = "select partCode from part where partCode='"+ teamInfo[2] +"' and partName='"+teamInfo[3]+"';";
                    resNum = mysql.doQuery(mysqlStr,result);
                    if(resNum > 0) {
                        mysqlStr = "select teamCode from team where teamCode='"+ teamInfo[0] +"';";
                        resNum = mysql.doQuery(mysqlStr,result);
                        if(resNum == 0) {
                            mysqlStr = "insert into team value(null";
                            for (int i = 0; i < teamInfo.length; i++) {
                                mysqlStr += ",'" + teamInfo[i] + "'";
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
                            data.put("cause", "部组已经存在");
                        }
                    }else{
                        data.put("result", "faill");
                        data.put("cause", "所属区部不存在");
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法写入部组,请再次尝试,或者联系管理员");
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

