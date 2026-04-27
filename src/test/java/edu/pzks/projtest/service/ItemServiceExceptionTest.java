package edu.pzks.projtest.service;

import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;
import edu.pzks.projtest.request.ItemSearchRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

/*
  @author   george
  @project   proj-test
  @class  ItemServiceTest
  @version  1.0.0 
  @since 11.04.25 - 20.01
*/

@SpringBootTest
class ItemServiceExceptionTest {

    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ItemService underTest;

    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item( "1", "Freddie Mercury", "Queen","vocal, piano"));
        items.add(new Item("2", "Paul McCartney", "Beatles","guitar"));
        items.add(new Item("3", "Mick Jagger", "Rolling Stones","vocal"));

    }


    @BeforeEach
    void setUp() {
    }
  //  @AfterEach
    void tearsDown(){
        repository.deleteAll();
    }

    @Test
    void whenItemIsAbsentByIdThenExceptionIsThrown() {
        //given
       String id = "1";
        // when
       Exception exception = assertThrows(NoSuchElementException.class,
               () -> underTest.getByIdOrThrow(id));
       //then
        assertNotNull(exception);
        assertTrue(exception instanceof NoSuchElementException);
        assertTrue(exception.getMessage().equals("Item not found"));

    }

    @Test
    void searchHappyPath() {
        //given
        ItemSearchRequest request = new ItemSearchRequest("e", 3);
        given(repository.findAll()).willReturn(items);
    }


    @Test
    void whenItemsAreOutOfBoundsRequestedThenExceptionIsThrown() {
        //given
        ItemSearchRequest request = new ItemSearchRequest("e", 5);
        given(repository.findAll()).willReturn(items);
        //then
        Exception exception = assertThrows(NoSuchElementException.class,
                () -> underTest.findByFragment(request));
    }





}
