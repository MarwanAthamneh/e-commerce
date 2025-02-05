package org.example.startupprjoect.Service.ServiceImpl;

import org.example.startupprjoect.Repository.ItemRespository;
import org.example.startupprjoect.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl  {

    @Autowired
   private ItemRespository itemRespository;

    public void addItem(Item item) {
        if (itemRespository.findByName(item.getName()).isPresent()) {
            throw new RuntimeException("Item is already there");
        }

        itemRespository.save(item);
    }

    public List<Item> getAllItems() {

        return itemRespository.findAll();
    }
}
