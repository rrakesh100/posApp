package com.pos.service;

import com.pos.model.Item;
import com.pos.pojos.ItemType;
import com.pos.repository.ItemsRepository;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ItemType fetchItem(Long id){
        Item itemFromRepo = itemsRepository.findOne(id);
        return mapper.map(itemFromRepo, ItemType.class);

    }

    public void editItem(ItemType item){
        Item itemFromRequest = mapper.map(item, Item.class);
        Item itemFromRepo = itemsRepository.findOne(item.getId());
        BeanUtils.copyProperties(itemFromRepo,itemFromRequest, getNullPropertyNames(itemFromRequest) );
        itemsRepository.save(itemFromRequest);
    }

    public void addItem(ItemType itemJaxb){
        Item item = mapper.map(itemJaxb, Item.class);
        item.setDate(new Date());
        System.out.println(item);
        itemsRepository.save(item);
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        List<String> emptyNames = new ArrayList();
        for(java.beans.PropertyDescriptor pd : pds) {
            //check if value of this property is null then add it to the collection
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return  emptyNames.toArray(result);
    }
}
