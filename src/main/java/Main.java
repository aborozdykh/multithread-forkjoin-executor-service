import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author Andrii Borozdykh
 */
public class Main {
    private static final int NUM_OF_THREADS = Runtime.getRuntime().availableProcessors();
    private static final int NUM_OF_ELEMENTS = 1_000_000;

    public static void main(String[] args) throws Exception {
        List<Long> nums = LongStream.range(0, NUM_OF_ELEMENTS)
                .boxed()
                .collect(Collectors.toList());

        var forkJoinPool = ForkJoinPool.commonPool();
        System.out.println(forkJoinPool.invoke(new CustomRecursiveTask(nums)));

        var customExecutorService = new CustomExecutorService(nums, NUM_OF_THREADS);
        System.out.println(customExecutorService.sum());
        customExecutorService.shutdown();
    }
}
