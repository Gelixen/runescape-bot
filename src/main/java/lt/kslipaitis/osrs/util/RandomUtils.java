package lt.kslipaitis.osrs.util;

import java.util.Random;

public class RandomUtils {

    private final Random random;

    public RandomUtils(Random random) {
        this.random = random;
    }

    public RandomCoordinate getRandomCoordinateWithinRadius(int x, int y, int radius) {
        return getRandomCoordinateWithinDifferentRadiuses(x, y, radius, radius);
    }

    public RandomCoordinate getRandomCoordinateWithinDifferentRadiuses(int x, int y, int xRadius, int yRadius) {
        final int xMin = x - xRadius;
        final int xMax = x + xRadius;

        final int yMin = y - yRadius;
        final int yMax = y + yRadius;

        return getRandomCoordinates(xMin, yMin, xMax, yMax);
    }

    public RandomCoordinate getRandomCoordinates(int xMin, int yMin, int xMax, int yMax) {
        int randomX = getRandomBetween(xMin, xMax);
        int randomY = getRandomBetween(yMin, yMax);

        return new RandomCoordinate(randomX, randomY);
    }

    public int getRandomBetween(int min, int max) {
        final int difference = max - min;
        return random.nextInt(difference) + min;
    }
}
