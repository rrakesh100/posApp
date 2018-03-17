package com.pos.controller;

import com.pos.commons.Response;
import com.pos.model.Item;
import com.pos.pojos.XItem;
import com.pos.service.ItemsService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by rrampall on 19/12/17.
 */
@RestController
//Allowing from all for now
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1/")
public class ItemsController {

    @Autowired
    private ItemsService itemsService;


    private String name="More super market";

    @GetMapping(value="items")
    public List<XItem> listAll(){
        return itemsService.getAllItems();
    }

    @GetMapping(value="items/{itemId}")
    public @ResponseBody XItem fetchItem(@PathVariable(value="itemId") String itemId ){
        XItem it =  itemsService.fetchItem(itemId);
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
    List<XItem> getFilteredItems(@RequestParam(value="searchPattern") String searchPattern) {
        List<XItem> filterdItems = new ArrayList<>();
        if(searchPattern.matches("[0-9]+")) {
            // exact search by barcode
           XItem item = itemsService.fetchItem(searchPattern);
           if(item !=null)
               filterdItems.add(item);
        }else{
            //pattern search by name or SKU
            filterdItems.addAll(itemsService.getItemNameAndIdMapping(searchPattern));
        }
        return filterdItems;
    }

    @DeleteMapping(value="items/{itemId}")
    public @ResponseBody
    ResponseEntity<HttpStatus> deleteItem(@PathVariable(value="itemId") String itemId){
        itemsService.deleteItem(itemId);
        return new Response<HttpStatus>().noContent().build();
    }
}

