package pl.edu.agh.toik;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import application.BoxPlotWidget;
import application.BoxType;
import javafx.scene.paint.Color;
import logger.component.Logger;
import com.github.TKWensday1115.Chart7ComplexBarChart.ComplexBarChart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jersey.repackaged.com.google.common.collect.Lists;
import pl.edu.agh.piechart.PieChartPanel;
import pl.edu.agh.student.mkasprz.tk.chart3.SimpleTable;
import pl.edu.agh.tk.ToCSVConverter;
import pl.edu.agh.toik.historychart.DataLineDoesNotExistException;
import pl.edu.agh.toik.historychart.HistoryChart;
import pl.edu.agh.toik.historychart.HistoryChartFactory;
import pl.edu.agh.toik.historychart.TimeUnit;
import sample.TermometerPanel;

public class Main extends Application {

    BoxPlotWidget processorUsage;
    PieChartPanel freeDiscSpace;
    PieChartPanel freeRAM;
    TermometerPanel systemProcessesCount;
    SimpleTable elapsedTime;
    HistoryChart tcpVSudp;
    ComplexBarChart memoryUsagePerProcess;
    TermometerPanel threadsWaiting;
    SimpleTable webParameters;
    HistoryChart driveUsage;
    TermometerPanel lastConnectionElapsedTime;
    Logger httpSessions;
    ToCSVConverter converter;
    private Container container;

    private void initCommunication(){
        container = new Container();
        Receiver receiver = new Receiver();
        receiver.startReceiving(container);
    }

    private void pretendWorking() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                double free = 0, used = 0;
                while (true) {
                    try {
                        String usedRam = container.getSingleRamStatus();
                        while(usedRam == null){
                            Thread.sleep(10000);
                            usedRam = container.getSingleRamStatus();
                        }
                        used = Double.parseDouble(usedRam);
                        free = 100 - used;
                        Thread.sleep(1000);
                        freeRAM.setChartValue("Wolny ram", free);
                        freeRAM.setChartValue("Zajety ram", used);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                double free = 0, used = 0;
                while (true) {
                    try {
                        String usedDisk = container.getUsedSingleDiskSpaceStatus();
                        while(usedDisk == null){
                            Thread.sleep(10000);
                            usedDisk = container.getUsedSingleDiskSpaceStatus();
                        }
                        used = Double.parseDouble(usedDisk);
                        free = 100 - used;
                        Thread.sleep(5000);
                        freeDiscSpace.setChartValue("Free disk space", free);
                        freeDiscSpace.setChartValue("Used disk space", used);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        String sysProcessesCount = container.getSystemProcessesCount();
                        while(sysProcessesCount == null){
                            Thread.sleep(10000);
                            sysProcessesCount = container.getSystemProcessesCount();
                        }
                        int sysProcessesCountAsInt = Integer.parseInt(sysProcessesCount);
                        Thread.sleep(600);
                        systemProcessesCount.setTemperature(sysProcessesCountAsInt/2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Random r = new Random();
                    try {
                        Thread.sleep(600);
                        threadsWaiting.setTemperature(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Random r = new Random();
                    try {
                        Thread.sleep(600);
                        lastConnectionElapsedTime.setTemperature(r.nextInt(100));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        int tcp = tcpVSudp.registerNewLine("TCP");
        int udp = tcpVSudp.registerNewLine("UDP");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Random r = new Random();
                    try {
                        String tcps = container.getCurrTcpConnectionCount();
                        while(tcps == null){
                            Thread.sleep(10000);
                            tcps = container.getCurrTcpConnectionCount();
                        }
                        Double tcpsAsDouble = Double.parseDouble(tcps);
                        Thread.sleep(600);
                        tcpVSudp.addNewEntry(tcp, tcpsAsDouble, new Date());
                        tcpVSudp.addNewEntry(udp, tcpsAsDouble + r.nextDouble() * 10, new Date());
                    } catch (InterruptedException | DataLineDoesNotExistException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				int i = 0;
////				while (true) {
//				for (int j = 0; j < 10; j++) {
//					Random r = new Random();
//					try {
//						Thread.sleep(600);
//						memoryUsagePerProcess.addData("sample", "0", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("sample1", "1", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("sample2", "2", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("sample3", "3", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("sample4", "4", i * 50 + r.nextInt(50));
//						i++;
//						System.out.println(i*50 + r.nextInt(50));
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();

        /*
        int file1 = driveUsage.registerNewLine("file1");
        int file2 = driveUsage.registerNewLine("file2");
        int file3 = driveUsage.registerNewLine("file3");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Random r = new Random();
                    try {
                        Thread.sleep(600);
                        driveUsage.addNewEntry(file1, r.nextDouble() * 50, new Date());
                        driveUsage.addNewEntry(file2, r.nextDouble() * 50, new Date());
                        driveUsage.addNewEntry(file3, r.nextDouble() * 50, new Date());
                    } catch (InterruptedException | DataLineDoesNotExistException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        */

        int cpu1 = driveUsage.registerNewLine("cpu1");
        new Thread(new Runnable() {
            @Override
            public void run() {
                processorUsage.addBox(100);
                while (true) {
                    try {
                        String cpuUsage = container.getSingleCpuStatus();
                        while(cpuUsage == null){
                            Thread.sleep(1000);
                            cpuUsage = container.getSingleCpuStatus();
                        }
                        Double cpuUsageAsDouble = Double.parseDouble(cpuUsage);
                        driveUsage.addNewEntry(cpu1, cpuUsageAsDouble, new Date());
                        processorUsage.addData(0, cpuUsageAsDouble);
                        Thread.sleep(100);
                    } catch (InterruptedException | DataLineDoesNotExistException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void start(Stage primaryStage) {
        // com.github.TKWensday1115.Chart7ComplexBarChart.Main main = new
        // com.github.TKWensday1115.Chart7ComplexBarChart.Main();
        // try {
        // main.start(new Stage());
        // } catch (Exception e1) {
        // e1.printStackTrace();
        // }
        // SwingNode swingNode = new SwingNode();

        initCommunication();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        BorderPane root = new BorderPane();
        JFrame frame = new JFrame("Main window");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));
//		frame.getContentPane().add(panel, BorderLayout.CENTER);
        try {
            // Użycie procesora - boxplot (ostatnie 100 odczytów)
            processorUsage = new BoxPlotWidget(10, 10, 200, 200, 2000, BoxType.FIFO, 100, 0);
            root.getChildren().add(processorUsage.getPane());

            httpSessions = new Logger();
            httpSessions.setHistorySize(5000);
            httpSessions.setColor(Color.BLACK);
            Scene loggerScene = new Scene(httpSessions);
            Stage loggerStage = new Stage();
            loggerStage.setScene(loggerScene);
            loggerStage.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        String mupp = container.getMemoryUsagePerProcess();
                        while (mupp == null || mupp.equals(":")) {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mupp = container.getMemoryUsagePerProcess();
                        }
                        httpSessions.append(new Date(), mupp);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        String time = container.getUpTime();
                        while (time == null) {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            time = container.getUpTime();
                        }
                        httpSessions.append(new Date(), time);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            // Dostępna przestrzeń na dysku - wykres kołowy (pliki)
            freeDiscSpace = new PieChartPanel("Free disc space", 200, 200);
            frame.getContentPane().add(freeDiscSpace, BorderLayout.WEST);

            // Wolny RAM - wykres kołowy
            freeRAM = new PieChartPanel("Free RAM", 200, 200);
            frame.getContentPane().add(freeRAM, BorderLayout.EAST);
//			panel.add(freeRAM);

            // Ilość procesów systemowych - wykres termometrowy, ew. bąbelkowy
            systemProcessesCount = new TermometerPanel("System processes", 0, 100);
            systemProcessesCount.setPreferredSize(new Dimension(250, 200));
            panel.add(systemProcessesCount);

            // Czas od startu systemu - label
            elapsedTime = new SimpleTable();
            // brak jakiejkolwiek implementacji poza szablonem

            // Liczba połączeń TCP - wykres liniowy
            // Liczba połączeń UDP - wykres liniowy
            tcpVSudp = HistoryChartFactory.createNew("TCP & UDP count", "time", TimeUnit.Second, "count", "count");
            tcpVSudp.setPreferredSize(new Dimension(100, 200));
            frame.getContentPane().add(tcpVSudp, BorderLayout.NORTH);

//            // Memory usage per process - słupkowy
//            memoryUsagePerProcess = new ComplexBarChart();
//            memoryUsagePerProcess.setHistoryLength(8);
//            memoryUsagePerProcess.setTitle("Memory Usage Per Process");
//            memoryUsagePerProcess.setLayoutX(200);
//            memoryUsagePerProcess.setPrefSize(200, 200);
//            Random r = new Random();
//            Scene s = new Scene(memoryUsagePerProcess, 200, 200);
//            primaryStage.setScene(s);
//            primaryStage.show();
//            root.getChildren().add(memoryUsagePerProcess);
//            memoryUsagePerProcess.addData("sample", "0", r.nextInt(50));
//            memoryUsagePerProcess.addData("sample1", "1", r.nextInt(50));
//            memoryUsagePerProcess.addData("sample2", "2", r.nextInt(50));
//            memoryUsagePerProcess.addData("sample3", "3", r.nextInt(50));
//            memoryUsagePerProcess.addData("sample4", "4", r.nextInt(50));
//            // memoryUsagePerProcess.addData("okreznica", "1", r.nextInt(50));
//            // memoryUsagePerProcess.addData("dwunastnica", "2", r.nextInt(50));
//            // memoryUsagePerProcess.addData("okreznica", "3", r.nextInt(50));
//            // memoryUsagePerProcess.addData("dwunastnica", "4", r.nextInt(50));

            // Długośc kolejki wątków czekających na użycie procesora - wykres w
            // formie termometru
            threadsWaiting = new TermometerPanel("Threads waiting", 0, 100);
            threadsWaiting.setPreferredSize(new Dimension(250, 200));
            panel.add(threadsWaiting);
            // threadsWaiting = new TermometerPanel("threadsQueueLength", 100,
            // 200);

            // Informacje sieciowe - adres IP, adres mac, brama domyślna -
            // labels
            webParameters = new SimpleTable();

            // Obecne zużycie dysku twardego (zapis, odczyt) - wykres liniowy
            driveUsage = HistoryChartFactory.createNew("Usage of processor ", "time", TimeUnit.Second,
                    "count", "");
            driveUsage.setPreferredSize(new Dimension(100, 200));
            frame.getContentPane().add(driveUsage, BorderLayout.SOUTH);

            // Ilość otwartych sesji http - logger + CSV

            converter = new ToCSVConverter();

            // Termometr pokazujący czas od ostatniej komunikacji. Zazwyczaj
            // stoi na zerze, w przypadku przerw w komunikacji idzie do góry
            lastConnectionElapsedTime = new TermometerPanel("Last Connection", 0, 100);
            lastConnectionElapsedTime.setPreferredSize(new Dimension(250, 200));
            panel.add(lastConnectionElapsedTime);
            // lastConnectionElapsedTime = new TermometerPanel("Time elapsed
            // from last connection", 100, 200);
            frame.setVisible(true);
            JFrame frm = new JFrame("Termometers");
            frm.setContentPane(panel);
            frm.setSize(600,600);
            frm.setVisible(true);

            // swingNode.set
            pretendWorking();

            Scene scene = new Scene(root, 800, 600);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();
            //showMemoryUsagePerProcess(primaryStage, root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMemoryUsagePerProcess(Stage primaryStage, BorderPane root){
        // Memory usage per process - słupkowy
        memoryUsagePerProcess = new ComplexBarChart();
        memoryUsagePerProcess.setHistoryLength(8);
        memoryUsagePerProcess.setTitle("Memory Usage Per Process");
        memoryUsagePerProcess.setLayoutX(200);
        memoryUsagePerProcess.setPrefSize(200, 200);
        Random r = new Random();
        Scene s = new Scene(memoryUsagePerProcess, 200, 200);
        primaryStage.setScene(s);
        primaryStage.show();
        root.getChildren().add(memoryUsagePerProcess);
        while(true) {
            try {
                String memUsagePerProcess = container.getMemoryUsagePerProcess();
                while(memUsagePerProcess == null || memUsagePerProcess.equals(":")){
                    Thread.sleep(5000);
                    memUsagePerProcess = container.getMemoryUsagePerProcess();
                }
                List<MemUsagePerProcess> memUsagePerProcessesList = parseMemUsagePerProcess(memUsagePerProcess);
                for(MemUsagePerProcess mupp : memUsagePerProcessesList) {
                    memoryUsagePerProcess.addData(mupp.getProcess(), "0", Double.parseDouble(mupp.getUsage()));
                }

                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // memoryUsagePerProcess.addData("okreznica", "1", r.nextInt(50));
            // memoryUsagePerProcess.addData("dwunastnica", "2", r.nextInt(50));
            // memoryUsagePerProcess.addData("okreznica", "3", r.nextInt(50));
            // memoryUsagePerProcess.addData("dwunastnica", "4", r.nextInt(50));
        }
    }

    public List<MemUsagePerProcess> parseMemUsagePerProcess(String string){
        List<MemUsagePerProcess> memUsagePerProcessList = Lists.newArrayList();
        System.out.println("#########################################      " + string + "    ###############################################");
        String[] parts = string.split(":");
        String names = parts[0];
        String values = parts[1];
        String [] nameTable = names.split(";");
        String [] valueTable = values.split(";");
        if(nameTable.length == valueTable.length){
            for(int i = 0; i < nameTable.length; i++){
                memUsagePerProcessList.add(new MemUsagePerProcess(nameTable[i], valueTable[i]));
            }
        }else{
            System.out.println("VERY BAD ERROR WHILE PARSING MEMORY USAGE PER PROCESS!");
        }
        return memUsagePerProcessList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
