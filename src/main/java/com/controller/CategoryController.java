package com.controller;

import com.entity.Category;
import com.entity.Role;
import com.entity.User;
import com.payload.CategoryPayLoad;
import com.repository.CategoryRepository;
import com.repository.RoleRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    @GetMapping("/admin")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return ResponseEntity.ok(categoryList);
    }


    @GetMapping("/user")
    public ResponseEntity<?> getCategoriesToPlay(HttpServletRequest request) {
        User user = userRepository.findByUsername(request.getRemoteUser());
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> resultList = new ArrayList<>();
        for (Category category: categoryList) {
            if(category.getUser().getRole().contains((roleRepository.findById(2))) || category.getUser().getUsername().equals(user.getUsername())){
                resultList.add(category);
            }
        }
        return ResponseEntity.ok(resultList);
    }

    @GetMapping
    public ResponseEntity<?> getCategoriesForUn() {
        List<Category> categoryList = categoryRepository.findAll();
        List<Category> resultList = new ArrayList<>();
        for (Category category: categoryList) {
            if(category.getUser().getRole().contains((roleRepository.findById(2)))){
                resultList.add(category);
            }
        }
        return ResponseEntity.ok(resultList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") int ID) {
        Category category = categoryRepository.findById(ID);
        return ResponseEntity.ok(category);
    }


    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryPayLoad categoryPayLoad, HttpServletRequest request){
        User user  = userRepository.findByUsername(request.getRemoteUser());
        Category newCategory = new Category(categoryPayLoad.getTitle(), categoryPayLoad.getPrice(), categoryPayLoad.isPurchaseRequirement(), user);
        this.categoryRepository.save(newCategory);
        return ResponseEntity.ok("Success");
    }

    @PutMapping
    public ResponseEntity<?> updateCategory(@RequestBody CategoryPayLoad categoryPayLoad){
        Category existingCategory = this.categoryRepository.findById(categoryPayLoad.getId());
        existingCategory.setTitle(categoryPayLoad.getTitle());
        existingCategory.setPrice(categoryPayLoad.getPrice());
        existingCategory.setPurchaseRequirment(categoryPayLoad.isPurchaseRequirement());
        this.categoryRepository.save(existingCategory);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id") int ID) {
        Category existingCategory = this.categoryRepository.findById(ID);
        this.categoryRepository.delete(existingCategory);
        return ResponseEntity.ok("Success");
    }

}
