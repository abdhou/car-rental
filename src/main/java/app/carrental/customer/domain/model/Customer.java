package app.carrental.customer.domain.model;

import app.carrental.customer.domain.event.CustomerRegisteredEvent;
import app.carrental.shared.domain.DomainEvent;
import app.carrental.shared.domain.AbstractAggregateRoot;

import java.util.List;
import java.util.UUID;

public class Customer extends AbstractAggregateRoot {
    private CustomerId id;
    private Name name;
    private DrivingLicense drivingLicense;
    private Email email;

    private Customer() {
    }

    public static Customer register(
        UUID customerId,
        String firstName,
        String lastName,
        String drivingLicense,
        String email) {

        var customer = new Customer();
        customer.apply(new CustomerRegisteredEvent(customerId, firstName, lastName, drivingLicense, email));
        return customer;
    }

    @Override
    protected void when(DomainEvent e) {
        switch (e) {
            case CustomerRegisteredEvent event -> {
                id = new CustomerId(event.customerId());
                name = new Name(event.firstName(), event.lastName());
                drivingLicense = new DrivingLicense(event.drivingLicense());
                email = new Email(event.email());
            }
            default -> throw new IllegalStateException("Unexpected event: " + e);
        }
    }

    @Override
    protected void ensureValidState() {
        boolean valid = id!=null && name !=null && drivingLicense !=null && email !=null;
        if (!valid) {
            throw new IllegalStateException("Invalid Customer state");
        }
    }

    public static Customer rehydrate(List<DomainEvent> events) {
        var customer = new Customer();
        for (var event : events) {
            customer.apply(event);
        }
        return customer;
    }

    public CustomerId id() {
        return this.id;
    }

    public Name name() {
        return name;
    }

    public DrivingLicense drivingLicense() {
        return drivingLicense;
    }

    public Email email() {
        return email;
    }
}
