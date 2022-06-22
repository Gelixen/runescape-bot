package lt.kslipaitis.osrs.processor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AllProcessors {

    private final StatusProcessor statusProcessor;
    private final MessagesProcessor messagesProcessor;
    private final OptionsProcessor optionsProcessor;
    private final BankProcessor bankProcessor;
    private final RobotProcessor robotProcessor;
    private final InventoryProcessor inventoryProcessor;

}
