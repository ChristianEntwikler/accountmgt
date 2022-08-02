/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.account.management.repositories;

import com.account.management.entities.AccountDao;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author CE155742
 */
public interface AccountRepository  extends JpaRepository<AccountDao, Long>{
    
    public List<AccountDao> findByAccountNumber(Sort sort, String field);
}
