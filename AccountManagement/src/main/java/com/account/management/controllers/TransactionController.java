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
@Component(value = "transactionController")
@ELBeanName(value = "transactionController")
@Join(path = "/", to = "/index.xhtml")
public class TransactionController {
        
    @Autowired private TransactionRepository transactionRepo;
    @Autowired private AccountRepository accountRepo;
    
    private TransactionsDao tns = new TransactionsDao();
    private TransactionsDao tnsw = new TransactionsDao();
    private List<TransactionsDto> listTns = new ArrayList<TransactionsDto>();

    private String searchData;

    public TransactionsDao getTnsw() {
        return tnsw;
    }

    public void setTnsw(TransactionsDao tnsw) {
        this.tnsw = tnsw;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    public List<TransactionsDto> getListTns() {
        return listTns;
    }

    public void setListTns(List<TransactionsDto> listTns) {
        this.listTns = listTns;
    }

    public TransactionsDao getTns() {
        return tns;
    }

    public void setTns(TransactionsDao tns) {
        this.tns = tns;
    }

    
    
    @PostConstruct
    public void init() {
      tns = new TransactionsDao();
      listTns = new ArrayList<TransactionsDto>();
    }
        
    public void doTransaction(String ttype, TransactionsDao tnsd) {
            System.out.println("dshcxbkjbnumber" + tnsd.getAmount());
        try
        { 
        if(tnsd.getAccountNumber()==null || tnsd.getAccountNumber().isEmpty() || validateAccount(tnsd.getAccountNumber())==false)
        {
            new Util().DisplayError("Valid Account Number required");
        }
        else if(tnsd.getAmount()<=0)
        {
            System.out.println("dshcxbkjbamt");
            new Util().DisplayError("Valid Amount required");
        }
        else
        {
        System.out.println("dshcxbkjb");
        tnsd.setReference(new Util().generateReference("TR"));
        tnsd.setTransactionType(ttype);
                
        tnsd.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        transactionRepo.save(tnsd);
        tns = new TransactionsDao();
        tnsw = new TransactionsDao();
        }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            new Util().DisplayError("Unable to " + tns.getTransactionType().toLowerCase() + " account");
        }
        
        listTns = listTransactions("");
        
    }
    
    public void doTransactiond() {
        
       doTransaction("CREDIT", tns); 
    }
    
    public void doTransactionw() {
        
       doTransaction("DEBIT", tnsw); 
    }
    
    public List<TransactionsDto> listTransactions(String accountNumber) {

        listTns = new ArrayList<TransactionsDto>();
        List<TransactionsDao> tns =new ArrayList<TransactionsDao>();
        try
        {
        if(accountNumber!=null && !accountNumber.isEmpty())
        {
        tns = transactionRepo.findByAccountNumber(Sort.by(Sort.Direction.DESC, "accountNumber"), accountNumber);
        }
        {
        tns = transactionRepo.findAll(Sort.by(Sort.Direction.DESC, "accountNumber"));
        }
        
        System.out.println("tns.size(): " + tns.size());
        
        if(tns.size() > 0)
        {
        List<TransactionsDto> rep = new Util().convertTransactionList(tns);
        listTns =rep.stream().sorted(Comparator.comparing(TransactionsDto::getCreatedAt)).collect(Collectors.toList());
        }
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            new Util().DisplayError("Error loading data");
        }
        
        return listTns;
        
    }
    
    public boolean validateAccount(String acctNo)
    { 
        System.out.println("acctNo: " + acctNo);
        try
        {
        if(accountRepo.findByAccountNumber(Sort.by(Sort.Direction.DESC, "createdAt"), acctNo).size() > 0)
        {
         return true;   
        }
        else
        {
         return false;
        }
        
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            
            return false;
        }
        
    }
    
    
}
