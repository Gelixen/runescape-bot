package lt.kslipaitis.osrs.util;

public class BankUtils {

    private static final int FIRST_SLOT_X = 829;
    private static final int FIRST_SLOT_Y = 164;
    private static final int ITEM_LENGTH = 72;
    private static final int ITEM_HEIGHT = 54;
    private static final int RADIUS = 19;

    private static final int DEPOSIT_INVENTORY_X = 1361;
    private static final int DEPOSIT_INVENTORY_Y = 1114;

    private static final int CLOSE_WINDOW_X = 1424;
    private static final int CLOSE_WINDOW_Y = 51;
    private static final int CLOSE_WINDOW_RADIUS = 10;

    private final RandomUtils random;

    public BankUtils(RandomUtils random) {
        this.random = random;
    }

    public RandomCoordinate getFirstSlotCoords() {
        return getNthSlotCoords(0, 0);
    }

    public RandomCoordinate getNthSlotCoords(int slotRowIndex, int slotColumnIndex) {
        int x = FIRST_SLOT_X + slotColumnIndex * ITEM_LENGTH;
        int y = FIRST_SLOT_Y + slotRowIndex * ITEM_HEIGHT;
        return random.getRandomCoordinateWithinRadius(x, y, RADIUS);
    }

    public RandomCoordinate getSecondSlotCoords() {
        return getNthSlotCoords(0, 1);
    }

    public RandomCoordinate getDepositInventoryCoordinate() {
        return random.getRandomCoordinateWithinRadius(DEPOSIT_INVENTORY_X, DEPOSIT_INVENTORY_Y, RADIUS);
    }

    public RandomCoordinate getCloseWindowCoordinate() {
        return random.getRandomCoordinateWithinRadius(CLOSE_WINDOW_X, CLOSE_WINDOW_Y, CLOSE_WINDOW_RADIUS);
    }
}
