package com.androiddesdecero.myapplication.model;

import java.util.List;

public class Data {

    private String count;
    private String next;
    private List<Personaje> results;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Personaje> getResults() {
        return results;
    }

    public void setResults(List<Personaje> results) {
        this.results = results;
    }
}
