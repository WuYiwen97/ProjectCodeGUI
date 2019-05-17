package handle;


//对于突变体 mutants.log文件 冒号隔开 需要放入一个对象里面？？？
public class Mutants {
    //突变体ID
    private int ID;
    private String element1;
    //原来什么类型的
    private String element2;
    //变到什么类型的
    private String element3;
    //需要名字
    private String filename;
    //行号
    private Integer codeline;
    //什么改变
    private String whichchange;
    //可疑度
    private Double suspicion;

    public Double getSuspicion() {
        return suspicion;
    }

    public void setSuspicion(Double suspicion) {
        this.suspicion = suspicion;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getElement1() {
        return element1;
    }

    public void setElement1(String element1) {
        this.element1 = element1;
    }

    public String getElement2() {
        return element2;
    }

    public void setElement2(String element2) {
        this.element2 = element2;
    }

    public String getElement3() {
        return element3;
    }

    public void setElement3(String element3) {
        this.element3 = element3;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getCodeline() {
        return codeline;
    }

    public void setCodeline(Integer codeline) {
        this.codeline = codeline;
    }

    public String getWhichchange() {
        return whichchange;
    }

    public void setWhichchange(String whichchange) {
        this.whichchange = whichchange;
    }


    @Override
    public String toString() {
        return "Mutants{" +
                "ID=" + ID +
                ", filename='" + filename + '\'' +
                ", codeline='" + codeline + '\'' +
                '}';
    }
}
