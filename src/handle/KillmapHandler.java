package handle;

import Utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KillmapHandler {
    public static List<Killmap> fileToKillmap(List<String> stringListKillmap){
        List<Killmap> killmapList=new ArrayList<>();
        //从 000000开始
        for (int index=0;index<stringListKillmap.size();index++){
            String[] strings=stringListKillmap.get(index).split(",",-1);
            //System.out.println("length is ==="+strings.length+"read line ===="+index);
            Killmap killmap=new Killmap();
            killmap.setTestcase(strings[0]);
            killmap.setMutantid(Integer.parseInt(strings[1]));
            killmap.setTimeout(Integer.parseInt(strings[2]));
            killmap.setOutcome(strings[3]);
            killmap.setRuntime(Integer.parseInt(strings[4]));
            killmap.setHash(strings[5]);
            //第6个为覆盖行 可能没有 绝大部分情况是没有的
            if (!strings[6].equals("")){
                String[] coverline=strings[6].split(" ");
                int[] coverlineInt = new int[coverline.length];
                for(int i=0;i<coverline.length;i++){
                    coverlineInt[i] = Integer.parseInt(coverline[i]);
                }
                killmap.setCoveredmutants(coverlineInt);
            }
            else{
               // killmap.setCoveredmutants();
            }
            killmapList.add(killmap);
        }
        return killmapList;
    }


    public static List<String> readKillmapFileByLines(String versionid,int bugid){
        StringBuffer msgKillmap=new StringBuffer("");
        msgKillmap.append(Constants.DOWNLOADFILE);
        msgKillmap.append(versionid+"/"+bugid+"/killmaps/"+versionid+"/"+bugid+"/");
        msgKillmap.append("killmap.csv");
        /*msgKillmap.append(Constants.FLDPATH);
        msgKillmap.append("killmap/out/");
        msgKillmap.append(versionid+"-"+bugid+"-");
        msgKillmap.append("err.txt");*/
        String fileName=msgKillmap.toString();
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> stringKillmapList=new ArrayList<>();
        try {
            System.out.println("killmap.csv文件以行为单位读取文件内容，一次读一整行：reading......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("第" + line + "行：" +"（"+ tempString+"）");
                stringKillmapList.add(tempString);
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
        return stringKillmapList;
    }

}
