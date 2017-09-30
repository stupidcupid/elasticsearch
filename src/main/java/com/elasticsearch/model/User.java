package com.elasticsearch.model;

/**
 * Created by nanzhou on 2017/9/30.
 */
public class User {

    private int age;

    private String name;

    private long weight;

    private boolean married;

    public User() {

        this.age = 11;
        this.name = "default";
        this.weight = 1000l;
        this.married = false;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }
}
