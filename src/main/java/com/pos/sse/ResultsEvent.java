package com.pos.sse;

import com.pos.model.Item;
import com.pos.pojos.XItem;

import java.util.List;

/**
 * Created by rrampall on 20/01/18.
 */

public class ResultsEvent extends AbstractApplicationEvent{

        private static final long serialVersionUID = 2831716832308882027L;
        protected List<XItem> items;

        public ResultsEvent(Object source, String eventId, List<XItem> items) {
            super(source, eventId);
            this.items = items;
        }

        public List<XItem> getItems() {
            return items;
        }

}

