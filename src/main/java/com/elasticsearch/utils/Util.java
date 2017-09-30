package com.elasticsearch.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.elasticsearch.common.xcontent.XContentBuilder;

/**
 * Created by nanzhou on 2017/9/30.
 */
public class Util {

    public static ObjectMapper mapper = new ObjectMapper();

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static String toJson(Object o) {
        try {
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
            return writer.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "转换 JSON 时发生异常";
        }
    }

    /**
     * mapping 一旦定义，之后就不能修改。 * @return
     *
     * @throws Exception
     */
    /*private static XContentBuilder getMapping() throws Exception {
        XContentBuilder mapping = jsonBuilder()
                .startObject().startObject("test").startObject("properties")
                .startObject("id")
                .field("type", "long")
                .field("store", "yes")
                .endObject()
                .startObject("type")
                .field("type", "string").field("index", "not_analyzed")
                .endObject()
                .startObject("catIds").field("type", "integer")
                .endObject()
                .endObject()
                .endObject()
                .endObject();
        return mapping;
    }*/
}
