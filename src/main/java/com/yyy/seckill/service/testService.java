package com.yyy.seckill.service;

import com.yyy.seckill.dao.testDao;
import com.yyy.seckill.pojo.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class testService {
    @Autowired
    testDao testDao;
    public test GetById(int id){
        return testDao.GetByID(id);
    }

    @Transactional
    public boolean inserttest(){
        test u2 = new test();
        u2.setId(6);
        u2.setName("kkkk");
        testDao.inserttest(u2);
        test u1 = new test();
        u1.setId(1);
        u1.setName("haha");
        testDao.inserttest(u1);
        return true;
    }
}
