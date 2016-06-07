package pl.edu.agh.toik.data;

import org.hyperic.sigar.*;
import pl.edu.agh.student.smialek.tk.communications.client.CommunicationsClient;

import java.util.concurrent.BlockingQueue;


public class SystemMonitor implements Runnable {

    private static Sigar sigar = new Sigar();
    private CommunicationsClient client;
    private BlockingQueue<Parameter> sensorsDataQueue;

    public SystemMonitor(CommunicationsClient client, BlockingQueue<Parameter> sensorsDataQueue) {
        this.client = client;
        this.sensorsDataQueue = sensorsDataQueue;
    }

    private void addParameters() throws InterruptedException {
        sensorsDataQueue.put(new Parameter("CPU_USAGE", getCpuUsage()));
        sensorsDataQueue.put(new Parameter("FILE_SYSTEM_USAGE", getFileSystemUsage()));
        sensorsDataQueue.put(new Parameter("MEM_USAGE", getMemUsage()));
        sensorsDataQueue.put(new Parameter("PROCESSES_COUNT", getSystemProcessesCount()));
        sensorsDataQueue.put(new Parameter("UP_TIME", getSystemUptime()));
        sensorsDataQueue.put(new Parameter("CURR_CONN_COUNT", getEstablishedConnCount()));
        sensorsDataQueue.put(new Parameter("PROC_CPU_USAGE", getProcessCpuUsage()));


    }

    @Override
    public void run() {
        try {
            addParameters();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
                Parameter parameter = sensorsDataQueue.take();
                client.sendUpdate(parameter.getName(), parameter.getValue());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public static String getCpuUsage(){
        CpuPerc cpuperc = null;
        try {
            cpuperc = sigar.getCpuPerc();
        } catch (SigarException se) {
            se.printStackTrace();
        }
        return String.valueOf(cpuperc.getCombined()*100);
    }

    public static String getFileSystemUsage(){
        FileSystemUsage filesystemusage = null;
        try {
            filesystemusage = sigar.getFileSystemUsage("C:");
        } catch (SigarException se) {
            se.printStackTrace();
        }
        return String.valueOf(filesystemusage.getUsePercent()*100);
    }

    public static String getMemUsage(){
        Mem mem = null;
        try {
            mem = sigar.getMem();
        } catch (SigarException se) {
            se.printStackTrace();
        }
        return String.valueOf(mem.getUsedPercent());
    }

    public static String getSystemProcessesCount(){
        long[] pids = null;
        try {
            pids = sigar.getProcList();
        } catch (SigarException se) {
            se.printStackTrace();
        }
        return String.valueOf(pids.length);
    }

    public static String getSystemUptime(){
        Uptime uptime = null;
        try {
            uptime = sigar.getUptime();
        } catch (SigarException se) {
            se.printStackTrace();
        }
        return String.valueOf(uptime.getUptime());
    }

    public static String getEstablishedConnCount(){
        Long establishedConnCount = null;
        try {
            establishedConnCount = sigar.getTcp().getCurrEstab();
        } catch (SigarException se) {
            se.printStackTrace();
        }
        return String.valueOf(establishedConnCount);
    }

    public static String getProcessCpuUsage() {
        long[] processes = null;
        try {
            processes = sigar.getProcList();
        } catch (Exception e) {
        }
        StringBuilder resultNames = new StringBuilder();
        StringBuilder resultValues = new StringBuilder();

        for (final long processId : processes) {
            try {
                ProcCpu procCpu = sigar.getProcCpu(processId);
                String name = ProcUtil.getDescription(sigar, processId);
                if (procCpu.getPercent() > 0.001f) {
                    String[] parsedName = name.split("\\\\");
                    String trueName = parsedName[parsedName.length-1];
                    resultNames.append(trueName + ";");
                    resultValues.append(String.valueOf(procCpu.getPercent()) + ";");
                }
            } catch (SigarException e) {
            }

        }
        return resultNames.toString()+":"+resultValues.toString();
    }


}
