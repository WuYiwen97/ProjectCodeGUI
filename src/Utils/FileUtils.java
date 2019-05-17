package Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理类
 */
public class FileUtils {

    public static void main(String[] args){
        boolean flag=false;
        flag=gzoltarFileExist("Time",3);
        System.out.println(flag);

        boolean flag2=false;
        flag2=killmapFileExist("Lang",7);
        System.out.println(flag2);
    }


    /**
     * /Users/wuyiwen/Documents/GraduationProject/fault-localization-data/
     * analysis/pipeline-scripts/buggy-lines/Chart-1.buggy.lines
     * @param projectID
     * @param bugID
     * @return
     */
    public static List<String> buggylinesFile(String projectID,int bugID){
        StringBuffer msgBuggyLine=new StringBuffer();
        msgBuggyLine.append(Constants.FLDPATH);
        msgBuggyLine.append("analysis/pipeline-scripts/buggy-lines/");
        msgBuggyLine.append(projectID+"-"+bugID);
        msgBuggyLine.append(".buggy.lines");
        String buggyFilePath=msgBuggyLine.toString();
        File buggyFile=new File(buggyFilePath);
        BufferedReader reader = null;
        List<String> buggylines=new ArrayList<>();
        try {
            System.out.println("buggylines文件以行为单位读取文件内容，一次读一整行：reading......");
            reader = new BufferedReader(new FileReader(buggyFile));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("第" + line + "行：" +"（"+ tempString+"）");
                buggylines.add(tempString);
                line++;
            }
            //System.out.println(line);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return buggylines;

    }


    /**
     * 判断shell脚本有没有生成 gzoltar 文件
     */
    public static boolean gzoltarFileExist(String projectID,int bugID) {
        boolean flag=false;
        //文件路径
        StringBuffer msgMatrix=new StringBuffer("");
        StringBuffer msgSpectra=new StringBuffer("");
        msgMatrix.append(Constants.DOWNLOADFILE);
        msgMatrix.append(projectID+"/"+bugID+"/gzoltars/"+projectID+"/"+bugID+"/");
        msgMatrix.append("matrix");


        msgSpectra.append(Constants.DOWNLOADFILE);
        msgSpectra.append(projectID+"/"+bugID+"/gzoltars/"+projectID+"/"+bugID+"/");
        msgSpectra.append("spectra");
        /*msgMatrix.append(Constants.FLDPATH);
        msgMatrix.append("gzoltar/out/gzoltars/");
        msgMatrix.append(projectID+"/"+bugID+"/");
        msgMatrix.append("matrix");

        msgSpectra.append(Constants.FLDPATH);
        msgSpectra.append("gzoltar/out/gzoltars/");
        msgSpectra.append(projectID+"/"+bugID+"/");
        msgSpectra.append("spectra");*/
        String fileMatrixPath=msgMatrix.toString();
        String fileSpectraPath=msgSpectra.toString();
        System.out.println(fileMatrixPath);
        System.out.println(fileSpectraPath);
        File matrix=new File(fileMatrixPath);
        File spectra=new File(fileSpectraPath);
        if (matrix.exists()  && spectra.exists()){
            flag=true;
            return flag;
        }

        return flag;
    }

    /**
     * 判断killmap脚本有没有生成 killmap文件
     * @param projectID
     * @param bugID
     * @return
     */
    public static boolean killmapFileExist(String projectID,int bugID) {
        boolean flag=false;
        //文件路径
        StringBuffer msgMutants=new StringBuffer("");
        StringBuffer msgKillmap=new StringBuffer("");

        msgMutants.append(Constants.DOWNLOADFILE);
        msgMutants.append(projectID+"/"+bugID+"/killmaps/"+projectID+"/"+bugID+"/");
        msgMutants.append("mutants.log");

        msgKillmap.append(Constants.DOWNLOADFILE);
        msgKillmap.append(projectID+"/"+bugID+"/killmaps/"+projectID+"/"+bugID+"/");
        msgKillmap.append("killmap.csv");
        /*msgMutants.append(Constants.FLDPATH);
        msgMutants.append("killmap/out/");
        msgMutants.append(projectID+"-"+bugID+"-");
        msgMutants.append("mutants.log");


        msgKillmap.append(Constants.FLDPATH);
        msgKillmap.append("killmap/out/");
        msgKillmap.append(projectID+"-"+bugID+"-");
        msgKillmap.append("err.txt");*/
        String mutantsPath=msgMutants.toString();
        String killmapPath=msgKillmap.toString();
        System.out.println(mutantsPath);
        System.out.println(killmapPath);

        File mutants=new File(mutantsPath);
        File killmap=new File(killmapPath);

        if (mutants.exists()&&killmap.exists()){
            flag=true;
            return flag;
        }

        return flag;
    }
}
