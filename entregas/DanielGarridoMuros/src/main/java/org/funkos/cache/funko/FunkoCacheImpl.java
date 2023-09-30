package org.funkos.cache.funko;

import org.funkos.cache.funko.FunkoCache;
import org.funkos.models.Funko;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class FunkoCacheImpl implements FunkoCache {
    private final int MaxSize;
    private final Map<Integer, Funko> CACHE;

    public FunkoCacheImpl(int Max_Size){
        this.MaxSize = Max_Size;
        this.CACHE = new LinkedHashMap<Integer, Funko>(this.MaxSize, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Funko> eldest) {
                return size() > MaxSize;
            }
        };

    }

    @Override
    public void put(Integer key, Funko value) {
        CACHE.put(key, value);
    }

    @Override
    public Funko get(Integer key) {
        return CACHE.get(key);
    }

    @Override
    public void remove(Integer key) {
        CACHE.remove(key);
    }

    @Override
    public void clear() {
        CACHE.entrySet().removeIf(entry -> {
            boolean shouldRemove = entry.getValue().getUpdated_at().plusMinutes(1).isBefore(LocalDateTime.now());
            return shouldRemove;
        });
    }

}
