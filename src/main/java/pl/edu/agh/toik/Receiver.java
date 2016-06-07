package pl.edu.agh.toik;

import pl.edu.agh.student.smialek.tk.communications.server.CommunicationsServer;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReading;
import pl.edu.agh.student.smialek.tk.communications.server.SensorReadingCallback;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Receiver {

    public Receiver(){}

    public void startReceiving(Container container){
        final DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT)
                .withZone(ZoneId.systemDefault());
        CommunicationsServer.registerCallback(new SensorReadingCallback() {
            @Override
            public void receive(SensorReading reading) {

                if(reading.getColor().equals("CPU_USAGE")){
                    container.addSingleCpuStatus(reading.getValue());
                }else if(reading.getColor().equals("FILE_SYSTEM_USAGE")){
                    container.addFreeSingleDiskSpaceStatus(reading.getValue());
                }else if(reading.getColor().equals("PROCESSES_COUNT")){
                    container.addSystemProcessesCount(reading.getValue());
                }else if(reading.getColor().equals("UP_TIME")){
                    container.addUpTime(reading.getValue());
                }else if(reading.getColor().equals("CURR_CONN_COUNT")){
                    container.addCurrTcpConnectionCount(reading.getValue());
                }else if(reading.getColor().equals("PROC_CPU_USAGE")){
                    //proc cpu usage per process
                }else if(reading.getColor().equals("MEM_USAGE")){
                    container.addSingleRamStatus(reading.getValue());
                }


//                System.out.format("[%s] %s: <%s> %s\n",
//                        dateFormatter.format(reading.getTimestamp().toInstant()), reading.getSensorName(),
//                        reading.getColor(), reading.getValue()
//                );
            }
        });
        CommunicationsServer.start(8080);
        System.out.println("Receiver started");
    }
}