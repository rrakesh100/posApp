package com.pos.controller;

import com.pos.model.Item;
import com.pos.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by rrampall on 19/12/17.
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1")
public class ItemsController {


    @Autowired
    private ItemsService itemsService;

    @GetMapping(value="items")
    public List<Item> listAll(){
        return itemsService.getAllItems();
    }

}
