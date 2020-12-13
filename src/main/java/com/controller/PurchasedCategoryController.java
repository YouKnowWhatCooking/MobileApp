package com.controller;

import com.entity.Bonus;
import com.entity.Category;
import com.entity.PurchasedCategory;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.PurchasedCategoryPayLoad;
import com.repository.CategoryRepository;
import com.repository.PurchasedCategoriesRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PurchasedCategoryController {

    @Autowired
    private PurchasedCategoriesRepository purchasedCategoriesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public List<PurchasedCategory> getAllPurchasedCategories() {
        return this.purchasedCategoriesRepository.findAll();
    }

    @GetMapping("/statistic/{date1}/{date2}")
    public HashMap<String, Integer> getStats(@PathVariable("date1") long date1, @PathVariable("date2") long date2) {
        List<PurchasedCategory> purchasedCategoryList = purchasedCategoriesRepository.findAllByPurchaseDateBetween(date1, date2);
        List<Category> categoryList = categoryRepository.findAllByPurchaseRequirmentTrue();
        HashMap<String, Integer> statList = new HashMap<>();
        for (Category category: categoryList){
            statList.put(category.getTitle(), 0);
        }
        for (PurchasedCategory purchasedCategory: purchasedCategoryList){
            System.out.println(purchasedCategory.getCategory().getTitle());
            statList.put(purchasedCategory.getCategory().getTitle(), statList.get(purchasedCategory.getCategory().getTitle())+1);
        }
        return statList;
    }



    @PostMapping
    public ResponseEntity<?> purchaseCategory(@RequestBody PurchasedCategoryPayLoad purchasedCategoryPayLoad, HttpServletRequest request) {
        PurchasedCategory purchasedCategory = new PurchasedCategory();
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = categoryRepository.findById(purchasedCategoryPayLoad.getCategoryID())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        purchasedCategory.setUser(user);
        purchasedCategory.setCategory(category);
        purchasedCategory.setPurchaseDate(System.currentTimeMillis());
        if (user.getBalance() >= category.getPrice()) {
            user.setBalance(user.getBalance() - category.getPrice());
            this.purchasedCategoriesRepository.save(purchasedCategory);
            this.userRepository.save(user);
            return ResponseEntity.ok().body("Вы успешно приобрели категорию " + category.getTitle());
        } else return ResponseEntity.badRequest().body("Недостаточный баланс");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Bonus> deletePurchasedCategory(@PathVariable("id") int ID) {
        PurchasedCategory existingPurchasedCategory = this.purchasedCategoriesRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("PurchasedCategory not found"));
        this.purchasedCategoriesRepository.delete(existingPurchasedCategory);
        return ResponseEntity.ok().build();
    }
}
