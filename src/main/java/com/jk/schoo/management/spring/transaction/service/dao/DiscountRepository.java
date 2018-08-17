package com.jk.schoo.management.spring.transaction.service.dao;

import com.jk.schoo.management.spring.transaction.domain.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository  extends JpaRepository<Discount, Long> {
}
