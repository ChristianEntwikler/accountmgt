/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.account.management.controllers;

import com.account.management.dto.TransactionsDto;
import com.account.management.entities.AccountDao;
import com.account.management.entities.TransactionsDao;
import com.account.management.repositories.AccountRepository;
import com.account.management.repositories.TransactionRepository;
import com.account.management.util.Util;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 *
 * @author CE155742
 */
@Scope(value = "session")
@Component(value = "accountManagementController")
@ELBeanName(value = "accountManagementController")
@Join(path = "/", to = "/index.xhtml")
public class AccountManagementController {
    
    @Value("${gen.acct.count}")
    private int genCount;
    
    @Autowired private AccountRepository accountRepo;
    
    private AccountDao acct = new AccountDao();
    private String searchData;

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public AccountDao getAcct() {
        return acct;
    }

    public void setAcct(AccountDao acct) {
        this.acct = acct;
    }
    
    @PostConstruct
    public void init() {
      acct = new AccountDao();
    }
    
    public void createAccount() {
        try
        { 
        acct.setAccountNumber(new Util().generateAccount());
        while(accountRepo.findByAccountNumber(Sort.by(Sort.Direction.DESC, "createdAt"), acct.getAccountNumber()).size() > 0)
        {
         acct.setAccountNumber(new Util().generateAccount()); 
        }
        acct.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        accountRepo.save(acct);
        acct = new AccountDao();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            new Util().DisplayError("Unable to create account");
        }
    }
    
    public void generateAccount() {
        
        int i =1;
        while(i<=genCount)
        {
            try
        {
           createAccount(); 
           i++;
           }
        catch(Exception ex)
        {
            ex.printStackTrace();
            new Util().DisplayError("Unable to generate account");
        }
        }
        
    }
    
    public List<AccountDao> listAccounts() {
        try
        {
        return accountRepo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            new Util().DisplayError("Error loading data");
            return new ArrayList<AccountDao>();
        }
                
    }
        
}
