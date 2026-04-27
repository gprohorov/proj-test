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
import edu.pzks.projtest.request.ItemSearchRequest;
import edu.pzks.projtest.request.ItemUpdateRequest;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private List<Item> items = new ArrayList<>();
    {
        items.add(new Item( "Freddie Mercury---", "Queen","vocal, piano"));
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
        return itemRepository.findById(id).orElse(null);
    }

    public Item getByIdOrThrow(String id) {
        return itemRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("Item not found"));
    }

    public Item create(Item item) {

        return itemRepository.save(item);
    }

    public Item createByRequest(ItemCreateRequest request) {
        if (request.name() == null) {
            return null;
        }
        Item item = mapToItem(request);
        return this.create(item);
    }

    public  Item update(Item item) {
        return itemRepository.save(item);
    }


    public void delById(String id) {
        getByIdOrThrow(id);
        itemRepository.deleteById(id);
    }

    private Item mapToItem(ItemCreateRequest request) {
        Item item = new Item(request.name(), request.code(), request.description());
        return item;
    }

    public Item updateByRequest(ItemUpdateRequest request) {

        if (itemRepository.existsById(request.id())) {
            Item itemToUpdate =
                    Item.builder()
                            .id(request.id())
                            .name(request.name())
                            .code(request.code())
                            .description(request.description())
                            .build();
            return this.create(itemToUpdate);

        }
        return null;
    }

    public List<Item> findByFragment(ItemSearchRequest request) {

        String fragment = request.fragment();
        List<Item> limited = new ArrayList<>();
        List<Item> items = this.getAll().stream()
                .filter(item -> item.getName().contains(fragment))
                .toList();

            limited= items.subList(0, request.amount());

        return limited;
    }

}
