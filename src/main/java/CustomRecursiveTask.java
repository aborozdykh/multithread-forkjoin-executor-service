import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author Andrii Borozdykh
 */
public class CustomRecursiveTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 20;
    private List<Long> nums;

    public CustomRecursiveTask(List<Long> nums) {
        this.nums = nums;
    }

    @Override
    protected Long compute() {
        int listSize = nums.size();
        if (listSize > THRESHOLD) {
            int middle = nums.size() / 2;
            var firstHalf = new CustomRecursiveTask(nums.subList(0, middle));
            var secondHalf = new CustomRecursiveTask(nums.subList(middle, listSize));
            return ForkJoinTask.invokeAll(List.of(firstHalf, secondHalf))
                    .stream()
                    .mapToLong(ForkJoinTask::join)
                    .sum();
        } else {
            return nums.stream()
                    .reduce(0L, Long::sum);
        }
    }
}
