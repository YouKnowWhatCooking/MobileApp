package com.repository;

import com.entity.Category;
import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository  extends JpaRepository<Category, Integer> {
    Optional<Category> findById(int ID);
    List<Category> findAllByPurchaseRequirmentTrue();
    List<Category> findAllByUser(User user);
}
