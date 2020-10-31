package com.controller;

import com.entity.Category;
import com.payload.CategoryPayLoad;
import com.repository.CategoryRepository;
import com.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<Category> getAllCategories() {
        return this.categoryRepository.findAll();
    }


    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable("id") int ID) {
        return this.categoryRepository.findById(ID);
    }


    @PostMapping
    public Category createCategory(@RequestBody CategoryPayLoad categoryPayLoad){
        Category newCategory = new Category(categoryPayLoad.getTitle(), categoryPayLoad.getPrice(), categoryPayLoad.isPurchaseRequirement(), userRepository.findByUsername(categoryPayLoad.getUsername()));
        return this.categoryRepository.save(newCategory);
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody CategoryPayLoad categoryPayLoad, @PathVariable("id") int ID){
        Category existingCategory = this.categoryRepository.findById(ID);
        existingCategory.setTitle(categoryPayLoad.getTitle());
        existingCategory.setPrice(categoryPayLoad.getPrice());
        existingCategory.setPurchaseRequirment(categoryPayLoad.isPurchaseRequirement());
        return this.categoryRepository.save(existingCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") int ID) {
        Category existingCategory = this.categoryRepository.findById(ID);
        this.categoryRepository.delete(existingCategory);
        return ResponseEntity.ok().build();
    }

}
