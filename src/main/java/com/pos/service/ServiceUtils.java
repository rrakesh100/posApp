package com.pos.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Created by rajithar on 14/1/18.
 */
public class ServiceUtils {

  public static String[] getNullPropertyNames (Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    List<String> emptyNames = new ArrayList<>();
    for(java.beans.PropertyDescriptor pd : pds) {
      //check if value of this property is null then add it to the collection
      Object srcValue = src.getPropertyValue(pd.getName());
      if (srcValue != null) emptyNames.add(pd.getName());
    }
    String[] result = new String[emptyNames.size()];
    return emptyNames.toArray(result);
  }


  public static Map<String,String> getDiff(Object source, Object target) {
    final BeanWrapper src = new BeanWrapperImpl(source);
    final BeanWrapper tgt = new BeanWrapperImpl(target);

    Map<String,String> returnMap = new HashMap();

    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

    for(java.beans.PropertyDescriptor pd : pds) {
      //check if the values are different in src and target
      Object srcValue = src.getPropertyValue(pd.getName());
      Object tgtValue = tgt.getPropertyValue(pd.getName());

      if (srcValue != null && !srcValue.equals(tgtValue))
          returnMap.put(pd.getName(),  srcValue.toString());
    }
    return returnMap;
  }
}
