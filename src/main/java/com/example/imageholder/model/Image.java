package com.example.imageholder.model;

import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

import static com.example.imageholder.model.BaseEntity.ALLOCATION_SIZE;

@Entity
@Table(name = "IMAGES")
@SequenceGenerator(name = "sequence", sequenceName = "SEQ_IMAGES", allocationSize = ALLOCATION_SIZE)
public class Image extends BaseEntity {

    private String name;

    @LastModifiedDate
    private LocalDateTime lastUpdatedAt;

    private Long size;

    private String extension;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(LocalDateTime lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
