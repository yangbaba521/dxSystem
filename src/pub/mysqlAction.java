package pub;

import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

/************************************************************************************************************
 类名:mysqlAction
 类功能:MySQL有关的函数和变量
 ************************************************************************************************************/

public class mysqlAction {

    private Statement statement = null; //mysql句柄
    public Connection connection = null;//链接语句

    String driverMysql = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/dxSystem?useUnicode=true&characterEncoding=utf8";
    String pwd = "abcd123%";
    String usr = "root";

    /************************************
     * @return int 错误类型代码 1000为正常
     * @function mysqlStart
     ***********************************/
    public boolean mysqlStart() throws SQLException {
        Logger logger = Logger.getLogger(mysqlAction.class);
        Thread thread = Thread.currentThread();
        logger.debug(thread.getId() + ":mysql start connect");
        try {
            logger.info(thread.getId() + ":drive=" + driverMysql);
            Class.forName(driverMysql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
        try {
            //logger.info(thread.getId() + ":url=" + url + ",usr=" + usr + ",pwd=" + pwd + ";");
            connection = DriverManager.getConnection(url, usr, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error(thread.getId() + ":" + e.toString());
            return false;
        }
        return true;
    }

    /************************************
     * @return int 错误类型代码 1000为正常
     * @function mysqlClose
     ***********************************/
    public boolean mysqlClose() throws SQLException {
        ResultSet res = null; //查询结果
        int resNum = -1;      //查询结果个数
        Logger logger = Logger.getLogger(mysqlAction.class);
        Thread thread = Thread.currentThread();
        logger.debug(thread.getId() + ":mysql close");
        statement.close();
        connection.close();
        if (res != null) {
            res.close();
        }
        return true;
    }

    /************************************
     * @return int 错误类型代码 1000为正常
     * @function insertinfo
     ***********************************/
    public boolean doUpdate(String sql) throws SQLException {
        ResultSet res = null; //查询结果
        int resNum = -1;      //查询结果个数
        Logger logger = Logger.getLogger(mysqlAction.class);
        Thread thread = Thread.currentThread();

        logger.debug(thread.getId() + ":" + sql);
        resNum = statement.executeUpdate(sql);
        if (resNum <= 0) {
            return false;
        }
        return true;
    }

    /************************************
     * @return int 错误类型代码 1000为正常
     * @function insertinfo
     ***********************************/
    public boolean doUpdateNoRes(String sql) throws SQLException {
        ResultSet res = null; //查询结果
        int resNum = -1;      //查询结果个数
        Logger logger = Logger.getLogger(mysqlAction.class);
        Thread thread = Thread.currentThread();

        logger.debug(thread.getId() + ":" + sql);
        resNum = statement.executeUpdate(sql);
        if (resNum < 0) {
            return false;
        }
        return true;
    }

    /************************************
     * @param sql    被检查对象 String
     * @param result 被检索的表单 String
     * @return int 错误类型代码 1000为正常
     * @function checkLog
     ***********************************/
    public int doQuery(String sql, List<String> result) throws SQLException {
        ResultSet res = null; //查询结果
        int resNum = 0;      //查询结果个数
        Logger logger = Logger.getLogger(mysqlAction.class);
        Thread thread = Thread.currentThread();
        logger.debug(thread.getId() + ":" + sql);
        res = statement.executeQuery(sql);
        ResultSetMetaData rsmd = res.getMetaData() ;
        int columnCount = rsmd.getColumnCount();
        if (res.next()) {
            do {
                resNum++;
                for(int i=0; i<columnCount; i++) {
                    result.add(res.getString(i+1));
                }
            } while (res.next());
        }
        return resNum;
    }
}

