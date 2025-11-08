package edu.pzks.projtest.model;


/*
  @author   george
  @project   proj-test
  @class  Item
  @version  1.0.0 
  @since 09.09.24 - 11.53
*/

import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Document
public class Item  {
    @Id
    private String id;
    private String name;
    private String code;
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;





    public Item(String id, String name, String code, String description) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
    }

    public Item(String name, String code, String description) {
        this.name = name;
        this.code = code;
        this.description = description;
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
}
