package SBFL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dstar {

    //计算复杂度Dstar
    public static List<Double> calculateDstar(String [][] arr){
        // 先得到有多少代码行
        int codeLine=arr[0].length-1;
        // 假如覆盖 结果在二维数组里面
        int[][] NpsOrNfs =new int[codeLine][2];
        //假如不覆盖 结果在另一个二维数组里面
        int[][] NupsOrNufs=new int[codeLine][2];
        // 计算结果在list里面
        List<Double> resultList=new ArrayList<>();

        Map<Integer,Double> resultMap=new HashMap();
        //长度 length 行数 line
        //列数 row
        /*System.out.println(arr.length);
        for (int line=0;line<arr.length;line++){
            for(int row=0;row<arr[line].length;row++){
                System.out.print(arr[line][row]+"["+line+","+row+"]");
            }
            System.out.println("");
        }*/
        for (int line=0;line<arr.length;line++){
            int Nps=0;
            int Nfs=0;
            // 最后一个为+  成功Nps  1
            if (arr[line][arr[line].length-1].equals("+")){
                System.out.println("line"+line +"is "+arr[line][arr[line].length-1]);
                // 循环
                for(int row=0;row<arr[line].length;row++){
                    if (arr[line][row].equals("1")) {
                        NpsOrNfs[row][1]++;
                    }
                }
                for(int row=0;row<arr[line].length;row++){
                    if (arr[line][row].equals("0")) {
                        NupsOrNufs[row][1]++;
                    }
                }
            }
            // 最后为- 失败 覆盖失败+1Nfs 0
            else if (arr[line][arr[line].length-1].equals("-")){
                System.out.println("line"+line +"is "+arr[line][arr[line].length-1]);
                // 循环
                for(int row=0;row<arr[line].length;row++){
                    if (arr[line][row].equals("1")) {
                        NpsOrNfs[row][0]++;
                    }
                }
                for(int row=0;row<arr[line].length;row++){
                    if (arr[line][row].equals("0")) {
                        NupsOrNufs[row][0]++;
                    }
                }
            }
        }
        // 计算在这里
        for(int index=0;index<NpsOrNfs.length;index++){
            Double dstar = ( (double) NpsOrNfs[index][0] *(double) NpsOrNfs[index][0] ) / ((double)NpsOrNfs[index][1]+(double)NupsOrNufs[index][0]);
            if (dstar.isNaN() || dstar.isInfinite()) {
                dstar = 0.0;
            }
            resultList.add(dstar);
        }
        // 输入结果
        for (int index =0;index<resultList.size();index++){
            System.out.println("line "+(index+1)+" dstar is "+resultList.get(index)+"\t");
        }
        return  resultList;
    }

}
