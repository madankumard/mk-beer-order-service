package mk.springframework.mkbeerorderservice.services;

import mk.springframework.mkbeerorderservice.domain.BeerOrder;

public interface BeerOrderManager {
    BeerOrder newBeerOrder(BeerOrder beerOrder);
}
