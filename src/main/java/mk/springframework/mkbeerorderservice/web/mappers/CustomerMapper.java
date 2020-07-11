package mk.springframework.mkbeerorderservice.web.mappers;

import mk.springframework.brewery.model.CustomerDto;
import mk.springframework.mkbeerorderservice.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    CustomerDto customerToDto(Customer customer);
    Customer dtoToCustomer(CustomerDto customerDto);
}
