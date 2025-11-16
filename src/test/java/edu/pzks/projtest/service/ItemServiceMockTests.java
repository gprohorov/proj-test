package edu.pzks.projtest.service;

import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*
  @author   george
  @project   proj-test
  @class  ItemServiceTest
  @version  1.0.0 
  @since 11.04.25 - 20.01
*/

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ItemServiceMockTests {

    @Mock
    private ItemRepository mockRepository;

    @InjectMocks
    private ItemService underTest;



    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        underTest = new ItemService(mockRepository);
    }
   @AfterEach
    void tearsDown(){
    }

    @DisplayName("Create new Item. An item with such an id still exists. Fail")
    @Test
    void whenInsertNewItemAndSuchIdExistsThenFail() {
        //given
        Item itemToSave = new Item("5","Till Lindemann----------", "Rammstein", "poet");
        given(mockRepository.existsById(itemToSave.getId())).willReturn(true);
        // when
         Item itemPersisted = underTest.create(itemToSave);
        // then
        then(mockRepository).should(never()).save(itemToSave);
        assertNull(itemPersisted);
        verify(mockRepository, never()).save(itemToSave);
        verify(mockRepository, times(0)).save(itemToSave);
        verify(mockRepository, times(1)).existsById(itemToSave.getId());
    }

    @DisplayName("Create new Item. An item with such an id doesn't exist. Ok")
    @Test
    void whenInsertNewItemAndSuchIdNotExistsThenOk() {
        //given
        Item itemToSave = new Item("5","Till Lindemann----------", "Rammstein", "poet");
        given(mockRepository.existsById(itemToSave.getId())).willReturn(false);
        given(mockRepository.save(itemToSave)).willReturn(itemToSave);

        // when
         Item itemPersisted = underTest.create(itemToSave);
        // then
        then(mockRepository).should().save(itemToSave);

        assertNotNull(itemPersisted);

        verify(mockRepository, times(1)).save(itemToSave);
        verify(mockRepository, times(1)).existsById(itemToSave.getId());
    }


  //  @Test
    void update() {
    }
}
