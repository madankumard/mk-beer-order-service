package mk.springframework.brewery.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.springframework.brewery.model.BeerOrderDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeallocateOrderRequest {
    private BeerOrderDto beerOrderDto;
}