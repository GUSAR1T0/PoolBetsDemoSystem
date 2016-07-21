package com.poolbets.application.additions;

/**
 * Created by Mashenkin Roman on 15.07.16.
 */
public class Pair<C, V> {

    private C code;
    private V value;

    public Pair(C code, V value) {
        this.code = code;
        this.value = value;
    }

    public C getCode() {
        return code;
    }

    public V getValue() {
        return value;
    }
}