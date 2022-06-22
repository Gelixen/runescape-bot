package lt.kslipaitis.osrs.bot.logging;

import lombok.Value;

@Value
public class PositionWithMoveAndCheckCoordinates {

    Boolean isTopPosition;
    MoveAndCheckCoordinate moveAndCheckCoordinate;
}
