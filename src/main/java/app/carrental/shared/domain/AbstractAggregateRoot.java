package app.carrental.shared.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAggregateRoot {

    private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

    protected abstract void when(DomainEvent event);
    protected abstract void ensureValidState();

    protected AbstractAggregateRoot() {
    }

    protected void apply(DomainEvent event) {
        when(event);
        ensureValidState();
        uncommittedEvents.add(event);
    }

    public List<DomainEvent> uncommittedEvents() {
        return List.copyOf(uncommittedEvents);
    }

    public List<DomainEvent> pullUncommittedEvents() {
        var events = uncommittedEvents();
        uncommittedEvents.clear();
        return events;
    }
}
