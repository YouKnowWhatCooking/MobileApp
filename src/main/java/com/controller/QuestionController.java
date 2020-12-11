package com.controller;


import com.entity.Category;
import com.entity.Question;
import com.exception.ResourceNotFoundException;
import com.payload.QuestionPayLoad;
import com.repository.CategoryRepository;
import com.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("api/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public ResponseEntity<?> getAllQuestions() {
        List<Question> listQuestion = questionRepository.findAll();
        return ResponseEntity.ok(listQuestion);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getQuestionByCategory(@PathVariable("id") int ID) {
        Random random = new Random();
        List<Question> randomQuestions = new ArrayList<>();
        Category category = categoryRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        List<Question> list = questionRepository.findByCategory(category);
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(list.size());
            randomQuestions.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return ResponseEntity.ok(randomQuestions);
    }


    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody QuestionPayLoad questionPayLoad) {
        Category category = categoryRepository.findById(questionPayLoad.getCategoryID())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        Question newQuestion = new Question(questionPayLoad.getText(), category);
        this.questionRepository.save(newQuestion);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionPayLoad questionPayLoad) {
        Question existingQuestion = this.questionRepository.findById(questionPayLoad.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id :" + questionPayLoad.getId()));
        Category category = categoryRepository.findById(questionPayLoad.getCategoryID())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingQuestion.setText(questionPayLoad.getText());
        existingQuestion.setCategory(category);
        this.questionRepository.save(existingQuestion);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") int ID) {
        Question existingQuestion = this.questionRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id :" + ID));
        this.questionRepository.delete(existingQuestion);
        return ResponseEntity.ok().build();
    }
}
