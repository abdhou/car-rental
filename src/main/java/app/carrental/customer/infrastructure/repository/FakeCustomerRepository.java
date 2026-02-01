package app.carrental.customer.infrastructure.repository;

import app.carrental.customer.domain.model.Customer;
import app.carrental.customer.domain.model.CustomerId;
import app.carrental.customer.domain.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

public class FakeCustomerRepository implements CustomerRepository {

    private final Map<CustomerId, Customer> customers = new HashMap<>();

    @Override
    public Customer load(CustomerId id) {
        return customers.get(id);
    }

    @Override
    public void save(Customer customer) {
        this.customers.put(customer.id(), customer);
    }

    @Override
    public boolean exists(CustomerId id) {
        return customers.get(id) != null;
    }
}
