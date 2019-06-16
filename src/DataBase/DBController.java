package DataBase;

import Utils.DBUtils;
import handle.Spectra;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 操作数据库
 */
public class DBController {
    /**
     * 查找 dstar数据库里面 前几名
     * @param versionid
     * @param bugid
     * @param line
     * @return
     */
    public static List<Spectra> findSpectraByDstar(String versionid,int bugid,int line) throws SQLException{
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<Spectra> spectraList=new ArrayList<>();
        try {
            conn = DBUtils.getCon();
                String sqlfind="select * from spectra_data_dstar where versionid=? and bugid =? order by suspicion desc";
                pstmt=conn.prepareStatement(sqlfind);
                pstmt.setString(1,versionid);
                pstmt.setInt(2,bugid);
                rs=pstmt.executeQuery();
                System.out.println("start finding.....");
                //如果存在
                int index=0; //限定查询多少行
                while (rs.next() && index<line){
                    Spectra spectra=new Spectra();
                    System.out.println(rs.getString(4)+rs.getInt(5)+rs.getDouble(6));
                    spectra.setFilename(rs.getString(4));
                    spectra.setLine(rs.getInt(5));
                    spectra.setSuspicious(rs.getDouble(6));
                    spectraList.add(spectra);
                    index++;
                }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
        return spectraList;
    }

    /**
     * 查找 Ochiai数据库里面 前几名
     * @param versionid
     * @param bugid
     * @param line
     * @return
     */
    public static List<Spectra> findSpectraByOchiai(String versionid,int bugid,int line) throws SQLException{
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        List<Spectra> spectraList=new ArrayList<>();
        try {
            conn = DBUtils.getCon();
            String sqlfind="select * from spectra_data_ochiai where versionid=? and bugid =? order by suspicion desc";
            pstmt=conn.prepareStatement(sqlfind);
            pstmt.setString(1,versionid);
            pstmt.setInt(2,bugid);
            rs=pstmt.executeQuery();
            System.out.println("start finding.....");
            //如果存在
            int index=0; //限定查询多少行
            while (rs.next() && index<line){
                Spectra spectra=new Spectra();
                System.out.println(rs.getString(4)+rs.getInt(5)+rs.getDouble(6));
                spectra.setFilename(rs.getString(4));
                spectra.setLine(rs.getInt(5));
                spectra.setSuspicious(rs.getDouble(6));
                spectraList.add(spectra);
                index++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
        return spectraList;
    }

    //Dstar执行结果 spectraID 加入数据库
    //如果存在，不加入
    /**
     * DB add spectra
     * @param
     */
    public static void addSpectraByDstar(String versionid,int bugid,List<Spectra> spectraList) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            for (Spectra spectra:spectraList){
                String sqlfind="select * from spectra_data_dstar where versionid=? and bugid =? and filename =? and line =?";
                pstmt=conn.prepareStatement(sqlfind);
                pstmt.setString(1,versionid);
                pstmt.setInt(2,bugid);
                pstmt.setString(3,spectra.getFilename());
                pstmt.setInt(4,spectra.getLine());
                rs=pstmt.executeQuery();
                //如果存在
                if (rs.next()){


                    String sqlupdate="update spectra_data_dstar set suspicion =? where versionid =? and bugid =? and filename =? and line =?";
                    pstmt =conn.prepareStatement(sqlupdate);
                    pstmt.setDouble(1,spectra.getSuspicious());
                    pstmt.setString(2,versionid);
                    pstmt.setInt(3,bugid);
                    pstmt.setString(4,spectra.getFilename());
                    pstmt.setInt(5,spectra.getLine());
                    int updateresult=pstmt.executeUpdate();
                    System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");
                }
                else {
                    String sqlInsert="insert into spectra_data_dstar (versionid,bugid,filename,line,suspicion)values(?,?,?,?,?)";
                    pstmt=conn.prepareStatement(sqlInsert);
                    pstmt.setString(1,versionid);
                    pstmt.setInt(2,bugid);
                    pstmt.setString(3,spectra.getFilename());
                    pstmt.setInt(4,spectra.getLine());
                    //System.out.println(spectra.getSuspicious());
                    pstmt.setDouble(5,spectra.getSuspicious());
                    int insertresult=pstmt.executeUpdate();
                    System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    // ochiai
    //M_D
    public static void addSpectraByMetallDstar(String versionid,int bugid,List<Spectra> spectraList) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            for (Spectra spectra:spectraList){
                String sqlfind="select * from spectra_data_Metall_dstar where versionid=? and bugid =? and filename =? and line =?";
                pstmt=conn.prepareStatement(sqlfind);
                pstmt.setString(1,versionid);
                pstmt.setInt(2,bugid);
                pstmt.setString(3,spectra.getFilename());
                pstmt.setInt(4,spectra.getLine());
                rs=pstmt.executeQuery();
                //如果存在
                if (rs.next()){


                    String sqlupdate="update spectra_data_Metall_dstar set suspicion =? where versionid =? and bugid =? and filename =? and line =?";
                    pstmt =conn.prepareStatement(sqlupdate);
                    pstmt.setDouble(1,spectra.getSuspicious());
                    pstmt.setString(2,versionid);
                    pstmt.setInt(3,bugid);
                    pstmt.setString(4,spectra.getFilename());
                    pstmt.setInt(5,spectra.getLine());
                    int updateresult=pstmt.executeUpdate();
                    System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");
                }
                else {
                    String sqlInsert="insert into spectra_data_Metall_dstar (versionid,bugid,filename,line,suspicion)values(?,?,?,?,?)";
                    pstmt=conn.prepareStatement(sqlInsert);
                    pstmt.setString(1,versionid);
                    pstmt.setInt(2,bugid);
                    pstmt.setString(3,spectra.getFilename());
                    pstmt.setInt(4,spectra.getLine());
                    //System.out.println(spectra.getSuspicious());
                    pstmt.setDouble(5,spectra.getSuspicious());
                    int insertresult=pstmt.executeUpdate();
                    System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    public static void addSpectraByOchiai(String versionid,int bugid,List<Spectra> spectraList) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            for (Spectra spectra:spectraList){
                String sqlfind="select * from spectra_data_ochiai where versionid=? and bugid =? and filename =? and line =?";
                pstmt=conn.prepareStatement(sqlfind);
                pstmt.setString(1,versionid);
                pstmt.setInt(2,bugid);
                pstmt.setString(3,spectra.getFilename());
                pstmt.setInt(4,spectra.getLine());
                rs=pstmt.executeQuery();
                //如果存在
                if (rs.next()){


                    String sqlupdate="update spectra_data_ochiai set suspicion =? where versionid =? and bugid =? and filename =? and line =?";
                    pstmt =conn.prepareStatement(sqlupdate);
                    pstmt.setDouble(1,spectra.getSuspicious());
                    pstmt.setString(2,versionid);
                    pstmt.setInt(3,bugid);
                    pstmt.setString(4,spectra.getFilename());
                    pstmt.setInt(5,spectra.getLine());
                    int updateresult=pstmt.executeUpdate();
                    System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");
                }
                else {
                    String sqlInsert="insert into spectra_data_ochiai (versionid,bugid,filename,line,suspicion)values(?,?,?,?,?)";
                    pstmt=conn.prepareStatement(sqlInsert);
                    pstmt.setString(1,versionid);
                    pstmt.setInt(2,bugid);
                    pstmt.setString(3,spectra.getFilename());
                    pstmt.setInt(4,spectra.getLine());
                    //System.out.println(spectra.getSuspicious());
                    pstmt.setDouble(5,spectra.getSuspicious());
                    int insertresult=pstmt.executeUpdate();
                    System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }
    // ochiai
    //M_O
    public static void addSpectraByMetallOchiai(String versionid,int bugid,List<Spectra> spectraList) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            for (Spectra spectra:spectraList){
                String sqlfind="select * from spectra_data_Metall_ochiai where versionid=? and bugid =? and filename =? and line =?";
                pstmt=conn.prepareStatement(sqlfind);
                pstmt.setString(1,versionid);
                pstmt.setInt(2,bugid);
                pstmt.setString(3,spectra.getFilename());
                pstmt.setInt(4,spectra.getLine());
                rs=pstmt.executeQuery();
                //如果存在
                if (rs.next()){
                    String sqlupdate="update spectra_data_Metall_ochiai set suspicion =? where versionid =? and bugid =? and filename =? and line =?";
                    pstmt =conn.prepareStatement(sqlupdate);
                    pstmt.setDouble(1,spectra.getSuspicious());
                    pstmt.setString(2,versionid);
                    pstmt.setInt(3,bugid);
                    pstmt.setString(4,spectra.getFilename());
                    pstmt.setInt(5,spectra.getLine());
                    int updateresult=pstmt.executeUpdate();
                    System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");
                }
                else {
                    String sqlInsert="insert into spectra_data_Metall_ochiai (versionid,bugid,filename,line,suspicion)values(?,?,?,?,?)";
                    pstmt=conn.prepareStatement(sqlInsert);
                    pstmt.setString(1,versionid);
                    pstmt.setInt(2,bugid);
                    pstmt.setString(3,spectra.getFilename());
                    pstmt.setInt(4,spectra.getLine());
                    //System.out.println(spectra.getSuspicious());
                    pstmt.setDouble(5,spectra.getSuspicious());
                    int insertresult=pstmt.executeUpdate();
                    System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }


    //only M
    public static void addSpectraByMetallaxis(String versionid,int bugid,List<Spectra> spectraList) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            for (Spectra spectra:spectraList){
                String sqlfind="select * from spectra_data_Metallaxis where versionid=? and bugid =? and filename =? and line =?";
                pstmt=conn.prepareStatement(sqlfind);
                pstmt.setString(1,versionid);
                pstmt.setInt(2,bugid);
                pstmt.setString(3,spectra.getFilename());
                pstmt.setInt(4,spectra.getLine());
                rs=pstmt.executeQuery();
                //如果存在
                if (rs.next()){


                    String sqlupdate="update spectra_data_Metallaxis set suspicion =? where versionid =? and bugid =? and filename =? and line =?";
                    pstmt =conn.prepareStatement(sqlupdate);
                    pstmt.setDouble(1,spectra.getSuspicious());
                    pstmt.setString(2,versionid);
                    pstmt.setInt(3,bugid);
                    pstmt.setString(4,spectra.getFilename());
                    pstmt.setInt(5,spectra.getLine());
                    int updateresult=pstmt.executeUpdate();
                    System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");
                }
                else {
                    String sqlInsert="insert into spectra_data_Metallaxis (versionid,bugid,filename,line,suspicion)values(?,?,?,?,?)";
                    pstmt=conn.prepareStatement(sqlInsert);
                    pstmt.setString(1,versionid);
                    pstmt.setInt(2,bugid);
                    pstmt.setString(3,spectra.getFilename());
                    pstmt.setInt(4,spectra.getLine());
                    //System.out.println(spectra.getSuspicious());
                    pstmt.setDouble(5,spectra.getSuspicious());
                    int insertresult=pstmt.executeUpdate();
                    System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    //下面都是统计时间了
    /**
     * shellruntime脚本 运行时间
     * @param runtime
     * @param project
     * @param id
     */
    public static void addGzoltarTime(long runtime, String project, int id) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
                String sql="select * from gzoltar_time where versionid =? and bugid =?";
                pstmt=conn.prepareStatement(sql);
                pstmt.setString(1,project);
                pstmt.setInt(2,id);
                 rs=pstmt.executeQuery();
                //如果存在
                if (rs.next()){
                    String sqlupdate="update gzoltar_time set run_time =? where versionid =? and bugid =?";
                    pstmt = conn.prepareStatement(sqlupdate);
                    pstmt.setDouble(1, runtime);
                    pstmt.setString(2, project);
                    pstmt.setInt(3,id);
                    int updateresult=pstmt.executeUpdate();
                    System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

                }
                else {
                    String sqlInsert="insert into gzoltar_time (versionid,bugid,run_time)values(?,?,?)";
                    pstmt=conn.prepareStatement(sqlInsert);
                    pstmt.setString(1,project);
                    pstmt.setInt(2,id);
                    pstmt.setDouble(3,runtime);
                    int insertresult=pstmt.executeUpdate();
                    System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    public static void addKillmapTime(long runtime, String project, int id) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql="select * from killmap_time where versionid =? and bugid =?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,project);
            pstmt.setInt(2,id);
            rs=pstmt.executeQuery();
            //如果存在
            if (rs.next()){
                String sqlupdate="update killmap_time set run_time =? where versionid =? and bugid =?";
                pstmt = conn.prepareStatement(sqlupdate);
                pstmt.setDouble(1, runtime);
                pstmt.setString(2, project);
                pstmt.setInt(3,id);
                int updateresult=pstmt.executeUpdate();
                System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

            }
            else {
                String sqlInsert="insert into killmap_time (versionid,bugid,run_time)values(?,?,?)";
                pstmt=conn.prepareStatement(sqlInsert);
                pstmt.setString(1,project);
                pstmt.setInt(2,id);
                pstmt.setDouble(3,runtime);
                int insertresult=pstmt.executeUpdate();
                System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    public static void addDstarRunTime(String versionID, int bugID, long runtime) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql="select * from dstar_time where versionid =? and bugid =?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,versionID);
            pstmt.setInt(2,bugID);
            rs=pstmt.executeQuery();
            //如果存在
            if (rs.next()){
                String sqlupdate="update dstar_time set run_time =? where versionid =? and bugid =?";
                pstmt = conn.prepareStatement(sqlupdate);
                pstmt.setDouble(1, runtime);
                pstmt.setString(2, versionID);
                pstmt.setInt(3,bugID);
                int updateresult=pstmt.executeUpdate();
                System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

            }
            else {
                String sqlInsert="insert into dstar_time (versionid,bugid,run_time)values(?,?,?)";
                pstmt=conn.prepareStatement(sqlInsert);
                pstmt.setString(1,versionID);
                pstmt.setInt(2,bugID);
                pstmt.setDouble(3,runtime);
                int insertresult=pstmt.executeUpdate();
                System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }


    public static void addOchiaiRunTime(String versionID, int bugID, long runtime) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql="select * from ochiai_time where versionid =? and bugid =?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,versionID);
            pstmt.setInt(2,bugID);
            rs=pstmt.executeQuery();
            //如果存在
            if (rs.next()){
                String sqlupdate="update ochiai_time set run_time =? where versionid =? and bugid =?";
                pstmt = conn.prepareStatement(sqlupdate);
                pstmt.setDouble(1, runtime);
                pstmt.setString(2, versionID);
                pstmt.setInt(3,bugID);
                int updateresult=pstmt.executeUpdate();
                System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

            }
            else {
                String sqlInsert="insert into ochiai_time (versionid,bugid,run_time)values(?,?,?)";
                pstmt=conn.prepareStatement(sqlInsert);
                pstmt.setString(1,versionID);
                pstmt.setInt(2,bugID);
                pstmt.setDouble(3,runtime);
                int insertresult=pstmt.executeUpdate();
                System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    public static void addMetallRunTime(String versionID, int bugID, long runtime) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql="select * from Metall_time where versionid =? and bugid =?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,versionID);
            pstmt.setInt(2,bugID);
            rs=pstmt.executeQuery();
            //如果存在
            if (rs.next()){
                String sqlupdate="update Metall_time set run_time =? where versionid =? and bugid =?";
                pstmt = conn.prepareStatement(sqlupdate);
                pstmt.setDouble(1, runtime);
                pstmt.setString(2, versionID);
                pstmt.setInt(3,bugID);
                int updateresult=pstmt.executeUpdate();
                System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

            }
            else {
                String sqlInsert="insert into Metall_time (versionid,bugid,run_time)values(?,?,?)";
                pstmt=conn.prepareStatement(sqlInsert);
                pstmt.setString(1,versionID);
                pstmt.setInt(2,bugID);
                pstmt.setDouble(3,runtime);
                int insertresult=pstmt.executeUpdate();
                System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    public static void addMetall_DstarRunTime(String versionID, int bugID, long runtime) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql="select * from Metall_Dstar_time where versionid =? and bugid =?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,versionID);
            pstmt.setInt(2,bugID);
            rs=pstmt.executeQuery();
            //如果存在
            if (rs.next()){
                String sqlupdate="update Metall_Dstar_time set run_time =? where versionid =? and bugid =?";
                pstmt = conn.prepareStatement(sqlupdate);
                pstmt.setDouble(1, runtime);
                pstmt.setString(2, versionID);
                pstmt.setInt(3,bugID);
                int updateresult=pstmt.executeUpdate();
                System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

            }
            else {
                String sqlInsert="insert into Metall_Dstar_time (versionid,bugid,run_time)values(?,?,?)";
                pstmt=conn.prepareStatement(sqlInsert);
                pstmt.setString(1,versionID);
                pstmt.setInt(2,bugID);
                pstmt.setDouble(3,runtime);
                int insertresult=pstmt.executeUpdate();
                System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }

    public static void addMetall_OchiaiRunTime(String versionID, int bugID, long runtime) throws SQLException {
        //建立连接
        Connection conn = null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        try {
            conn = DBUtils.getCon();
            String sql="select * from Metall_Ochiai_time where versionid =? and bugid =?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,versionID);
            pstmt.setInt(2,bugID);
            rs=pstmt.executeQuery();
            //如果存在
            if (rs.next()){
                String sqlupdate="update Metall_Ochiai_time set run_time =? where versionid =? and bugid =?";
                pstmt = conn.prepareStatement(sqlupdate);
                pstmt.setDouble(1, runtime);
                pstmt.setString(2, versionID);
                pstmt.setInt(3,bugID);
                int updateresult=pstmt.executeUpdate();
                System.out.println("executeUpdate="+String.valueOf(updateresult)+"update success");

            }
            else {
                String sqlInsert="insert into Metall_Ochiai_time (versionid,bugid,run_time)values(?,?,?)";
                pstmt=conn.prepareStatement(sqlInsert);
                pstmt.setString(1,versionID);
                pstmt.setInt(2,bugID);
                pstmt.setDouble(3,runtime);
                int insertresult=pstmt.executeUpdate();
                System.out.println("executeInsert="+String.valueOf(insertresult)+"insert success");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            rs.close();
            DBUtils.close(pstmt, conn);
        }
    }


}

