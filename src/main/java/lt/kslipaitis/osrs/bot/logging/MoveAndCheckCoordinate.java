package lt.kslipaitis.osrs.bot.logging;

import lombok.Value;
import lt.kslipaitis.osrs.util.RandomCoordinate;

@Value
public class MoveAndCheckCoordinate {

    RandomCoordinate moveCoordinate;
    RandomCoordinate checkCoordinate;
}
