package com.pos.service;

import com.pos.model.Item;
import com.pos.pojos.ItemType;
import com.pos.repository.ItemsRepository;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by rrampall on 19/12/17.
 */
@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private Mapper mapper;

    public List<Item> getAllItems(){
            return (List<Item>) itemsRepository.findAll();
    }

    public void addItem(ItemType itemJaxb){
        Item item = mapper.map(itemJaxb, Item.class);
        item.setDate(new Date());
        System.out.println(item);
        itemsRepository.save(item);
    }

}
