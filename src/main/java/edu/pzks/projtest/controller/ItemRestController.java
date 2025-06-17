package edu.pzks.projtest.controller;


import edu.pzks.projtest.model.Item;
import edu.pzks.projtest.repository.ItemRepository;
import edu.pzks.projtest.request.ItemCreateRequest;
import edu.pzks.projtest.request.ItemUpdateRequest;
import edu.pzks.projtest.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/*
  @author   nick
  @project   proj-test
  @class  ItemRestController
  @version  1.0.0 
  @since 09.09.24 - 12.01
*/
@Slf4j
@RestController
@RequestMapping("api/v1/items")
@RequiredArgsConstructor
public class ItemRestController {

    private final ItemService itemService;

    @GetMapping
    public List<Item> showAll() {
     //log.info("Smth");
        return itemService.getAll();
    }

    // read one
    @GetMapping("/{id}")
    public Item showOneById(@PathVariable String id) {

    return itemService.getById(id);
    }

    @PostMapping
    public Item insert(@RequestBody Item item) {
        return itemService.create(item);
    }

    //============== request =====================
    @PostMapping("/dto")
    public Item insert(@RequestBody ItemCreateRequest request) {
        return itemService.create(request);
    }

    @PutMapping
    public Item edit(@RequestBody Item item) {
        return itemService.update(item);
    }
    //============== request =====================
    @PutMapping("/dto")
    public Item edit(@RequestBody ItemUpdateRequest request) {
        return itemService.update(request);
    }


    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        itemService.delById(id);
    }

}
