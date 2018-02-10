package com.pos.service;

import com.pos.model.Item;
import com.pos.model.ItemUpdate;
import com.pos.pojos.XItem;
import com.pos.repository.ItemUpdatesRepository;
import com.pos.repository.ItemsRepository;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by rrampall on 19/12/17.
 */
@Service
public class ItemsService {

    @Autowired
    private ItemsRepository itemsRepository;


    @Autowired
    private ItemUpdatesRepository itemUpdatesRepository;

    @Autowired
    private Mapper mapper;

    public List<XItem> getAllItems(){
        List<Item> itemList = itemsRepository.findByDeletedFalseOrderByName();
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
        Map<String,String> diff = ServiceUtils.getDiff(itemFromRequest, itemFromRepo);
        storeUpdates(itemFromRequest.getUid(), diff);
        BeanUtils.copyProperties(itemFromRepo,itemFromRequest, ServiceUtils.getNullPropertyNames(itemFromRequest) );
        itemsRepository.save(itemFromRequest);

    }

    private void storeUpdates(String itemId, Map<String, String> diff) {
        Date now = new Date(); List updates = new ArrayList();
        for(Map.Entry<String,String> entry : diff.entrySet()) {
            ItemUpdate update = new ItemUpdate();
            update.setItemId(itemId);
            update.setField(entry.getKey());
            update.setValue(entry.getValue());
            update.setDate(now);
            updates.add(update);
        }
        itemUpdatesRepository.save(updates);
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


        //Function.identity() will return the same item object
//
//        Map<String,Item> namesListMap = namesList.stream().collect(Collectors.toMap(Item::getUid, Function.identity()));
//
//        Map<String,Item> skuListMap = skuList.stream().collect(Collectors.toMap(Item::getUid, Function.identity()));
//
//        namesListMap.keySet().forEach(key -> {
//            if( skuListMap.get(key) ==null)
//                return;
//            else
//                skuListMap.remove(key);

//            namesListMap.merge(key, skuListMap.get(key),(name,sku) -> {
//                System.out.println("##############"  + name);
//                System.out.println("$$$$$$$$$$$$$$" + sku);
//                if(name.getId().equals(sku.getId()))
//                    return name;
//                else
//                    return sku;
//            } );
//        } );

        Set<Item> allItems  = new HashSet<>();
        allItems.addAll(namesList);
        allItems.addAll(skuList);



        List<XItem> xItemList = new ArrayList<>();
        for(Item item : allItems) {
            XItem xItem = mapper.map(item, XItem.class);
            xItemList.add(xItem);
        }
        return xItemList;
    }


    public void deleteItem(String itemId) {
        //we only do a soft delete of the item. so just mark the deleted flag = true
        Item itemFromRepo = itemsRepository.findOne(itemId);
        itemFromRepo.setDeleted(true);
        itemsRepository.save(itemFromRepo);
    }


}
