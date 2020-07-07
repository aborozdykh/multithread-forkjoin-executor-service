import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Andrii Borozdykh
 */
public class CustomRecursiveTaskTest {
    @Test
    public void sumIsOk(){
        Long expected = 4950L;
        List<Long> nums = LongStream.range(0, 100)
                .boxed()
                .collect(Collectors.toList());
        var forkJoinPool = ForkJoinPool.commonPool();
        Long actual = forkJoinPool.invoke(new CustomRecursiveTask(nums));
        Assert.assertEquals(expected, actual);
    }
}
