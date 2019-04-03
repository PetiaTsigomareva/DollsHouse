package com.petia.dollhouse.domain.entities;

import javax.persistence.*;

import com.petia.dollhouse.domain.enums.StatusValues;
import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid-string")
    @GenericGenerator(name = "uuid-string", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusValues status;

    public BaseEntity() {
        setStatus(StatusValues.ACTIVE);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public StatusValues getStatus() {
        return status;
    }

    public void setStatus(StatusValues status) {
        this.status = status;
    }
}
