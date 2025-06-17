package edu.pzks.projtest.model;


/*
  @author   nick
  @project   proj-test
  @class  Item
  @version  1.0.0 
  @since 09.09.24 - 11.53
*/

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Document
public class Item   {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;
    //---------- custom audit  ----------------
    private LocalDateTime createDate;
    private List<LocalDateTime> updateDate;

    public Item() {
    }

    public Item(String id,
                String name,
                String code,
                String description,
                LocalDateTime createDate,
                List<LocalDateTime> updateDate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Item(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public Item(String id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<LocalDateTime> getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(List<LocalDateTime> updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;
        return getId().equals(item.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}
