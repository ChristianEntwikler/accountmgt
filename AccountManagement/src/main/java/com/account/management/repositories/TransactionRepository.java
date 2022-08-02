/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.account.management.repositories;

import com.account.management.entities.TransactionsDao;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author CE155742
 */
public interface TransactionRepository  extends JpaRepository<TransactionsDao, Long>{
    
    public List<TransactionsDao> findByAccountNumber(Sort sort, String field);
}
