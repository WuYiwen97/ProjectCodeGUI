package handle;

import java.util.ArrayList;
import java.util.List;

public class SuspiciousHandler {
    public static List<Suspicious> listToObject(List<Double> doubleList){
        List<Suspicious> suspiciousList=new ArrayList<>();
        for (int index=0;index<doubleList.size();index++){
            Suspicious suspicious=new Suspicious();
            suspicious.setId(index);
            suspicious.setResult(doubleList.get(index));
            suspiciousList.add(suspicious);
        }
        return  suspiciousList;
    }
}
