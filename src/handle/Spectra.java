package handle;


//读取spectra放入对象
public class Spectra {
    private int id;
    private String filename;
    private Integer line;
    private Double suspicious;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getLine() {
        return line;
    }

    public void setLine(Integer line) {
        this.line = line;
    }

    public Double getSuspicious() {
        return suspicious;
    }

    public void setSuspicious(Double suspicious) {
        this.suspicious = suspicious;
    }

    @Override
    public String toString() {
        return "Spectra{" +
                "id=" + id +
                ", filename='" + filename + '\'' +
                ", line=" + line +
                ", suspicious=" + suspicious +
                '}';
    }
}
