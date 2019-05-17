package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 执行脚本的类
 * @Date 2019.4.29
 * @Author wuyiwen
 */
public class ShellUtils {


    //killmap
    public static String killmaptest(String cmd) throws Exception{

        //使用"sh -c 命令字符串"的方式解决管道和重定向的问题
        List<String> cmds = new LinkedList<String>();
        cmds.add("sh");
        cmds.add("-c");
        cmds.add(cmd);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        //重定向到标准输出
        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor(3, TimeUnit.SECONDS);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String result = sb.toString();
        return result;
    }
    public static void main(String[] args){
        try {
            Run_Killmap("Lang",7);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            Run_Gzoltar("Lang",7);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /**
     * gzoltar
     */

    public static void Run_Gzoltar(String projectid,int bugid) throws Exception {
        String cmd=Constants.FLDPATH+"/gzoltar/run_gzoltar.sh "+projectid+" "+bugid+" "+Constants.OUTPUTPATH+" developer";
        //使用"sh -c 命令字符串"的方式解决管道和重定向的问题
        List<String> cmds = new LinkedList<String>();
        System.out.println(cmd);
        cmds.add("sh");
        cmds.add("-c");
        cmds.add(cmd);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        //重定向到标准输出
        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor(3, TimeUnit.SECONDS);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String result = sb.toString();
        System.out.println(result);

    }


    /**
     *   ./killmap/scripts/generate-matrix.sh Lang 1 /tmp/Lang-1 Lang-1-mutants.log >lang-1-err.txt
     * @param versionID
     * @param bugID
     */
    public static void Run_Killmap(String versionID, int bugID) throws Exception{
        StringBuffer cmd=new StringBuffer("");
        cmd.append(Constants.FLDPATH);
        cmd.append("killmap/scripts/generate-matrix.sh ");
        cmd.append(versionID+" "+bugID);
        cmd.append(" /tmp/"+versionID+"-"+bugID+" ");
        cmd.append(Constants.FLDPATH+"killmap/out/");
        cmd.append(versionID+"-"+bugID+"-mutants.log >");
        cmd.append(Constants.FLDPATH+"killmap/out/"+versionID+"-"+bugID+"-err.txt");
        //使用string 太容易出错
        //String cmd=FLDPATH+"killmap/scripts/generate-matrix.sh "+versionID+" "+bugID+" /tmp/"+versionID+"-"+bugID+" "+FLDPATH+"killmap/out/"+versionID+"-"+bugID+"-mutants.log >"+FLDPATH+"killmap/out/"+versionID+"-"+bugID+"-err.txt";
        System.out.println(cmd.toString());
        //使用"sh -c 命令字符串"的方式解决管道和重定向的问题
        List<String> cmds = new LinkedList<String>();
        System.out.println(cmd);
        cmds.add("sh");
        cmds.add("-c");
        cmds.add(cmd.toString());
        ProcessBuilder pb = new ProcessBuilder(cmds);
        //重定向到标准输出
        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor(3, TimeUnit.SECONDS);
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        String result = sb.toString();
        System.out.println(result);
    }
}
