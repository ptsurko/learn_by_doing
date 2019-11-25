package com.test.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.test.api.models.Message;

public class MessageDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("json")
    private Object json;

    public MessageDTO() {}

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.json = message.getJsonb();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getJson() {
        return json;
    }

    public void setJson(Object name) {
        this.json = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDTO{");
        sb.append("id=").append(id);
        sb.append(", json='").append(json).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
