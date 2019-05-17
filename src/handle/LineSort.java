package handle;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 对SMFL he SBFL 结果 排序
 */
public class LineSort {
    //遍历list得到排名？？？
    //重写compareTO方法
    public static List<Suspicious> sortLine(List<Suspicious> resultList) {
        Collections.sort(resultList, new Comparator<Suspicious>() {
            @Override
            public int compare(Suspicious o1, Suspicious o2) {
                return o1.getResult()>o2.getResult()? -1:(o1.getResult().equals(o2.getResult())? 0:1);
            }
        });
        return resultList;
    }

    /**
     * 变异体可疑度排序
     * @param mutantsList
     * @return
     */
    public static List<Mutants> sortMutants(List<Mutants> mutantsList){
        Collections.sort(mutantsList, new Comparator<Mutants>() {
            @Override
            public int compare(Mutants o1, Mutants o2) {
                return o1.getSuspicion()>o2.getSuspicion()? -1:(o1.getSuspicion().equals(o2.getSuspicion())? 0:1);
            }
        });
        return mutantsList;

    }

    public  static List<Spectra> sortSpectra(List<Spectra> spectraList){
        Collections.sort(spectraList, new Comparator<Spectra>() {
            @Override
            public int compare(Spectra o1, Spectra o2) {
                return o1.getSuspicious()>o2.getSuspicious()? -1:(o1.getSuspicious().equals(o2.getSuspicious())? 0:1);
            }
        });
        return spectraList;
    }

}
