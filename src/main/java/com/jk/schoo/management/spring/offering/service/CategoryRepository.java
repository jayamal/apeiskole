package com.jk.schoo.management.spring.offering.service;

import com.jk.schoo.management.spring.offering.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jayamalk on 9/5/2018.
 */
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
