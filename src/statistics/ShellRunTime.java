package statistics;

import DataBase.DBController;
import Utils.ShellUtils;
import jdk.nashorn.tools.Shell;

/**因为执行shell脚本生成分析文件时间太长，需要数万个cpu小时
 * 所以此类自动生成所有要分析的文件
 * 对于每个project 生成20个bug版本 供分析研究
 *
 * 同时统计时间
 *
 */

public class ShellRunTime {
    public static void main(String[] args){
        try {
            //runGzoltarALL("Closure",20);
            //runGzoltarALL("Lang",21);
            //runGzoltarALL("Time",21);



            runKillmapALL("Closure",20);
            //runKillmapALL("Lang",21);


            //runGzoltarOne("Math",20);
            //runKillmapALL("Math",21);
            //runGzoltarALL("Mockito",21);
            //runKillmapALL("Mockito",21);

            //runKillmapALL("Time",21);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runGzoltarSome(String project,int min,int max) throws Exception {

        for(int id=min;id<=max;id++) {
            long startTime = System.currentTimeMillis();
            ShellUtils.Run_Gzoltar(project, id);
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;
            DBController.addGzoltarTime(runtime, project, id);
        }
    }
    public static void runGzoltarOne(String project,int id) throws Exception {


            long startTime = System.currentTimeMillis();
            ShellUtils.Run_Gzoltar(project, id);
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;
            DBController.addGzoltarTime(runtime, project, id);

    }
    public static void runGzoltarALL(String project,int id) throws Exception {

        for(int index=1;index<id;index++) {
            long startTime = System.currentTimeMillis();
            ShellUtils.Run_Gzoltar(project, index);
            long endTime = System.currentTimeMillis();
            long runtime = endTime - startTime;
            DBController.addGzoltarTime(runtime, project, index);
        }
    }


    public static void runKillmapALL(String project,int id) throws Exception {

        for (int index=1;index<id;index++){
            long startTime=System.currentTimeMillis();
            ShellUtils.Run_Killmap(project,index);
            long endTime=System.currentTimeMillis();
            long runtime=endTime-startTime;
            DBController.addKillmapTime(runtime,project,index);
        }
    }
}
