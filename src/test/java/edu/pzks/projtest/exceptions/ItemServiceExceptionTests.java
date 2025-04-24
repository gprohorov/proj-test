package edu.pzks.projtest.exceptions;

import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;
import edu.pzks.projtest.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/*
  @author george
  @project proj-test
  @class ItemServiceTest
  @version 1.0.0
  @since 11.04.25-20.01
*/

@SpringBootTest
class ItemServiceExceptionTests {

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

    @DisplayName("When item is not found then throws exception")
    @Test
    void whenItemNotFoundThenThrowsException() {
        //given
        String id = "123";
        given(mockRepository.findById(id)).willReturn(Optional.empty());
        // when
      Exception exception = assertThrows(NoSuchElementException.class, () -> underTest.getById(id));
        // then
       assertEquals("Item with id " + id + " not found.", exception.getMessage());
    }

    @DisplayName("When code is used  then throws exception")
    @Test
    void whenEmailUsedThenThrowsException() {
        //given
        request = new ItemCreateRequest("John Lennon", "Beatles", "guitar");
        given(mockRepository.existsByCode(request.code())).willReturn(true);
        // when
      Exception exception = assertThrows(IllegalStateException.class, () -> underTest.create(request));
        // then
        then(mockRepository).should(never()).save(any(Item.class));
        then(mockRepository).should(never()).save(any());


        assertNotNull(exception);
       assertTrue(exception.getMessage().contains("code already exists"));

       //finally
        verify(mockRepository, times(1)).existsByCode(request.code());
        verify(mockRepository, times(0)).save(any(Item.class));

    }




  //  @Test
    void update() {
    }
}
