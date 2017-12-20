package com.pos.service;

import com.pos.model.Item;
import com.pos.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rrampall on 19/12/17.
 */
@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;

    public List<Item> getAllItems(){
            return (List<Item>) itemsRepository.findAll();
    }

}
