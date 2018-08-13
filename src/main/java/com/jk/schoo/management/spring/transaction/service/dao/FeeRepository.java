package com.jk.schoo.management.spring.transaction.service.dao;

import com.jk.schoo.management.spring.transaction.domain.Fee;
import com.jk.schoo.management.spring.transaction.domain.FeeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by jayamalk on 7/23/2018.
 */
@Repository
public interface FeeRepository extends JpaRepository<Fee, Long> {

    List<Fee> findByFeeType(FeeType feeType);
}
