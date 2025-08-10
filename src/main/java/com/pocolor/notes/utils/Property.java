package com.pocolor.notes.utils;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Property<T> {
    private T value;
    private final ArrayList<Consumer<T>> listeners;

    private Property<T> boundTo;
    private BoundType boundType;

    private enum BoundType {
        SOURCE,
        TARGET,
        DIRECTIONAL
    }

    public Property() {
        this(null);
    }

    public Property(T initialValue) {
        this.value = initialValue;
        this.listeners = new ArrayList<>();
    }

    public T get() {
        return this.value;
    }

    public void set(T newValue) {
        if (this.boundType == BoundType.TARGET) {
            throw new IllegalStateException("Cannot change value. This instance is bound to " + this.boundTo);
        }

        if (newValue == null) {
            if (this.value != null) {
                _set(null);
            }
        } else if (!this.value.equals(newValue)) {
            _set(newValue);
        }

        if (this.boundTo != null) {
            this.boundTo._set(this.value);
        }
    }

    private void _set(T newValue) {
        this.value = newValue;
        this.notifyListeners();
    }

    @Override
    public String toString() {
        return "Property{" +
                "value=" + value +
                ", listeners=" + listeners +

                // causes infinite recursion (StackOverflowException)
                // this.toString() calls this.boundTo.toString() which calls this.toString()
//                ", boundTo=" + boundTo +
                ", boundState=" + boundType +
                '}';
    }

    public void addListener(Consumer<T> listener) {
        if (listener != null) {
            this.listeners.add(listener);
        } else {
            throw new IllegalArgumentException("Listener must not be null.");
        }
    }

    public void removeListener(Consumer<T> listener) {
        this.listeners.remove(listener);
    }

    private void notifyListeners() {
        for (Consumer<T> listener : this.listeners) {
            listener.accept(this.value);
        }
    }

    public void bind(Property<T> target) {
        this.bindInstance(target);
        target.bindInstance(this);

        this.boundType = BoundType.SOURCE;
        target.boundType = BoundType.TARGET;

        this._set(target.value);
    }

    public void bindDirectional(Property<T> source) {
        this.bindInstance(source);
        source.bindInstance(this);

        this.boundType = BoundType.DIRECTIONAL;
        source.boundType = BoundType.DIRECTIONAL;

        this._set(source.value);
    }

    private void bindInstance(Property<T> instance) {
        this.throwIfBound();
        this.boundTo = instance;
    }

    private void throwIfBound() throws IllegalStateException {
        if (this.boundTo != null) {
            throw new IllegalStateException("Already bound to " + this.boundTo);
        }
    }

    public void unbind() {
        if (this.boundTo != null) {
            this.boundTo._unbind();
            this._unbind();
        }
    }

    private void _unbind() {
        this.boundTo = null;
        this.boundType = null;
    }
}
