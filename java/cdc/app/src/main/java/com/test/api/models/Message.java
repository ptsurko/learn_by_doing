package com.test.api.models;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="messages")
//@TypeDef(name = "json", typeClass = JsonStringType.class)
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Message {
    @Id
    @GeneratedValue
    @Column(name="id")
    private long id;

    @Type(type = "jsonb")
    @Column(name="jsonb", columnDefinition = "jsonb")
    private String jsonb;

//    @Type(type = "json")
//    @Column(name="json", columnDefinition = "json")
//    private String json;

    @Column(name="created_at")
    private Timestamp createdAt;

    public Message() { }

    public Message(String json) {
//        this.json = json;
        this.jsonb = json;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public String getJson() {
//        return json;
//    }
//
//    public void setJson(String json) {
//        this.json = json;
//    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public String getJsonb() {
        return jsonb;
    }

    public void setJsonb(String jsonb) {
        this.jsonb = jsonb;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("id=").append(id);
        sb.append(", jsonb='").append(jsonb).append('\'');
//        sb.append(", json='").append(json).append('\'');
        sb.append(", createdAt=").append(createdAt);
        sb.append('}');
        return sb.toString();
    }
}
