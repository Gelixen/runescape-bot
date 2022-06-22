package lt.kslipaitis.osrs.util;

public class SleepUtils {

    private final RandomUtils randomUtils;

    public SleepUtils(RandomUtils randomUtils) {
        this.randomUtils = randomUtils;
    }

    public void random(int seconds) {
        randomMillis(seconds * 1000);
    }

    public void randomMillis(int millis) {
        int sleepTime = randomUtils.getRandomBetween(millis - 74, millis + 113);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void randomTenth(int seconds) throws InterruptedException {
        int millis = seconds * 1134;
        final int variablePart = millis / 9;
        int sleepTime = randomUtils.getRandomBetween(millis, variablePart);
        Thread.sleep(sleepTime);
    }
}
