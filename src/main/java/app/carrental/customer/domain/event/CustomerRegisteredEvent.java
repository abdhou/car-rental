package app.carrental.customer.domain.event;

import app.carrental.shared.domain.DomainEvent;

import java.util.UUID;

public record CustomerRegisteredEvent(
    UUID customerId,
    String firstName,
    String lastName,
    String drivingLicense,
    String email
) implements DomainEvent {
}
