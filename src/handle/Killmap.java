package handle;

//killmap.csv文件也要放入对象里面
public class Killmap {
    //<test case>，
    private String testcase;
    // <mutant id>，ID important
    private int mutantid;
    // <timeout>，
    private int timeout;
    // <outcome>，
    private String outcome;
    // <runtime>，
    private int runtime;
    //  <output hash>，有这个吗？？？ 有
    private String hash;
    // <covered mutants>， ID important
    private int[] coveredmutants;
    // <stack trace>
    private String stacktrace;

    public String getTestcase() {
        return testcase;
    }

    public void setTestcase(String testcase) {
        this.testcase = testcase;
    }

    public int getMutantid() {
        return mutantid;
    }

    public void setMutantid(int mutantid) {
        this.mutantid = mutantid;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int[] getCoveredmutants() {
        return coveredmutants;
    }

    public void setCoveredmutants(int[]  coveredmutants) {
        this.coveredmutants = coveredmutants;
    }

    public String getStacktrace() {
        return stacktrace;
    }

    public void setStacktrace(String stacktrace) {
        this.stacktrace = stacktrace;
    }


    public void printmutants(int[] coveredmutants){
       // System.out.print(coveredmutants.length);
        for (int a:coveredmutants){
            System.out.print("["+a+"],");
        }
    }
}
