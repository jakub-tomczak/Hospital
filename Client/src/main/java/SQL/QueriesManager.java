package SQL;

import java.util.concurrent.*;

public class QueriesManager {
    int corePoolSize = 5;
    int maxPoolSize = 10;   //max number of threads
    long keepAliveTime = 10; //task timeout 10 sec
    ExecutorService executor = null;

    public QueriesManager()
    {
        executor =
                new ThreadPoolExecutor(
                        corePoolSize,
                        maxPoolSize,
                        keepAliveTime,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>()
                );
    }

    public void addDoctor(String firstName, String lastName, Command onQuryFinished)
    {
        executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                onQuryFinished.execute();
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });

        System.out.println("Submitted.");

    }

}
