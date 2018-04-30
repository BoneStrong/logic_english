package com.dzz;

import org.codehaus.jackson.annotate.JsonWriteNullProperties;

import java.util.List;

/**
 * @Author shawn
 * @Date 2017/9/20 17:59
 */
@JsonWriteNullProperties(false)
public class JsonDto {

    private int id;

    private String namespace;

    private List<String> ips;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }
}
