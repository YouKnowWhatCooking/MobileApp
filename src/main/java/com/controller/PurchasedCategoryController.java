package com.controller;

import com.entity.Bonus;
import com.entity.Category;
import com.entity.PurchasedCategory;
import com.entity.User;
import com.payload.PurchasedCategoryPayLoad;
import com.repository.CategoryRepository;
import com.repository.PurchasedCategoriesRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> purchaseCategory(@RequestBody PurchasedCategoryPayLoad purchasedCategoryPayLoad, HttpServletRequest request){
        PurchasedCategory purchasedCategory = new PurchasedCategory();
        User user = userRepository.findByUsername(request.getRemoteUser());
        Category category = categoryRepository.findById(purchasedCategoryPayLoad.getCategoryID());
        purchasedCategory.setUser(user);
        purchasedCategory.setCategory(category);
        if(user.getBalance() >= category.getPrice()) {
            user.setBalance(user.getBalance() - category.getPrice());
            this.purchasedCategoriesRepository.save(purchasedCategory);
            this.userRepository.save(user);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bonus> deletePurchasedCategory(@PathVariable("id") int ID) {
        PurchasedCategory existingPurchasedCategory = this.purchasedCategoriesRepository.findById(ID);
        this.purchasedCategoriesRepository.delete(existingPurchasedCategory);
        return ResponseEntity.ok().build();
    }
}
