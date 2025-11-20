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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/*
  @author   george
  @project   proj-test
  @class  ItemServiceTest
  @version  1.0.0 
  @since 11.04.25 - 20.01
*/

@SpringBootTest
class ItemServiceMockTests {

    @Mock
    private ItemRepository mockRepository;

    private ItemService underTest;

    @Captor
    private ArgumentCaptor<Item> argumentCaptor;

    private ItemCreateRequest request;
    private Item item;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new ItemService(mockRepository);
    }
   @AfterEach
    void tearsDown(){

    }

    @DisplayName("Create new Item. Happy Path")
    @Test
    void whenInsertNewItemAndCodeNotExistsThenOk() {
        //given
        request = new ItemCreateRequest("Till Lindemann", "Rammstein", "poet");
        item = Item.builder()
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .build();
        given(mockRepository.existsByCode(request.code())).willReturn(false);
        // when
         underTest.create(request);
        // then
       then(mockRepository).should().save(argumentCaptor.capture());
       Item itemToSave = argumentCaptor.getValue();
       assertThat(itemToSave.getName()).isEqualTo(request.name());
       assertNotNull(itemToSave.getCreateDate());
       assertTrue(itemToSave.getCreateDate().isBefore(LocalDateTime.now()));
       //assertTrue(itemToSave.getUpdateDate().isEmpty());
       verify(mockRepository).save(itemToSave);
       verify(mockRepository, times(1)).existsByCode(request.code());
       verify(mockRepository, times(1)).save(itemToSave);
    }

  //  @Test
    void update() {
    }
}
