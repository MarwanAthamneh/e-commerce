package org.example.startupprjoect.Controller;

import org.example.startupprjoect.Service.ServiceImpl.ItemServiceImpl;
import org.example.startupprjoect.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;

    @PostMapping("/add")

    public ResponseEntity<String> addItem(@RequestBody Item item){

    itemService.addItem(item);

        return ResponseEntity.ok("item added successfully!");
    }

    @GetMapping("/items")
    public List<Item> getAllItems(){


        return itemService.getAllItems();


    }
}
