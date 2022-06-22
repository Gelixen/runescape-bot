package lt.kslipaitis.osrs.util;

import lt.kslipaitis.osrs.Coordinate;

public class InventoryUtils {

    public static final int ITEM_LENGTH = 63;
    public static final int ITEM_HEIGHT = 54;
    public static final int MAX_ROW_INDEX = 6;
    public static final int MAX_COLUMN_INDEX = 3;
    private static final int FIRST_SLOT_X = 2251;
    private static final int FIRST_SLOT_Y = 981;
    private static final int RELATIVE_FIRST_SLOT_X = 40;
    private static final int RELATIVE_FIRST_SLOT_Y = 30;
    private static final int PROXIMITY = 19;
    private final RandomUtils random;

    public InventoryUtils(RandomUtils random) {
        this.random = random;
    }

    public RandomCoordinate getFirstSlotCoordinate() {
        return getNthSlotCoords(0, 0);
    }

    public RandomCoordinate getNthSlotCoords(int slotRowIndex, int slotColumnIndex) {
        int x = FIRST_SLOT_X + slotColumnIndex * ITEM_LENGTH;
        int y = FIRST_SLOT_Y + slotRowIndex * ITEM_HEIGHT;
        return random.getRandomCoordinateWithinRadius(x, y, PROXIMITY);
    }

    public RandomCoordinate getLastSlotCoordinate() {
        return getNthSlotCoords(MAX_ROW_INDEX, MAX_COLUMN_INDEX);
    }

    public Coordinate getSecondToLastSlotImageCoordinate() {
        Coordinate coordinate = getRelativeNthSlotCoordinate(MAX_ROW_INDEX, MAX_COLUMN_INDEX - 1);

        final int imageCornerXCoordinate = coordinate.getX() - ITEM_LENGTH / 2;
        final int imageCornerYCoordinate = coordinate.getY() - ITEM_HEIGHT / 2;

        return new Coordinate(imageCornerXCoordinate, imageCornerYCoordinate);
    }

    public Coordinate getRelativeNthSlotCoordinate(int slotRowIndex, int slotColumnIndex) {
        int x = RELATIVE_FIRST_SLOT_X + slotColumnIndex * ITEM_LENGTH;
        int y = RELATIVE_FIRST_SLOT_Y + slotRowIndex * ITEM_HEIGHT;
        return new Coordinate(x, y);
    }

    public Coordinate getThirdToLastSlotImageCoordinate() {
        Coordinate coordinate = getRelativeNthSlotCoordinate(6, 1);

        final int imageCornerXCoordinate = coordinate.getX() - ITEM_LENGTH / 2;
        final int imageCornerYCoordinate = coordinate.getY() - ITEM_HEIGHT / 2;

        return new Coordinate(imageCornerXCoordinate, imageCornerYCoordinate);
    }

    public Coordinate getLastSlotImageCoordinate() {
        Coordinate coordinate = getRelativeNthSlotCoordinate(MAX_ROW_INDEX, MAX_COLUMN_INDEX);

        final int imageCornerXCoordinate = coordinate.getX() - ITEM_LENGTH / 2;
        final int imageCornerYCoordinate = coordinate.getY() - ITEM_HEIGHT / 2;

        return new Coordinate(imageCornerXCoordinate, imageCornerYCoordinate);
    }
}
