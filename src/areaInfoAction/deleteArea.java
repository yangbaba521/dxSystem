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
 * Created by sunyuyang on 17/7/31.
 */
@WebServlet(name = "deleteArea")
public class deleteArea extends HttpServlet {
    Logger logger = Logger.getLogger(deleteArea.class);
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

            String areaCode = request.getParameter("areaCode");

            List<String> part = new ArrayList();
            List<String> team = new ArrayList();
            boolean teamFlag = true;
            boolean staffFlag = true;

            try{
                mysql.mysqlStart();
                mysql.connection.setAutoCommit(false);
                mysqlStr = "select level from user where userCode='"+ userCode +"';";
                resNum = mysql.doQuery(mysqlStr,result);
                if(resNum <= 0 || (!(result.get(0).equals("root")) && !(result.get(0).equals("charge")) )){
                    data.put("result","faill");
                    data.put("cause","您没有修改人力信息的权限");
                }else{
                    mysqlStr="delete from area where areaCode='"+areaCode+"';";
                    if(mysql.doUpdateNoRes(mysqlStr)) {
                        mysqlStr = "delete from staff where belongCode='" + areaCode + "';";
                        if(mysql.doUpdateNoRes(mysqlStr)) {
                            mysqlStr = "select partCode from part where areaCode='" + areaCode + "';";
                            resNum = mysql.doQuery(mysqlStr, part);
                            if (resNum >= 0) {
                                mysqlStr = "delete from part where areaCode='" + areaCode + "';";
                                if (mysql.doUpdateNoRes(mysqlStr)) {
                                    for (int i = 0; i < part.size(); i++) {
                                        mysqlStr = "select teamCode from team where partCode='" + part.get(i) + "';";
                                        resNum = mysql.doQuery(mysqlStr, team);
                                        mysqlStr = "delete from team where partCode='" + part.get(i) + "';";
                                        if (!mysql.doUpdateNoRes(mysqlStr)) {
                                            teamFlag = false;
                                            mysql.connection.rollback();
                                            data.put("result", "faill");
                                            data.put("cause", "删除部组失败,请再次尝试,或者联系管理员");
                                            break;
                                        }
                                        mysqlStr = "delete from staff where belongCode='" + part.get(i) + "';";
                                        if(!mysql.doUpdateNoRes(mysqlStr)) {
                                            teamFlag = false;
                                            mysql.connection.rollback();
                                            data.put("result", "faill");
                                            data.put("cause", "删除区部主管失败,请再次尝试,或者联系管理员");
                                            break;
                                        }
                                    }
                                    if (teamFlag) {
                                        for (int i = 0; i < team.size(); i++) {
                                            mysqlStr = "delete from staff where belongCode='" + team.get(i) + "';";
                                            if (!mysql.doUpdateNoRes(mysqlStr)) {
                                                staffFlag = false;
                                                mysql.connection.rollback();
                                                data.put("result", "faill");
                                                data.put("cause", "删除部组失败,请再次尝试,或者联系管理员");
                                                break;
                                            }
                                        }
                                        if (staffFlag) {
                                            mysqlStr = "delete from staff where belongCode='" + areaCode + "';";
                                            if (mysql.doUpdateNoRes(mysqlStr)) {
                                                mysql.connection.commit();
                                                data.put("result", "success");
                                            } else {
                                                mysql.connection.rollback();
                                                data.put("result", "faill");
                                                data.put("cause", "删除区域主管失败,请再次尝试,或者联系管理员");
                                            }
                                        }
                                    }
                                } else {
                                    mysql.connection.rollback();
                                    data.put("result", "faill");
                                    data.put("cause", "删除区部失败,请再次尝试,或者联系管理员");
                                }
                            } else {
                                mysql.connection.rollback();
                                data.put("result", "faill");
                                data.put("cause", "检索区部失败,请再次尝试,或者联系管理员");
                            }
                        }else {
                            mysql.connection.rollback();
                            data.put("result", "faill");
                            data.put("cause", "删除区域主管失败,请再次尝试,或者联系管理员");
                        }
                    } else {
                        mysql.connection.rollback();
                        data.put("result", "faill");
                        data.put("cause", "删除区域失败,请再次尝试,或者联系管理员");
                    }
                }
            }catch(Exception e){
                mysql.connection.rollback();
                e.printStackTrace();
                logger.error(thread.getId() + ":" + e.toString());
                data.put("result","faill");
                data.put("cause", "无法删除区域,请再次尝试,或者联系管理员");
                data.put("debug",e.toString());
            }finally{
                mysql.connection.setAutoCommit(true);
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
