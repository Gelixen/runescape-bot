package lt.kslipaitis.osrs.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllUtils {

  private final RandomUtils randomUtils;
  private final SleepUtils sleepUtils;
  private final RobotUtils robotUtils;
  private final BankUtils bankUtils;
  private final InventoryUtils inventoryUtils;
  private final OptionsUtils optionsUtils;

}
