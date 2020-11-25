package com.repository;

import com.entity.PurchasedCategory;
import com.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchasedCategoriesRepository extends JpaRepository<PurchasedCategory, Integer> {
    Optional<PurchasedCategory> findById(int id);
    List<PurchasedCategory> findByUser(User user);
}
