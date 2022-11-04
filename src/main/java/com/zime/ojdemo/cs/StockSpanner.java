package com.zime.ojdemo.cs;

import java.util.ArrayList;
import java.util.List;

class StockSpanner {
List<Integer> zhi;
    public StockSpanner() {
      zhi=new ArrayList<>();
    }

    public int next(int price) {

    zhi.add(price);
    for(int i=0;i<zhi.size();i++){
        if(zhi.get(i)<price) return zhi.size()-i;
    }
    return 1;
    }
}
