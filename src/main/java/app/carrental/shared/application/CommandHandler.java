package app.carrental.shared.application;

public interface CommandHandler<T> {
    void handle(T command);
}
