package com.repository;

import com.entity.PurchasedCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedCategoriesRepository extends JpaRepository<PurchasedCategory, Integer> {
    PurchasedCategory findById(int id);
}
