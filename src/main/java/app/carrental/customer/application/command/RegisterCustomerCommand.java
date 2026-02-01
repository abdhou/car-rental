package app.carrental.customer.application.command;

import java.util.UUID;

public record RegisterCustomerCommand(
    UUID customerId,
    String firstName,
    String lastName,
    String drivingLicense,
    String email
) {
}
