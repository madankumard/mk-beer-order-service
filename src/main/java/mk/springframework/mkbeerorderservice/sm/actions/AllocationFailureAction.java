package mk.springframework.mkbeerorderservice.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.springframework.brewery.model.events.AllocationFailureEvent;
import mk.springframework.mkbeerorderservice.config.JmsConfig;
import mk.springframework.mkbeerorderservice.domain.BeerOrderEventEnum;
import mk.springframework.mkbeerorderservice.domain.BeerOrderStatusEnum;
import mk.springframework.mkbeerorderservice.services.BeerOrderManagerImpl;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class AllocationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_FAILURE_QUEUE, AllocationFailureEvent.builder()
                .orderId(UUID.fromString(beerOrderId))
                .build());

        log.debug("Sent Allocation Failure Message to queue for order id " + beerOrderId);
    }
}
