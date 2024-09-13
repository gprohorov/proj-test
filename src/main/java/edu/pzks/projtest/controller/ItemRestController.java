package edu.pzks.projtest.controller;


import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/*
  @author   george
  @project   proj-test
  @class  ItemRestController
  @version  1.0.0 
  @since 09.09.24 - 12.01
*/
@RestController
@RequestMapping("api/v1/items/")
@RequiredArgsConstructor
public class ItemRestController {
    private final ItemService itemService;


    // CRUD   create read update delete

    // read all
    @RequestMapping
    public List<Item> showAll() {
        return itemService.getAll();
    }

    // read one
    @GetMapping("{id}")
    public Item showOneById(@PathVariable String id) {
        String str = id;
        return itemService.getById(id);
    }

    @PostMapping
    public Item insert(@RequestBody Item item) {
        return itemService.create(item);
    }

    @PutMapping
    public Item edit(@RequestBody Item item) {
        return itemService.update(item);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        itemService.delById(id);
    }

}
