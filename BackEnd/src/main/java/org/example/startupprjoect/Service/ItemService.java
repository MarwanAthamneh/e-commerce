package org.example.startupprjoect.Service;

import org.example.startupprjoect.model.Item;

import java.util.List;

public interface ItemService {
    public void addItem(Item item);
    public List<Item> getAllItems();
}
