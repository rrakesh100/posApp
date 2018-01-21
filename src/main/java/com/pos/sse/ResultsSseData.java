package com.pos.sse;

import com.pos.model.Item;
import com.pos.pojos.XItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by rrampall on 20/01/18.
 */

public class ResultsSseData extends SseData{

        private List<XItem> processedItems = new ArrayList<>();
        private int lastIndex;

        public ResultsSseData(String eventId) {
            super();
            setEventId(eventId);
            setStarted(new Date());
            this.setLastIndex(-1);
        }


        public List<XItem> getProcessedItems() {
            return processedItems;
        }

        public void setProcessedItems(List<XItem> processedItems) {
            this.processedItems = processedItems;
        }


        public int getLastIndex() {
            return lastIndex;
        }


        public void setLastIndex(int lastIndex) {
            this.lastIndex = lastIndex;
        }


    }
