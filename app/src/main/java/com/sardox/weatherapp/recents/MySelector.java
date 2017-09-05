package com.sardox.weatherapp.recents;

import com.sardox.weatherapp.model.Providers.WeatherProvider.cache.MyCacheKey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by sardox on 9/4/2017.
 */

public class MySelector {
    private HashSet<MyCacheKey> selected_items;
    private SelectorCallback selectorCallback;

    public MySelector() {
        selected_items = new HashSet<>();
    }
    public void setSelectedItems(List<MyCacheKey> items){
        this.selected_items.addAll(items);
    }


    public void wasSelected(MyCacheKey key) {
        if (selected_items.contains(key)) selected_items.remove(key);
        else selected_items.add(key);

        if (selected_items.isEmpty() && selectorCallback!=null) selectorCallback.onSelectorEmpty();
    }

    public boolean isStillSelecting(){
        return !selected_items.isEmpty();
    }

    public boolean isSelected(MyCacheKey key){
        return selected_items.contains(key);
    }
    public ArrayList<MyCacheKey> getSelectedItems(){
        return new ArrayList<>(selected_items);
    }

    public void setSelectorCallback(SelectorCallback selectorCallback) {
        this.selectorCallback = selectorCallback;
    }


    public interface SelectorCallback{
       void onSelectorEmpty();
    }
}
