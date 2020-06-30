package mk.springframework.mkbeerorderservice.web.mappers;

import mk.springframework.mkbeerorderservice.domain.BeerOrderLine;
import mk.springframework.mkbeerorderservice.web.model.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);
    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
