package com.pos.controller;

import com.pos.commons.Response;
import com.pos.model.Item;
import com.pos.pojos.ItemJaxb;
import com.pos.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Item> listAll(){
        return itemsService.getAllItems();
    }

    @GetMapping(value="items/{itemId}")
    public @ResponseBody
    ItemJaxb fetchItem(@PathVariable(value="itemId") String itemId ){
        ItemJaxb it =  itemsService.fetchItem(Long.valueOf(itemId));
        return it;
    }

    @PutMapping(value="items")
    public @ResponseBody
    ResponseEntity<HttpStatus> editItem(@RequestBody ItemJaxb item){
        itemsService.editItem(item);
        return new Response<HttpStatus>().noContent().build();
    }



    @PostMapping (value="items")
    public void addItem(@RequestBody ItemJaxb item){
        itemsService.addItem(item);
    }




}
