package edu.pzks.projtest.service;


/*
  @author   george
  @project   proj-test
  @class  ItemService
  @version  1.0.0 
  @since 09.09.24 - 12.16
*/

import edu.pzks.projtest.controller.ItemRestController;
import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;
import edu.pzks.projtest.request.ItemUpdateRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item( "Freddie Mercury", "Queen","vocal, piano"));
        items.add(new Item("2", "Paul McCartney", "Beatles","guitar"));
        items.add(new Item("3", "Mick Jagger", "Rolling Stones","vocal"));

    }

   @PostConstruct
    void init() {
     //   itemRepository.deleteAll();
    //    itemRepository.saveAll(items);
    }
    //  CRUD   - create read update delete

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(String id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item create(Item item) {
        if ( item.getId() != null && itemRepository.existsById(item.getId()) ) {
            return null;
        }
            return itemRepository.save(item);
    }


    public  Item update(Item item) {
        if ( item.getId() == null && !itemRepository.existsById(item.getId()) ) {
            return null;
        }
        return itemRepository.save(item);
    }


    public void delById(String id) {
        itemRepository.deleteById(id);
    }


}
