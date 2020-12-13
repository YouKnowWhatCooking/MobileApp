package com.controller;

import com.entity.Category;
import com.entity.PurchasedCategory;
import com.entity.Role;
import com.entity.User;
import com.exception.ResourceNotFoundException;
import com.payload.CategoryPayLoad;
import com.repository.CategoryRepository;
import com.repository.PurchasedCategoriesRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PurchasedCategoriesRepository purchasedCategoriesRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    //Для админа
    @GetMapping("/admin")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }



    //Для своих
    @GetMapping("/custom")
    public ResponseEntity<?> getUserCategories(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Category> categoryList = categoryRepository.findAllByUser(user);
        return ResponseEntity.ok(categoryList);
    }

    //Для купленных категорий
    @GetMapping("/user")
    public ResponseEntity<?> getPurchasedCategories(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<PurchasedCategory> purchasedCategoryList = purchasedCategoriesRepository.findAllByUser(user);
        List<Category> categoryList = new ArrayList<>();
        for (PurchasedCategory purchasedCategory : purchasedCategoryList) {
            categoryList.add(purchasedCategory.getCategory());
        }
        return ResponseEntity.ok(categoryList);
    }

    //Для некупленных категорий
    @GetMapping("/available")
    public ResponseEntity<?> getUnpurchased(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<PurchasedCategory> purchasedCategoryList = purchasedCategoriesRepository.findAllByUser(user);
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> resultList = new ArrayList<>();
        List<Category> categoryList1 = new ArrayList<>();
        for (PurchasedCategory purchasedCategory : purchasedCategoryList) {
            categoryList1.add(purchasedCategory.getCategory());
        }

        for (Category category : categoryList) {
            if (!categoryList1.contains(category) && category.isPurchaseRequirment()) {
                resultList.add(category);
            }
        }
        return ResponseEntity.ok(resultList);
    }

    //Все бесплатные
    @GetMapping("/free")
    public ResponseEntity<?> getFreeCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> resultList = new ArrayList<>();
        Role role2 = roleRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Set<Role> roles = new HashSet<>();
          roles.add(role2);

        for (Category category : categoryList) {
            if (category.getUser().getRole().containsAll(roles) && !category.isPurchaseRequirment()) {
                resultList.add(category);
            }
        }
        return ResponseEntity.ok(resultList);
    }

    //Все платные
    @GetMapping("/paid")
    public ResponseEntity<?> getPaidCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> resultList = new ArrayList<>();
        Role role2 = roleRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role2);

        for (Category category : categoryList) {
            if (category.getUser().getRole().containsAll(roles) && category.isPurchaseRequirment()) {
                resultList.add(category);
            }
        }
        return ResponseEntity.ok(resultList);
    }


    //Все, кроме авторских (для неавторизованного)
    @GetMapping
    public ResponseEntity<?> getCategoriesForUn() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> resultList = new ArrayList<>();
        Role role2 = roleRepository.findById(2)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role2);
        for (Category category : categoryList) {
            if (category.getUser().getRole().containsAll(roles)) {
                resultList.add(category);
            }
        }
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int ID) {
        Category category = categoryRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return ResponseEntity.ok(category);
    }


    @PostMapping("/admin")
    public ResponseEntity<?> createCategoryAdmin(@RequestBody CategoryPayLoad categoryPayLoad, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category newCategory = new Category(categoryPayLoad.getTitle(), categoryPayLoad.getPrice(), categoryPayLoad.isPurchaseRequirement(), user);
        this.categoryRepository.save(newCategory);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user")
    public ResponseEntity<?> createCategoryUser(@RequestBody CategoryPayLoad categoryPayLoad, HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category newCategory = new Category(categoryPayLoad.getTitle(), 0, false, user);
        this.categoryRepository.save(newCategory);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryPayLoad categoryPayLoad) {
        Category existingCategory = this.categoryRepository.findById(categoryPayLoad.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingCategory.setTitle(categoryPayLoad.getTitle());
        existingCategory.setPrice(categoryPayLoad.getPrice());
        existingCategory.setPurchaseRequirment(categoryPayLoad.isPurchaseRequirement());
        this.categoryRepository.save(existingCategory);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int ID) {
        Category existingCategory = this.categoryRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        this.categoryRepository.delete(existingCategory);
        return ResponseEntity.ok().build();
    }

}
