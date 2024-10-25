package com.gussoft.datareactive.core.business.impl;

import static com.gussoft.datareactive.core.utils.Constrains.NOT_FOUND;

import com.gussoft.datareactive.core.business.CustomerService;
import com.gussoft.datareactive.core.exception.NotFoundException;
import com.gussoft.datareactive.core.mapper.AddressMapper;
import com.gussoft.datareactive.core.mapper.CustomerMapper;
import com.gussoft.datareactive.core.models.Customer;
import com.gussoft.datareactive.core.repository.AddressRepository;
import com.gussoft.datareactive.core.repository.CustomerRepository;
import com.gussoft.datareactive.integration.transfer.record.CustomerRecord;
import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@Log4j2
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository repository;
  private final AddressRepository addressRepo;
  private CustomerMapper mapper;
  private AddressMapper addMapper;

  @Override
  public Flux<CustomerRecord> getAll() {
    return repository.findAll()
      .map(mapper::customerRecordToCustomer)
      .sort(Comparator.comparing(CustomerRecord::id));
  }

  @Override
  public Mono<Page<CustomerRecord>> getAllToPage(Pageable pageable, String name) {
    return null;
  }

  @Override
  public Flux<CustomerRecord> getByName(String name) {
    return repository.findByNames(name)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .map(mapper::customerRecordToCustomer);
  }

  @Override
  public Mono<CustomerRecord> getById(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(customer -> {
          if (customer.getAddressId() == null) {
            return Mono.just(mapper.customerRecordToCustomer(customer));
          }
          return addressRepo.findById(Long.valueOf(customer.getAddressId()))
            .map(address -> {
              customer.setAddress(address);
              return mapper.customerRecordToCustomer(customer);
            });
        });
  }

  @Override
  public Mono<CustomerRecord> create(CustomerRecord request) {
    if (request.address() == null) {
      return repository.save(mapper.toCustomerRecord(request))
        .map(mapper::customerRecordToCustomer);
    }
    return addressRepo.save(addMapper.toAddressRecord(request.address()))
      .flatMap(address -> {
        Customer customer = mapper.toCustomerRecord(request);
        customer.setAddress(address);
        customer.setAddressId(address.getId());
        return repository.save(customer)
          .map(mapper::customerRecordToCustomer);
      });
  }

  @Override
  public Mono<CustomerRecord> update(CustomerRecord request, Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(customer -> {
        if (request.address() == null) {
          return repository.save(mapper.toCustomerRecord(request))
            .map(mapper::customerRecordToCustomer);
        }
        return addressRepo.findById(Long.valueOf(request.addressId()))
          .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
          .flatMap(address -> {
            address = addMapper.toAddressRecord(request.address());
            return addressRepo.save(address)
              .flatMap(saved -> {
                Customer data = mapper.toCustomerRecord(request);
                data.setAddressId(saved.getId());
                return repository.save(data)
                  .map(c -> mapper.customerRecordToCustomer(c))
                  .doOnError(err -> log.error("Error " + err.getMessage()));
              });
          });
      });
  }

  @Override
  public Mono<Void> delete(Long id) {
    return repository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException(NOT_FOUND)))
      .flatMap(customer -> {
        if (customer.getAddressId() == null) {
          return repository.deleteById(customer.getId());
        }
        return repository.deleteById(customer.getId())
          .then(addressRepo.deleteById(Long.valueOf(customer.getAddressId())));
        });
  }
}
