package app.carrental.shared.infrastructure;

import app.carrental.shared.application.DomainEventPublisher;
import app.carrental.shared.domain.DomainEvent;

import java.util.ArrayList;
import java.util.List;

public class FakeDomainEventPublisher implements DomainEventPublisher {

    private final List<DomainEvent> publishedEvents = new ArrayList<>();

    @Override
    public void publish(DomainEvent event) {
            publishedEvents.add(event);
    }

    public List<DomainEvent> publishedEvents() {
        return publishedEvents;
    }
}
