package app.carrental.customer.application.command;

import app.carrental.customer.domain.event.CustomerRegisteredEvent;
import app.carrental.customer.domain.exception.CustomerAlreadyExistsException;
import app.carrental.customer.domain.model.*;
import app.carrental.customer.infrastructure.repository.FakeCustomerRepository;
import app.carrental.shared.infrastructure.FakeDomainEventPublisher;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegisterCustomerCommandTest {

    private final FakeCustomerRepository customerRepository = new FakeCustomerRepository();
    private final FakeDomainEventPublisher eventPublisher = new FakeDomainEventPublisher();
    private final RegisterCustomerCommandHandler commandHandler = new RegisterCustomerCommandHandler(
        customerRepository, eventPublisher
    );

    @ParameterizedTest
    @CsvSource({
        "5597c49c-8b7f-4e01-b017-1f549c71830d, John, Doe, 112233445566, john.doe@example.fr",
        "9c9ba1c8-b116-4556-bf7d-ded2e7df54f4, Jane, Clerk, 665544332211, jane.clerk@example.fr",
    })
    void should_register_new_customer_and_publish_domain_event(
        UUID customerId, String firstName, String lastName, String drivingLicense, String email
    ) {
        // Given
        var command = new RegisterCustomerCommand(customerId, firstName, lastName, drivingLicense, email);

        // When
        commandHandler.handle(command);

        // Then
        var customer = customerRepository.load(new CustomerId(customerId));
        assertThat(customer).isNotNull();
        assertThat(customer.id()).isEqualTo(new CustomerId(customerId));
        assertThat(customer.name()).isEqualTo(new Name(firstName, lastName));
        assertThat(customer.drivingLicense()).isEqualTo(new DrivingLicense(drivingLicense));
        assertThat(customer.email()).isEqualTo(new Email(email));
        assertThat(eventPublisher.publishedEvents()).containsExactly(new CustomerRegisteredEvent(
            customerId, firstName, lastName, drivingLicense, email
        ));
    }

    @ParameterizedTest
    @CsvSource({
        "5597c49c-8b7f-4e01-b017-1f549c71830d, John, Doe, 112233445566, john.doe@example.fr",
        "9c9ba1c8-b116-4556-bf7d-ded2e7df54f4, Jane, Clerk, 665544332211, jane.clerk@example.fr",
    })
    void should_throw_CustomerAlreadyExistsException(
        UUID customerId, String firstName, String lastName, String drivingLicense, String email
    ) {
        // Given
        var command = new RegisterCustomerCommand(customerId, firstName, lastName, drivingLicense, email);
        commandHandler.handle(command);

        // When, Then
        assertThrows(CustomerAlreadyExistsException.class, () -> commandHandler.handle(command));
    }
}
