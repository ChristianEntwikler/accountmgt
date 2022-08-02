/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.account.management.util;

import com.account.management.dto.TransactionsDto;
import com.account.management.entities.TransactionsDao;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author CE155742
 */
public class Util {
    
            public void DisplayError(String message) {
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: " + message, message));
        }

        public void DisplayInfo(String message) {
               FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info: " + message, message));
        }
        
        public String generateAccount()
    {
        String fcinum = "";
        try
        {
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyMddss");
                Timestamp timestamp3 = new Timestamp(System.currentTimeMillis());
                String timestampz3=sdf3.format(timestamp3);               
                //long number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                long number = (long) Math.floor(Math.random() * 9_00L);
            fcinum=timestampz3 + number;
            if(fcinum.length()<10)
            {
              int ct = 10 - fcinum.length();
              
              for(int i =0; i<ct; i++)
              {
                  fcinum = fcinum + (10 % i);
              }
            }
             System.out.println(fcinum);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
             return fcinum;
    }
        
     public String generateReference(String refCode)
    {
        String fcinum = "";
        try
        {
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyMMddHHmmss");
                Timestamp timestamp3 = new Timestamp(System.currentTimeMillis());
                String timestampz3=sdf3.format(timestamp3);               
                long number = (long) Math.floor(Math.random() * 9_00_00L) + 1_00_00_00L;
            fcinum=refCode + timestampz3 + number;
             System.out.println(fcinum);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
             return fcinum;
    }
     
   
   public List<TransactionsDto> convertTransactionList(List<TransactionsDao> req)
   {
       List<TransactionsDto> resps =new ArrayList<TransactionsDto>();
       float bal = 0;
       String acctno = "";
       if(req.size() > 0)
       {
          for(TransactionsDao tns : req)
          {
              TransactionsDto resp = new TransactionsDto();
              
              resp.setAccountNumber(tns.getAccountNumber());
              resp.setAmount(tns.getAmount());
              resp.setCreatedAt(tns.getCreatedAt());
              resp.setReference(tns.getReference());
              resp.setTransactionType(tns.getTransactionType());
              
              if(!acctno.equals(tns.getAccountNumber()))
              {
                  acctno = tns.getAccountNumber();
                  bal = 0;
              }
              
              if(acctno.equals(tns.getAccountNumber()) && tns.getTransactionType().equals("CREDIT"))
              {
                  bal = bal + tns.getAmount();
                  resp.setBalance(bal);
              }
              else if(acctno.equals(tns.getAccountNumber()) && tns.getTransactionType().equals("DEBIT"))
              {
                  bal = bal - tns.getAmount();
                  resp.setBalance(bal);
              }
              
              resps.add(resp);
          }
       }
       
       return resps;
   }
        
        
        
}
