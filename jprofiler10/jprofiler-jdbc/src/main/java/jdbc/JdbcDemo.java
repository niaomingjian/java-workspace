package jdbc;

import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.NamingException;
import javax.swing.UIManager;

public class JdbcDemo {

    private static final int WORKER_THREAD_COUNT = 5;

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private List<Future<Integer>> results = new ArrayList<>();
    private List<JdbcTestWorker> workers = new ArrayList<>();

    private ServerWrapper serverWrapper = new ServerWrapper();

    private void start() throws Exception {
        serverWrapper.start();
        new ServerControllerFrame(this).setVisible(true);
    }

    public synchronized void shutdown() {
        stopActivity();
        serverWrapper.stop();
        System.exit(0);
    }

    public synchronized void startActivity(boolean leakConnection) {
        System.out.println("Starting database activity ...");
        try {
            for (int i = 0; i < WORKER_THREAD_COUNT; i++) {
                System.out.println("Starting worker thread " + (i + 1) + " of " + WORKER_THREAD_COUNT);
                DataSource dataSource = serverWrapper.getDataSource();
                JdbcTestWorker worker = new JdbcTestWorker(dataSource, leakConnection && i == 1);
                Future<Integer> result = executorService.submit(worker);
                results.add(result);
                workers.add(worker);
            }
            System.out.println("All worker threads were started.");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public synchronized void stopActivity() {
        if (workers.isEmpty()) {
            return;
        }
        System.out.println("Stopping database activity ...");
        for (JdbcTestWorker worker : workers) {
            worker.terminate();
        }
        for (int i = 0; i < results.size(); i++) {
            Future<Integer> result = results.get(i);
            try {
                System.out.println("Worker thread " + (i + 1) + " performed "  + result.get() + " iterations.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All worker threads were stopped");
        workers.clear();
        results.clear();
        System.gc();
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new JdbcDemo().start();
    }

}
