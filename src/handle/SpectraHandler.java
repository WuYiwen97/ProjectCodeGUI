package handle;

import Utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理spectra
 */
public class SpectraHandler {

    /**
     *  读取文件 必须把每一行转换成对象 继续处理
     * @param stringListSpectra
     * @return
     */
    public static List<Spectra> fileInSpectra(List<String> stringListSpectra){
        List<Spectra> spectraList=new ArrayList<>();
        //从 000000开始
        for (int index=0;index<stringListSpectra.size();index++){
            String[] strings=stringListSpectra.get(index).split("#");
            Spectra spectra=new Spectra();
            spectra.setId(index);
            spectra.setFilename(strings[0]);
            spectra.setLine(Integer.parseInt(strings[1]));
            spectra.setSuspicious(0.0);
            spectraList.add(spectra);
        }
        return spectraList;


    }



    public static List<String> readSpectraFileByLines(String projectID ,int bugID){
        StringBuffer msgSpectra=new StringBuffer("");
        msgSpectra.append(Constants.DOWNLOADFILE);
        msgSpectra.append(projectID+"/"+bugID+"/gzoltars/"+projectID+"/"+bugID+"/");
        msgSpectra.append("spectra");
        /*msgSpectra.append(Constants.FLDPATH);
        msgSpectra.append("gzoltar/out/gzoltars/");
        msgSpectra.append(projectID+"/"+bugID+"/");
        msgSpectra.append("spectra");*/
        String fileName=msgSpectra.toString();
        File file = new File(fileName);
        BufferedReader reader = null;
        List<String> stringSpectraList=new ArrayList<>();
        try {
            System.out.println("Spectra文件以行为单位读取文件内容，一次读一整行：reading......");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("第" + line + "行：" +"（"+ tempString+"）");
                stringSpectraList.add(tempString);
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
        return stringSpectraList;
    }


    /**
     *  得到想要变异的行 必须找到可疑度高的行 是哪一行？？？
     * @param spectraList
     * @param suspiciousList
     * @param  line ------行数
     * @return
     */
    public static List<Spectra> ochiaiToSpectra(List<Spectra> spectraList, List<Suspicious> suspiciousList,int line){
        List<Spectra> spectraList1=new ArrayList<>();
        for(int index=0;index<line;index++){
            int suspLine=suspiciousList.get(index).getId();
            for(int indexTwo=0;indexTwo<spectraList.size();indexTwo++){
                // 行一样
                if (suspLine==spectraList.get(indexTwo).getId()){
                    spectraList.get(indexTwo).setSuspicious(suspiciousList.get(index).getResult());
                    spectraList1.add(spectraList.get(indexTwo));
                    }

                }

            }

        return spectraList1;

    }


    public  static List<Spectra> getSpectraFinall(List<Mutants> mutantsList,List<Spectra> spectraList ){

        //先置空
        for (Spectra spectra:spectraList){
            spectra.setSuspicious(0.0);
        }

        for (int index=0;index<spectraList.size();index++){
            for (int index2=0;index2<mutantsList.size();index2++){
                Spectra spectraA=spectraList.get(index);
                Mutants mutantsA=mutantsList.get(index2);
                if (spectraA.getFilename().equals(mutantsA.getFilename()) && spectraA.getLine().equals(mutantsA.getCodeline())){

                    if (spectraA.getSuspicious()<mutantsA.getSuspicion()){
                        spectraA.setSuspicious(mutantsA.getSuspicion());
                    }
                }

            }
        }

        return spectraList;
    }

    public static List<Spectra> getLine(List<Spectra> spectraOnlyMetallMutantsFinall, int line) {
        List<Spectra> spectraList=new ArrayList<>();
        for (int i=0;i<line;i++){
            spectraList.add(spectraOnlyMetallMutantsFinall.get(i));
        }
        return  spectraList;
    }
}
