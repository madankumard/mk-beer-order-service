package mk.springframework.mkbeerorderservice.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.springframework.brewery.model.events.ValidateOrderRequest;
import mk.springframework.mkbeerorderservice.config.JmsConfig;
import mk.springframework.mkbeerorderservice.domain.BeerOrder;
import mk.springframework.mkbeerorderservice.domain.BeerOrderEventEnum;
import mk.springframework.mkbeerorderservice.domain.BeerOrderStatusEnum;
import mk.springframework.mkbeerorderservice.repositories.BeerOrderRepository;
import mk.springframework.mkbeerorderservice.services.BeerOrderManagerImpl;
import mk.springframework.mkbeerorderservice.web.mappers.BeerOrderMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> stateContext) {
        String beerOrderId = (String) stateContext.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateOrderRequest.builder()
                .beerOrder(beerOrderMapper.beerOrderToDto(beerOrder))
                .build());

        log.debug("Sent Validation request to queue for order id " + beerOrderId);
    }
}
