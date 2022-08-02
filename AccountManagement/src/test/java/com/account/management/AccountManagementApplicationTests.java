package com.account.management;

import com.account.management.controllers.AccountManagementController;
import com.account.management.entities.AccountDao;
import com.account.management.repositories.AccountRepository;
import com.account.management.util.Util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;

//@SpringBootTest
@WebMvcTest(AccountManagementController.class)
class AccountManagementApplicationTests {

//	@Test
//	void contextLoads() {
//	}
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper mapper;
    
    @MockBean
    AccountRepository accountRepo;
    
@Test
public void getListAccount() throws Exception {
    try
    {
            AccountDao req = new AccountDao();          
            req.setAccountName("Test Tester");
            req.setAccountNumber("2213572348");
            req.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            req.setPhone("08012345678");
            accountRepo.save(req);
            List<AccountDao> records = new ArrayList<AccountDao>();
            records.add(req);
            
    Mockito.when(accountRepo.findAll()).thenReturn(records);
    
    List<AccountDao> val = accountRepo.findByAccountNumber(Sort.by(Sort.Direction.DESC, "createdAt"), "2213572348");
    Assert.assertEquals("Test Tester", val.stream().filter(p->p.getAccountNumber().equals("2213572348")).findFirst().get().getAccountName());
    Assert.assertEquals("08012345678", val.stream().filter(p->p.getAccountNumber().equals("2213572348")).findFirst().get().getPhone());

    Mockito.verify(accountRepo).findByAccountNumber(Sort.by(Sort.Direction.DESC, "createdAt"), "2213572348");
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    

}

}
