package app.carrental.customer.domain.repository;

import app.carrental.customer.domain.model.Customer;
import app.carrental.customer.domain.model.CustomerId;

public interface CustomerRepository {
    Customer load(CustomerId id);
    void save(Customer customer);
    boolean exists(CustomerId id);
}
