package edu.pzks.projtest.service;

import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/*
  @author   george
  @project   proj-test
  @class  ItemServiceTest
  @version  1.0.0 
  @since 11.04.25 - 20.01
*/

@SpringBootTest
class ItemServiceTest {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ItemService underTest;

    @BeforeEach
    void setUp() {
    }
  //  @AfterEach
    void tearsDown(){
        repository.deleteAll();
    }

    @Test
    void whenInsertNewItem_ThenCreateDateIsPresent() {
        //given
        ItemCreateRequest request = new ItemCreateRequest("Till Lindemman", "Rammstein", "poet");
        LocalDateTime now = LocalDateTime.now();
        // when
        Item createdItem = underTest.createByRequest(request);
        // then
        assertNotNull(createdItem);
        assertNotNull(createdItem.getId());
        assertEquals("Till Lindemman", createdItem.getName());
        assertEquals("Rammstein", createdItem.getCode());
        assertEquals("poet", createdItem.getDescription());
        assertNotNull(createdItem.getCreatedDate());
        assertSame(LocalDateTime.class, createdItem.getCreatedDate().getClass());
        assertTrue(createdItem.getCreatedDate().isAfter(now));
        assertNotNull(createdItem.getLastModifiedDate());
        assertSame(ArrayList.class, createdItem.getLastModifiedDate().getClass());
    }

    @Test
    void update() {
    }
}
