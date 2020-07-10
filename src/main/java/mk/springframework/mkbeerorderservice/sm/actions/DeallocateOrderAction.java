package mk.springframework.mkbeerorderservice.sm.actions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.springframework.brewery.model.events.DeallocateOrderRequest;
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

import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class DeallocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        if(beerOrderOptional.isPresent()){
            BeerOrder beerOrder = beerOrderOptional.get();
            jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_QUEUE,
                    DeallocateOrderRequest.builder()
                            .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                            .build());
        }else{
            log.error("Beer Order Not Found!");
        }
    }
}
