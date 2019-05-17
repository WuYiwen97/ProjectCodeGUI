package handle;

import Utils.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyiwen
 * @date  2019.4.19
 */

//处理 读取  矩阵
public class MatrixHandler {
    /**
     * 按行读取Matrix文件按行处理数据
     * @param fileName
     * @return List
     */
    public static List<String []> readMatrixFileByLines(String projectID ,int bugID) {
        StringBuffer msgMatrix=new StringBuffer("");
        msgMatrix.append(Constants.DOWNLOADFILE);
        msgMatrix.append(projectID+"/"+bugID+"/gzoltars/"+projectID+"/"+bugID+"/");
        msgMatrix.append("matrix");

        /*magFile.append(Constants.FLDPATH);
        magFile.append("gzoltar/out/gzoltars/");
        magFile.append(projectID+"/"+bugID+"/");
        magFile.append("matrix");*/
        String fileName=msgMatrix.toString();
        File file = new File(fileName);
        BufferedReader reader = null;
        //List<String> stringlastCharList=new ArrayList<>();
        List<String []> stringList=new ArrayList<>();
        try {
            System.out.println("Matrix文件 以行为单位读取文件内容，一次读一整行：Reading....");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                //System.out.println("第" + line + "行：" +"（"+ tempString.substring(tempString.length() - 1 )+"）");
                //String aa=tempString.substring(tempString.length()-1);
                //stringlastCharList.add(aa);

                //按照空格分割
                String [] arrStrings=tempString.split(" ");
                stringList.add(arrStrings);
                line++;
            }
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
        return stringList;
    }

    /**
     * 文件按行读取 要转换成矩阵
     * @param stringList
     * @return Srting[][]
     */
    public static String[][] changeToMatrix(List<String[]> stringList) {
        //变成矩阵
        int MatrixLine = stringList.size();
        int MatrixRow = stringList.get(1).length;

        String[][] Matrix = new String[MatrixLine][MatrixRow];
        for (int line = 0; line < MatrixLine; line++) {
            for (int row = 0; row < MatrixRow; row++) {
                Matrix[line][row] = stringList.get(line)[row];
            }
        }

    return Matrix;
    }

    /**
     * 输出矩阵
     * @param arr
     */

    public static void printMatrix(String [][] arr){
        //输出二维数组
            for (int line=0;line<arr.length;line++){
                System.out.print("[");
                for(int row=0;row<arr[line].length;row++){
                    System.out.print(arr[line][row]+",");
                }
                System.out.println("]");
            }
        }

    public static void printm(String [][] arr){
        for (int line=0;line<arr.length;line++){

            System.out.print(arr[line][2432]+"\t");

        }
    }


}