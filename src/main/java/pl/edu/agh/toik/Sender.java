package pl.edu.agh.toik;

import com.mashape.unirest.http.Unirest;
import org.apache.http.HttpHost;
import org.hyperic.sigar.*;
import pl.edu.agh.student.smialek.tk.communications.client.CommunicationsClient;
import pl.edu.agh.toik.data.Parameter;
import pl.edu.agh.toik.data.SystemMonitor;

import java.util.concurrent.*;

public class Sender {

    private static Sigar sigar = new Sigar();
    private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();


    public static void main(String[] args) {
        BlockingQueue<Parameter> sensorsDataQueue = new LinkedBlockingQueue<>();
        configureProxy(args);

        CommunicationsClient client = new CommunicationsClient("localhost", 8080, "SYSTEM_MONITORUJACY");
        SystemMonitor fetchAndSendData = new SystemMonitor(client, sensorsDataQueue);
        executor.scheduleAtFixedRate(fetchAndSendData, 2, 1, TimeUnit.SECONDS);
    }

    private static void configureProxy(String[] args) {
        if (args.length == 2 && "--proxy".equals(args[0])) {
            String[] parts = args[1].split(":");
            String host = parts[0];
            int port = Integer.valueOf(parts[1]);
            Unirest.setProxy(new HttpHost(host, port));
        }
    }
}
