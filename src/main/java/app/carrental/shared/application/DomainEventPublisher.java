package app.carrental.shared.application;

import app.carrental.shared.domain.DomainEvent;

import java.util.List;

public interface DomainEventPublisher {

    void publish(DomainEvent event);

    default void publish(List<DomainEvent> events) {
        for (DomainEvent event : events) {
            publish(event);
        }
    }
}
