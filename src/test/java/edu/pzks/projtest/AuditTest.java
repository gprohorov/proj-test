package edu.pzks.projtest;


/*
  @author   george
  @project   proj-test
  @class  RepositoryTest
  @version  1.0.0 
  @since 07.10.24 - 11.48
*/

import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.service.ItemService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuditTest {

    @Autowired
    ItemService underTest;

    @BeforeAll
    void beforeAll() {}

    @BeforeEach
    void setUp() {
        Item freddy = new Item( "Freddy Mercury", "Queen", "###test  audit test");
        Item paul = new Item( "Paul McCartney", "Beatles", "###test audit test");
        Item mick = new Item( "Mick Jagger", "Rolling Stones", "###test audit test ");
        underTest.createAll(List.of(freddy, paul, mick));
    }

   // @AfterEach
    void tearDown() {
        List<Item> itemsToDelete = underTest.getAll().stream()
                .filter(item -> item.getDescription().contains("###test"))
                .toList();
        for (Item item : itemsToDelete) {
            underTest.delById(item.getId());
        }
       // underTest.deleteAll(itemsToDelete);
    }

    @AfterAll
    void afterAll() {}

    @Test
    void testSetShouldContains_3_Records_ToTest(){
        List<Item> itemsToDelete = underTest.getAll().stream()
                .filter(item -> item.getDescription().contains("###test"))
                .toList();
        assertEquals(3,itemsToDelete.size());
    }

}
