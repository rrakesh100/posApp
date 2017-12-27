package com.pos.controller;

import com.pos.model.Item;
import com.pos.pojos.ItemType;
import com.pos.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping (value="items")
    public void addItem(@RequestBody ItemType item){
        itemsService.addItem(item);
    }




}
