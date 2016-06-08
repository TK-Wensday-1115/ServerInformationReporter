package pl.edu.agh.toik;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Vanquis on 2016-06-07.
 */
public class Container {
    Queue<String> ramStats;
    Queue<String> cpuUsage;
    Queue<String> usedDiskSpacePercent;
    Queue<String> systemProcessesCount;
    Queue<String> upTime;
    Queue<String> currTcpConnectionCount;
    Queue<String> memoryUsagePerProcess;



    public Container(){
        ramStats = new LinkedList<String>();
        cpuUsage = new LinkedList<String>();
        usedDiskSpacePercent = new LinkedList<String>();
        systemProcessesCount = new LinkedList<String>();
        upTime = new LinkedList<String>();
        currTcpConnectionCount = new LinkedList<String>();
        memoryUsagePerProcess = new LinkedList<String>();
//        ramStats.add("10.0");
//        cpuUsage.add("23.5");
//        usedDiskSpacePercent.add("75.2");
    }

    public void addSingleRamStatus(String s){
        ramStats.add(s);
    }

    public String getSingleRamStatus(){
        if(!ramStats.isEmpty()) {
            return ramStats.remove();
        }else{
            return null;
        }
    }

    public void addSingleCpuStatus(String s){
        cpuUsage.add(s);
    }

    public String getSingleCpuStatus(){
        if(!cpuUsage.isEmpty()) {
            return cpuUsage.remove();
        }else{
            return null;
        }
    }

    public void addFreeSingleDiskSpaceStatus(String s){
        usedDiskSpacePercent.add(s);
    }

    public String getUsedSingleDiskSpaceStatus(){
        if(!usedDiskSpacePercent.isEmpty()) {
            return usedDiskSpacePercent.remove();
        }else{
            return null;
        }
    }

    public void addSystemProcessesCount(String s){
        systemProcessesCount.add(s);
    }

    public String getSystemProcessesCount(){
        if(!systemProcessesCount.isEmpty()) {
            return systemProcessesCount.remove();
        }else{
            return null;
        }
    }

    public void addUpTime(String s){
        upTime.add(s);
    }

    public String getUpTime(){
        if(!upTime.isEmpty()) {
            return upTime.remove();
        }else{
            return null;
        }
    }

    public void addCurrTcpConnectionCount(String s){
        currTcpConnectionCount.add(s);
    }

    public String getCurrTcpConnectionCount(){
        if(!currTcpConnectionCount.isEmpty()) {
            return currTcpConnectionCount.remove();
        }else{
            return null;
        }
    }


    public void addMemoryUsagePerProcess(String s){
        memoryUsagePerProcess.add(s);
    }

    public String getMemoryUsagePerProcess(){
        if(!memoryUsagePerProcess.isEmpty()) {
            return memoryUsagePerProcess.remove();
        }else{
            return null;
        }
    }


}
