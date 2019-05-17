package Utils;

import java.sql.*;

/**
 * 连接数据库
 */
public class DBUtils {

    private static String dbUrl ="jdbc:mysql://localhost:3306/Gproject?useUnicode=true&useSSL=false&characterEncoding=UTF-8";
    private static String dbUser="root";
    private static String dbPassword="123456";
    //数据库驱动名字
    private static String jdbcName = "com.mysql.jdbc.Driver";


    /**
     * 获取连接
     * @return
     * @throws Exception
     */
    public static Connection getCon() throws Exception{
        Class.forName(jdbcName);
        Connection conn = DriverManager.getConnection(dbUrl,dbUser,dbPassword);
        return conn;
    }

    /**
     * 关闭连接
     * @param stmt
     * @param conn
     * @throws Exception
     */
    public static void close(Statement stmt, Connection conn) throws Exception{
        if(stmt!=null){
            stmt.close();
            if(conn!=null){
                conn.close();
            }
        }
    }

    /**
     * 关闭连接
     * @param cstmt
     * @param conn
     * @throws Exception
     */
    public static void close(CallableStatement cstmt, Connection conn) throws Exception{
        if(cstmt!=null){
            cstmt.close();
            if(conn!=null){
                conn.close();
            }
        }
    }


    /**
     * 关闭连接
     * @param pstmt
     * @param conn
     * @throws SQLException
     */
    public static void close(PreparedStatement pstmt, Connection conn) throws SQLException{
        if(pstmt!=null){
            pstmt.close();
            if(conn!=null){
                conn.close();
            }
        }
    }


    /**
     * 重载关闭方法
     * @param pstmt
     * @param conn
     * @throws Exception
     */
    public void close(ResultSet rs,PreparedStatement pstmt, Connection conn) throws Exception{
        if(rs!=null){
            rs.close();
            if(pstmt!=null){
                pstmt.close();
                if(conn!=null){
                    conn.close();
                }

            }
        }

    }


    public static void main(String [] args) throws SQLException {
        //测试连接数据库
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql ="select * from spectra_data_dstar ";
            pstmt=conn.prepareStatement(sql);
            //pstmt.setString(1,userdb.getCustom1());
            rs=pstmt.executeQuery();
            if (rs.next()) {
                System.out.println(true);
            }
            else{

            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }
}
