package com.controller;


import com.entity.Question;
import com.exception.ResourceNotFoundException;
import com.payload.QuestionPayLoad;
import com.repository.CategoryRepository;
import com.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("api/questions")
public class QuestionController {


    private EntityManager entityManager;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping
    public List<Question> getAllQuestions() {
        return this.questionRepository.findAll();
    }


    @GetMapping("/{id}/{count}")
    public List<Question> getQuestionByCategory(@PathVariable("id") int ID, @PathVariable("count") int count) {
        Random random = new Random();
        List<Question> randomQuestions = new ArrayList<>();
        List<Question> list = questionRepository.findByCategory(categoryRepository.findById(ID));
        for (int i = 0; i < count; i++){
            int randomIndex = random.nextInt(list.size());
            randomQuestions.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return randomQuestions;
    }


    @PostMapping
    public Question createQuestion(@RequestBody QuestionPayLoad questionPayLoad){
        Question newQuestion = new Question(questionPayLoad.getText(), categoryRepository.findById(questionPayLoad.getCategoryID()));
        return this.questionRepository.save(newQuestion);
    }

    @PutMapping("/{id}")
    public Question updateQuestion(@RequestBody QuestionPayLoad questionPayLoad, @PathVariable("id") int ID){
        Question existingQuestion = this.questionRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id :" + ID));
        existingQuestion.setText(questionPayLoad.getText());
        existingQuestion.setCategory(categoryRepository.findById(questionPayLoad.getCategoryID()));
        return this.questionRepository.save(existingQuestion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable("id") int ID) {
        Question existingQuestion = this.questionRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id :" + ID));
        this.questionRepository.delete(existingQuestion);
        return ResponseEntity.ok().build();
    }
}
