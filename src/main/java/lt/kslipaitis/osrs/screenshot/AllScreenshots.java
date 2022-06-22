package lt.kslipaitis.osrs.screenshot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllScreenshots {

  private final Screenshot screenshotWhole;
  private final Screenshot screenshotMiddle;
  private final Screenshot screenshotStatus;
  private final Screenshot screenshotEnemyHP;
  private final Screenshot screenshotCustom;
  private final Screenshot screenshotInventory;
  private final Screenshot screenshotMessages;
  private final Screenshot screenshotMap;

}
