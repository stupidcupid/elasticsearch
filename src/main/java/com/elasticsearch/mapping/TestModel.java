package com.elasticsearch.mapping;

import java.io.Serializable;
import java.util.List;

/**
 * Created by nanzhou on 2017/9/30.
 */
public class TestModel implements Serializable {

    private long id;

    private String type;

    private List<Integer> catIds;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Integer> getCatIds() {
        return catIds;
    }

    public void setCatIds(List<Integer> catIds) {
        this.catIds = catIds;
    }
}
