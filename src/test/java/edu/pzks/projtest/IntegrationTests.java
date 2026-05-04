package edu.pzks.projtest;


/*
  @author   george
  @project   proj-test
  @class  IntegrationTests
  @version  1.0.0 
  @since 04.05.25 - 08.29
*/

import edu.pzks.projtest.Utils.Utils;
import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemRepository repository;



    private List<Item> items = new ArrayList<>();

    @BeforeEach
    void setUp() {

            items.add(new Item( "1","Freddie Mercury", "Queen","vocal, piano"));
            items.add(new Item("2", "Paul McCartney", "Beatles","guitar"));
            items.add(new Item("3", "Till Lindemann", "Rammstein","vocal"));
            repository.saveAll(items);
    }

    @AfterEach
    void tearsDown(){
     repository.deleteAll();
    }


    @Test
    void whenHappyPathCreateNewItem() throws Exception {
        // given
        ItemCreateRequest request = new ItemCreateRequest(
                "Steven Tyler", "Aerosmith", "leader, guitar");
        // when
     ResultActions perform = mockMvc.perform(post("http://localhost:8080/api/v1/items/dto")
             .contentType(MediaType.APPLICATION_JSON)
             .content(Utils.toJson(request))); //!!!!!!!!!!!!!!!

     //then
        Item item = repository.findAll()
                        .stream()
                                .filter(it -> it.getName().equals(request.name()))
                                        .findFirst().orElse(null);

        perform.andExpect(status().is(200));
        assertNotNull(item);
        assertNotNull(item.getId());
        assertThat(item.getId()).isNotEmpty();
        assertThat(item.getId().length()).isEqualTo(24);
        assertThat(item.getDescription()).isEqualTo(request.description());
        assertThat(item.getName()).isEqualTo(request.name());
        assertThat(item.getCode()).isEqualTo(request.code());
        assertNotNull(item.getUpdateDate());
        assertThat(item.getCreateDate()).isNotNull();
    }

    @Test
    void whenIdExistsCreateNewItemThenFailure() throws Exception {
        // create item -  negative scenario :  id is already present

    }


    // update - happy path
    // update - negative
    // get one (positive, negative)





}
