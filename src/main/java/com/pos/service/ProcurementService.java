package com.pos.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pos.model.Item;
import com.pos.model.ItemPurchaseHistory;
import com.pos.model.ProcurementItem;
import com.pos.repository.ItemPurchaseHistoryRepository;
import com.pos.repository.ItemsRepository;
import org.dozer.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pos.model.Procurement;
import com.pos.pojos.XProcurement;
import com.pos.repository.ProcurementsRepository;

/**
 * Created by rajithar on 13/1/18.
 */
@Service
public class ProcurementService {

  @Autowired
  private ProcurementsRepository procurementsRepository;

  @Autowired
  private ItemsRepository itemsRepository;

  @Autowired
  private Mapper mapper;

  @Autowired
  private ItemPurchaseHistoryRepository itemPurchaseHistoryRepository;

  public Map<Date, Long> getAllProcurementIds() {
    return procurementsRepository.findAllProcurementIds();
  }

  public XProcurement fetchProcurement(Long procurementId) {
    Procurement procurement = procurementsRepository.findOne(procurementId);
    return mapper.map(procurement, XProcurement.class);
  }

  public void editProcurement(XProcurement xProcurement){
    Procurement procurementFromRequest = mapper.map(xProcurement, Procurement.class);
    Procurement procurementFromRepo = procurementsRepository.findOne(procurementFromRequest.getProcurementId());
    BeanUtils.copyProperties(procurementFromRepo,procurementFromRequest, ServiceUtils.getNullPropertyNames(procurementFromRequest) );
    procurementsRepository.save(procurementFromRequest);
  }

  public void addProcurement(XProcurement xProcurement){
    Procurement procurement = mapper.map(xProcurement, Procurement.class);
    procurementsRepository.save(procurement);
    updateItemPurchaseHistory(procurement);
    updateItemsWithQuantity(procurement);
  }

  private void updateItemsWithQuantity(Procurement procurement) {

    for(ProcurementItem procurementItem : procurement.getProcurementItems()){
      Item itemInRepo = itemsRepository.findOne(procurementItem.getItem().getUid());
      itemInRepo.setQuantity(itemInRepo.getQuantity() + procurementItem.getQuantityPurchased());
      itemsRepository.save(itemInRepo);
    }
  }

  private void updateItemPurchaseHistory(Procurement procurement) {

    List<ItemPurchaseHistory> purchaseItems = new ArrayList<>();LocalDate now = LocalDate.now();
    for(ProcurementItem procurementItem : procurement.getProcurementItems()){
      ItemPurchaseHistory itemPurchaseHistory = new ItemPurchaseHistory();
      itemPurchaseHistory.setItemId(procurementItem.getItem().getUid());
      itemPurchaseHistory.setUnits(procurementItem.getItem().getUnits());
      itemPurchaseHistory.setPrice(procurementItem.getItemCostPrice());
      itemPurchaseHistory.setQuantity(procurementItem.getQuantityPurchased());
      itemPurchaseHistory.setDate(now);
      purchaseItems.add(itemPurchaseHistory);
    }

    itemPurchaseHistoryRepository.save(purchaseItems);

  }

  /*
* @param date : date
* @return : Map of date to Procurement Id
* */
  public Map<Date, Long> getProcurementDateAndIdMapping(String date) {
    return procurementsRepository.findDateIdBySearchPattern(date);
  }
}
