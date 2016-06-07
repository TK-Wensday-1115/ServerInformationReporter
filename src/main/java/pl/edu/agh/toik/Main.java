package pl.edu.agh.toik;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import application.BoxPlotWidget;
import application.BoxType;
import application.Logger;
import com.github.TKWensday1115.Chart7ComplexBarChart.ComplexBarChart;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
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

    private void pretendWorking() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int u = 0, n = 0, g = 0;
                while (true) {
                    Random r = new Random();
                    u = r.nextInt(15) + 5;
                    n = r.nextInt(15) + 5;
                    g = r.nextInt(15) + 5;
                    try {
                        Thread.sleep(600);
                        freeRAM.setChartValue("Ram urwau", u);
                        freeRAM.setChartValue("Ram nie rwau", n);
                        freeRAM.setChartValue("Rama", g);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int u = 0, n = 0, g = 0;
                while (true) {
                    Random r = new Random();
                    u = r.nextInt(15) + 5;
                    n = r.nextInt(15) + 5;
                    g = r.nextInt(15) + 5;
                    try {
                        Thread.sleep(600);
                        freeDiscSpace.setChartValue("adin", u);
                        freeDiscSpace.setChartValue("dwa", n);
                        freeDiscSpace.setChartValue("tri", g);
                        freeDiscSpace.setChartValue("katiusza", g * 1.5);
                        freeDiscSpace.setChartValue("dupa", u * 2);
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
                        systemProcessesCount.setTemperature(r.nextInt(100));
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
                        Thread.sleep(600);
                        tcpVSudp.addNewEntry(tcp, r.nextDouble() * 50, new Date());
                        tcpVSudp.addNewEntry(udp, r.nextDouble() * 50, new Date());
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
//						memoryUsagePerProcess.addData("odbyt", "0", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("odbyt1", "1", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("odbyt2", "2", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("odbyt3", "3", i * 50 + r.nextInt(50));
//						memoryUsagePerProcess.addData("odbyt4", "4", i * 50 + r.nextInt(50));
//						i++;
//						System.out.println(i*50 + r.nextInt(50));
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                Random r = new Random();
                processorUsage.addBox(100);
                while (true) {
                    try {
                        processorUsage.addData(0, r.nextInt(100));
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
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
            memoryUsagePerProcess.addData("odbyt", "0", r.nextInt(50));
            memoryUsagePerProcess.addData("odbyt1", "1", r.nextInt(50));
            memoryUsagePerProcess.addData("odbyt2", "2", r.nextInt(50));
            memoryUsagePerProcess.addData("odbyt3", "3", r.nextInt(50));
            memoryUsagePerProcess.addData("odbyt4", "4", r.nextInt(50));
            // memoryUsagePerProcess.addData("okreznica", "1", r.nextInt(50));
            // memoryUsagePerProcess.addData("dwunastnica", "2", r.nextInt(50));
            // memoryUsagePerProcess.addData("okreznica", "3", r.nextInt(50));
            // memoryUsagePerProcess.addData("dwunastnica", "4", r.nextInt(50));

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
            driveUsage = HistoryChartFactory.createNew("Usage of hard drive (read/write)", "time", TimeUnit.Second,
                    "count", "");
            driveUsage.setPreferredSize(new Dimension(100, 200));
            frame.getContentPane().add(driveUsage, BorderLayout.SOUTH);

            // Ilość otwartych sesji http - logger + CSV
            httpSessions = new Logger();
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
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
