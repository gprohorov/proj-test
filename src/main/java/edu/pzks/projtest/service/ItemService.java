package edu.pzks.projtest.service;


/*
  @author   george
  @project   proj-test
  @class  ItemService
  @version  1.0.0 
  @since 09.09.24 - 12.16
*/

import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item("1", "name1", "000001","description1"));
        items.add(new Item("2", "name2", "000002","description3"));
        items.add(new Item("3", "name3", "000003","description3"));
    }

  //  @PostConstruct
    void init() {
        itemRepository.saveAll(items);
    }
    //  CRUD   - create read update delete
    public List<Item> getItems() {
        return itemRepository.findAll();
    }



}
