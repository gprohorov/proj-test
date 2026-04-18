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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
  @author   george
  @project   proj-test
  @class  ItemServiceTest
  @version  1.0.0 
  @since 11.04.25 - 20.01
*/

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ItemServiceArgsCaptorTests {

    @Mock
    private ItemRepository mockRepository;

    @InjectMocks
    private ItemService underTest;

    @Captor
    private ArgumentCaptor<Item> argumentCaptor;

    private ItemCreateRequest request;
    private Item item;


    @BeforeEach
    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        underTest = new ItemService(mockRepository);
    }
   @AfterEach
    void tearsDown(){

    }

    @DisplayName("Create new Item. Happy Path")
    @Test
    void whenInsertNewItemAndNameIsNotNullThenOk() {
        //given
       ItemCreateRequest request = new ItemCreateRequest("Till Lindemann", "Rammstein", "poet");
        // when
        Item itemPersisted = underTest.createByRequest(request);
        // then
       then(mockRepository).should().save(argumentCaptor.capture());
       Item itemToSave = argumentCaptor.getValue();
       assertThat(itemToSave.getName()).isEqualTo(request.name());
       assertThat(itemToSave.getCode()).isEqualTo(request.code());
       assertThat(itemToSave.getDescription()).isEqualTo(request.description());
       verify(mockRepository).save(itemToSave);
       verify(mockRepository, times(1)).save(itemToSave);
    }

    @Test
    void whenInsertNewItemAndNameIsNullThenFail() {
    }

    @Test
    void whenUpdateExistingItemThenOk() {
    }

    @Test
    void whenUpdateNotExistingItemThenFail() {
    }





}
