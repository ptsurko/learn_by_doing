package com.test.domain;

import com.test.validation.Create;
import com.test.validation.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class User {
    @NotNull(groups = { Update.class})
    @Null(groups= { Create.class })
    public Long id;

    @NotNull
    public String name;

    public User() { }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
