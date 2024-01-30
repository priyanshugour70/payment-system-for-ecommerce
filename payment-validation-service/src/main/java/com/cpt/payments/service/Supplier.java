package com.cpt.payments.service;

@FunctionalInterface
public interface Supplier<T> {
	T get();
}
