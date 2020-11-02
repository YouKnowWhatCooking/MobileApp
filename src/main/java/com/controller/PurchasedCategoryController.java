package com.controller;

import com.entity.Bonus;
import com.entity.PurchasedCategory;
import com.payload.PurchasedCategoryPayLoad;
import com.repository.CategoryRepository;
import com.repository.PurchasedCategoriesRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchasedCategoryController {

    @Autowired
    private PurchasedCategoriesRepository purchasedCategoriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public List<PurchasedCategory> getAllPurchasedCategories(){
        return this.purchasedCategoriesRepository.findAll();
    }


    @PostMapping
    public PurchasedCategory purchaseCategory(@RequestBody PurchasedCategoryPayLoad purchasedCategoryPayLoad){
        PurchasedCategory purchasedCategory = new PurchasedCategory();
        purchasedCategory.setUser(userRepository.findByUsername(purchasedCategory.getUser().getUsername()));
        purchasedCategory.setCategory(categoryRepository.findById(purchasedCategoryPayLoad.getCategoryID()));
        if(userRepository.findByUsername(purchasedCategoryPayLoad.getUsername()).getBalance() > categoryRepository.findById(purchasedCategory.getId()).getPrice()) {
            return purchasedCategoriesRepository.save(purchasedCategory);
        } else return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bonus> deletePurchasedCategory(@PathVariable("id") int ID) {
        PurchasedCategory existingPurchasedCategory = this.purchasedCategoriesRepository.findById(ID);
        this.purchasedCategoriesRepository.delete(existingPurchasedCategory);
        return ResponseEntity.ok().build();
    }
}
