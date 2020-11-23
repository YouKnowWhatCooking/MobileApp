package com.controller;


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


    @GetMapping("/{id}/{count}")
    public ResponseEntity<?> getQuestionByCategory(@PathVariable("id") int ID, @PathVariable("count") int count) {
        Random random = new Random();
        List<Question> randomQuestions = new ArrayList<>();
        List<Question> list = questionRepository.findByCategory(categoryRepository.findById(ID));
        for (int i = 0; i < count; i++){
            int randomIndex = random.nextInt(list.size());
            randomQuestions.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return ResponseEntity.ok(randomQuestions);
    }


    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody QuestionPayLoad questionPayLoad){
        Question newQuestion = new Question(questionPayLoad.getText(), categoryRepository.findById(questionPayLoad.getCategoryID()));
        this.questionRepository.save(newQuestion);
        return ResponseEntity.ok("Success");
    }

    @PutMapping()
    public ResponseEntity<?> updateQuestion(@RequestBody QuestionPayLoad questionPayLoad){
        Question existingQuestion = this.questionRepository.findById(questionPayLoad.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id :" + questionPayLoad.getId()));
        existingQuestion.setText(questionPayLoad.getText());
        existingQuestion.setCategory(categoryRepository.findById(questionPayLoad.getCategoryID()));
        this.questionRepository.save(existingQuestion);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable("id") int ID) {
        Question existingQuestion = this.questionRepository.findById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found with id :" + ID));
        this.questionRepository.delete(existingQuestion);
        return ResponseEntity.ok("Success");
    }
}
