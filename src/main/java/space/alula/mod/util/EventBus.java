package space.alula.mod.util;

import space.alula.mod.event.EventType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class EventBus {
    private Map<Class<? extends EventType>, List<Predicate>> eventListeners;

    public EventBus() {
        this.eventListeners = new HashMap<>();
    }

    public <T extends EventType> void addListener(Class<T> type, Predicate<T> consumer) {
        eventListeners.computeIfAbsent(type, t -> new LinkedList<>()).add(consumer);
    }

    public <T extends EventType> void removeListener(Class<T> type, Predicate<T> consumer) {
        eventListeners.computeIfAbsent(type, t -> new LinkedList<>()).remove(consumer);
    }

    public boolean publishEmpty(Class<? extends EventType> type) {
        List<Predicate> handlers = eventListeners.get(type);
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
        List<Predicate> handlers = eventListeners.get(event.getClass());
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
