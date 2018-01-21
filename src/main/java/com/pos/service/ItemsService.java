package com.pos.service;

import com.pos.model.Item;
import com.pos.pojos.XItem;
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

    public List<XItem> getAllItems(){
        List<Item> itemList = itemsRepository.findAllByOrderById();
        List<XItem> xItemList = new ArrayList<>();
        for(Item item : itemList) {
            XItem xItem = mapper.map(item, XItem.class);
            xItemList.add(xItem);
        }
        return xItemList;
    }

    public XItem fetchItem(String id){
        Item itemFromRepo = itemsRepository.findOne(id);
        if(itemFromRepo!=null)
            return mapper.map(itemFromRepo, XItem.class);
        else
            return null;
    }

    public void editItem(XItem item){
        Item itemFromRequest = mapper.map(item, Item.class);
        Item itemFromRepo = itemsRepository.findOne(item.getBarcode());
        BeanUtils.copyProperties(itemFromRepo,itemFromRequest, ServiceUtils.getNullPropertyNames(itemFromRequest) );
        itemsRepository.save(itemFromRequest);
    }

    public void addItem(XItem xItem){
        Item item = mapper.map(xItem, Item.class);
        item.setDate(new Date());
        System.out.println(item);
        itemsRepository.save(item);
    }

    /*
  * @param searchPattern : item name pattern
  * @return : Map of Item name to Item Id
  * */
    public List<XItem> getItemNameAndIdMapping(String searchPattern) {
        List<Item> namesList = itemsRepository.findAllItemsWithName("%" + searchPattern + "%");
        List<Item> skuList = itemsRepository.findAllItemsWithSKU("%" + searchPattern + "%");

        List<Item> allItems  = new ArrayList<>();
        allItems.addAll(namesList);
        //TODO Important handle the scenario where an item could feature in both name search and SKU search
        allItems.addAll(skuList);

        List<XItem> xItemList = new ArrayList<>();
        for(Item item : allItems) {
            XItem xItem = mapper.map(item, XItem.class);
            xItemList.add(xItem);
        }
        return xItemList;
    }


}
