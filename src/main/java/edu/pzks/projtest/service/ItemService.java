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
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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
        itemRepository.deleteAll();
        itemRepository.saveAll(items);
    }
    //  CRUD   - create read update delete

    public List<Item> getAll() {
        return itemRepository.findAll();
    }

    public Item getById(String id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isEmpty() || item.get() == null) {
            throw new NoSuchElementException("Item with id " + id + " not found.");
        }
        return item.get();
    }

    public Item create(Item item) {

        return itemRepository.save(item);
    }

    public Item create(ItemCreateRequest request) {
        if (itemRepository.existsByCode(request.code())) {
            throw  new IllegalStateException("code already exists");
        }
        Item item = mapToItem(request);
        item.setCreateDate(LocalDateTime.now());
        item.setUpdateDate(new ArrayList<LocalDateTime>());
        return itemRepository.save(item);
    }

    public  Item update(Item item) {
        return itemRepository.save(item);
    }


    public void delById(String id) {
        itemRepository.deleteById(id);
    }

    private Item mapToItem(ItemCreateRequest request) {
        Item item = new Item(request.name(), request.code(), request.description());
        return item;
    }

    public Item update(ItemUpdateRequest request) {
        Item itemPersisted = itemRepository.findById(request.id()).orElse(null);
        if (itemPersisted != null) {
            List<LocalDateTime> updateDates = itemPersisted.getUpdateDate();
            updateDates.add(LocalDateTime.now());
            Item itemToUpdate =
                    Item.builder()
                            .id(request.id())
                            .name(request.name())
                            .code(request.code())
                            .description(request.description())
                            .createDate(itemPersisted.getCreateDate())
                            .updateDate(updateDates)
                            .build();
            return itemRepository.save(itemToUpdate);

        }
        return null;
    }

}
