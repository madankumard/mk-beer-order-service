package mk.springframework.mkbeerorderservice.web.mappers;

import mk.springframework.mkbeerorderservice.domain.BeerOrder;
import mk.springframework.mkbeerorderservice.web.model.BeerOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {

    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);
    BeerOrder dtoToBeerOrder(BeerOrderDto dto);
}
