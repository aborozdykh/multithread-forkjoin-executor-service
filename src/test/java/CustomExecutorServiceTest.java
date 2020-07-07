import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import jdk.jfr.StackTrace;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrii Borozdykh
 */
public class CustomExecutorServiceTest {
    @Test
    public void sumIsOk() throws InterruptedException {
        Long expected = 4950L;
        int numOfThreads = Runtime.getRuntime().availableProcessors();
        List<Long> nums = LongStream.range(0, 100)
                .boxed()
                .collect(Collectors.toList());
        var customExecutorService = new CustomExecutorService(nums, numOfThreads);
        Long actual = customExecutorService.sum();
        customExecutorService.shutdown();
        Assert.assertEquals(expected, actual);
    }
}
