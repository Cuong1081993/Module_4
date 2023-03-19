package com.example.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public abstract class ModalGeneral {

    @Column(name = "create_at", updatable = false)
    @CreationTimestamp
    private Date createAt;

    @Column(name = "create_by")
    private Long createBy;

    @Column(name = "update_at")
    @UpdateTimestamp
    private Date updateAt;
    @Column(name = "update_by")
    private Long updateBy;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;
}
