package handle;


import Utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MutantsHandler {
    /**
     * 行转换成mutants对象list
     * @param stringListMutants
     * @return
     */
    public static List<Mutants> fileToMutants(List<String> stringListMutants){
        List<Mutants> mutantsList=new ArrayList<>();
        //从 000000开始
        for (int index=0;index<stringListMutants.size();index++){
            String[] strings=stringListMutants.get(index).split(":");

            Mutants mutants=new Mutants();
            mutants.setID(Integer.parseInt(strings[0]));
            mutants.setElement1(strings[1]);
            mutants.setElement2(strings[2]);
            mutants.setElement3(strings[3]);
            //文件名特殊处理
            String[] filename=strings[4].split("@",-1);
            mutants.setFilename(filename[0]);
            mutants.setCodeline(Integer.valueOf(strings[5]));
            mutants.setWhichchange(strings[6]);
            mutants.setSuspicion(0.0);
            mutantsList.add(mutants);
        }
        return mutantsList;

    }


    /**
     * 匹配spectrarlist and mutantslist 得到变异的ID
     * @param spectraIDList
     * @param mutantsList
     * @return
     */
    public static List<Mutants> spectraToMutants(List<Spectra> spectraIDList,List<Mutants> mutantsList){
        List<Mutants> mutantsIDList=new ArrayList<>();
        for(int indexS=0;indexS<spectraIDList.size();indexS++){
            for(int indexM=0;indexM<mutantsList.size();indexM++){
                if (spectraIDList.get(indexS).getLine().equals(mutantsList.get(indexM).getCodeline()) &&spectraIDList.get(indexS).getFilename().equals(mutantsList.get(indexM).getFilename())){
                    mutantsIDList.add(mutantsList.get(indexM));
                }
            }
        }

        return mutantsIDList;
    }


    /**
     * 读取mutants文件
     * @param versionid
     * @param bugid
     * @return
     */
    public static List<String> readMutantsFileByLines(String versionid,int bugid){
        StringBuffer msgMutants=new StringBuffer("");
        msgMutants.append(Constants.DOWNLOADFILE);
        msgMutants.append(versionid+"/"+bugid+"/killmaps/"+versionid+"/"+bugid+"/");
        msgMutants.append("mutants.log");
        /*msgMutants.append(Constants.FLDPATH);
        msgMutants.append("killmap/out/");
        msgMutants.append(versionid+"-"+bugid+"-");
        msgMutants.append("mutants.log");*/
        String fileName=msgMutants.toString();
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> stringMutantsList=new ArrayList<>();
        try {
            System.out.println("Mutants.log文件以行为单位读取文件内容，一次读一整行：reading......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("第" + line + "行：" +"（"+ tempString+"）");
                stringMutantsList.add(tempString);
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
        return stringMutantsList;
    }

}
