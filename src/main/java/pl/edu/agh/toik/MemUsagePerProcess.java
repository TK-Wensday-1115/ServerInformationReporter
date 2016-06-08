package pl.edu.agh.toik;

/**
 * Created by Vanquis on 2016-06-08.
 */
public class MemUsagePerProcess{

    public MemUsagePerProcess(String process, String usage){
        this.process = process;
        this.usage = usage;
    }
    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    String process;

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    String usage;

}
