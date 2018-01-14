package com.pos.service;

import com.pos.model.Item;
import com.pos.pojos.XItemType;
import com.pos.repository.ItemsRepository;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
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

    public List<XItemType> getAllItems(){
        List<Item> itemList = itemsRepository.findAllByOrderById();
        List<XItemType> xItemTypeList = new ArrayList<>();
        for(Item item : itemList) {
            XItemType xItemType = mapper.map(item, XItemType.class);
            xItemTypeList.add(xItemType);
        }
        return xItemTypeList;
    }

    public XItemType fetchItem(Long id){
        Item itemFromRepo = itemsRepository.findOne(id);
        return mapper.map(itemFromRepo, XItemType.class);
    }

    public void editItem(XItemType item){
        Item itemFromRequest = mapper.map(item, Item.class);
        Item itemFromRepo = itemsRepository.findOne(item.getId());
        BeanUtils.copyProperties(itemFromRepo,itemFromRequest, ServiceUtils.getNullPropertyNames(itemFromRequest) );
        itemsRepository.save(itemFromRequest);
    }

    public void addItem(XItemType xItemType){
        Item item = mapper.map(xItemType, Item.class);
        item.setDate(new Date());
        System.out.println(item);
        itemsRepository.save(item);
    }

    public List<XItemType> getAllItems(String searchPattern) {
        List<Item> itemList = itemsRepository.findAllBySearchPattern(searchPattern);
        List<XItemType> xItemTypeList = new ArrayList<>();
        for(Item item : itemList) {
            XItemType xItemType = mapper.map(item, XItemType.class);
            xItemTypeList.add(xItemType);
        }
        return xItemTypeList;
    }
}
