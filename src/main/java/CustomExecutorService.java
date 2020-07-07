import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Andrii Borozdykh
 */
public class CustomExecutorService {
    private static final int DIVISOR = 10;
    private final ExecutorService executorService;
    private final List<Long> nums;

    public CustomExecutorService(List<Long> nums, int numOfThreads) {
        executorService = Executors.newFixedThreadPool(numOfThreads);
        this.nums = nums;
    }

    public Long sum() throws InterruptedException {
        List<Future<Long>> futures = executorService.invokeAll(getCallableList());
        return futures.stream()
                .mapToLong(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        return 0;
                    }
                }).sum();
    }

    public void shutdown() {
        executorService.shutdown();
    }

    private List<Callable<Long>> getCallableList() {
        List<Callable<Long>> callableList = new ArrayList<>();
        int n = nums.size() / DIVISOR;
        for (int i = 0; i < DIVISOR; i++) {
            Callable<Long> callable = new CallableSum(nums.subList(i * n, (n * (i + 1))));
            callableList.add(callable);
        }
        return callableList;
    }
}
