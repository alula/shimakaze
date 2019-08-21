package space.alula.mod.util;

import space.alula.mod.event.EventType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class EventBus {
    private Map<Class<? extends EventType>, List<Consumer>> eventListeners;
    private Map<Class<? extends EventType>, List<Predicate>> eventHandlers;

    public EventBus() {
        this.eventHandlers = new HashMap<>();
        this.eventListeners = new HashMap<>();
    }

    /**
     * Registers an event handler
     *
     * @param type    the event type class.
     * @param handler event handling method, if returns true, event is marked as handled and remaining handlers can't handle it.
     * @param <T>     the event type
     */
    public <T extends EventType> void addHandler(Class<T> type, Predicate<T> handler) {
        eventHandlers.computeIfAbsent(type, t -> new LinkedList<>()).add(handler);
    }

    public <T extends EventType> void removeHandler(Class<T> type, Predicate<T> handler) {
        eventHandlers.computeIfAbsent(type, t -> new LinkedList<>()).remove(handler);
    }

    public <T extends EventType> void addListener(Class<T> type, Consumer<T> consumer) {
        eventListeners.computeIfAbsent(type, t -> new LinkedList<>()).add(consumer);
    }

    public <T extends EventType> void removeListener(Class<T> type, Consumer<T> consumer) {
        eventListeners.computeIfAbsent(type, t -> new LinkedList<>()).remove(consumer);
    }

    public boolean publishEmpty(Class<? extends EventType> type) {
        List<Consumer> listeners = eventListeners.get(type);
        if (listeners != null) {
            for (Consumer consumer : listeners) {
                consumer.accept(null);
            }
        }

        List<Predicate> handlers = eventHandlers.get(type);
        if (handlers != null) {
            for (Predicate predicate : handlers) {
                if (!predicate.test(null)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean publish(EventType event) {
        List<Consumer> listeners = eventListeners.get(event.getClass());
        if (listeners != null) {
            for (Consumer consumer : listeners) {
                consumer.accept(event);
            }
        }

        List<Predicate> handlers = eventHandlers.get(event.getClass());
        if (handlers != null) {
            for (Predicate predicate : handlers) {
                if (!predicate.test(event)) {
                    return true;
                }
            }
        }

        return false;
    }

    public void registerEventType(Class<? extends EventType> type) {
        eventListeners.put(type, new LinkedList<>());
    }
}
