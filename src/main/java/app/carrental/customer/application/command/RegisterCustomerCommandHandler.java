package app.carrental.customer.application.command;

import app.carrental.customer.domain.exception.CustomerAlreadyExistsException;
import app.carrental.customer.domain.model.*;
import app.carrental.customer.domain.repository.CustomerRepository;
import app.carrental.shared.application.CommandHandler;
import app.carrental.shared.application.DomainEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerCommandHandler implements CommandHandler<RegisterCustomerCommand> {

    private final CustomerRepository customerRepository;
    private final DomainEventPublisher eventPublisher;

    public RegisterCustomerCommandHandler(CustomerRepository repository, DomainEventPublisher eventPublisher) {
        this.customerRepository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(RegisterCustomerCommand command) {
        var customerId = new CustomerId(command.customerId());

        if (customerRepository.exists(customerId)) {
            throw new CustomerAlreadyExistsException();
        }

        var customer = Customer.register(
            command.customerId(),
            command.firstName(),
            command.lastName(),
            command.drivingLicense(),
            command.email()
        );

        var events = customer.uncommittedEvents();
        customerRepository.save(customer);
        eventPublisher.publish(events);
    }
}
