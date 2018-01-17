package com.pos.controller;

import com.pos.commons.Response;
import com.pos.pojos.XItem;
import com.pos.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by rrampall on 19/12/17.
 */
@RestController
//Allowing from all for now
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;

    @GetMapping(value="items")
    public List<XItem> listAll(){
        return itemsService.getAllItems();
    }

    @GetMapping(value="items/{itemId}")
    public @ResponseBody XItem fetchItem(@PathVariable(value="itemId") String itemId ){
        XItem it =  itemsService.fetchItem(Long.valueOf(itemId));
        return it;
    }

    @PutMapping(value="items")
    public @ResponseBody
    ResponseEntity<HttpStatus> editItem(@RequestBody XItem item){
        itemsService.editItem(item);
        return new Response<HttpStatus>().noContent().build();
    }

    @PostMapping (value="items")
    public void addItem(@RequestBody XItem item){
        itemsService.addItem(item);
    }

    @GetMapping(value="filteredItems")
    public @ResponseBody
    Map<String, Long> getFilteredItems(@RequestParam(value="searchPattern") String searchPattern) {
        return itemsService.getSupplierNameAndIdMapping(searchPattern);
    }
}
//when to use response body
