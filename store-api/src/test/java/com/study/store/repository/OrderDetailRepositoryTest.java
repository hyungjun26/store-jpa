package com.study.store.repository;

import com.study.store.model.entity.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("OrderDetailRepositoryTest")
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    @Rollback(value = false)
    public void create(){
//        OrderDetail orderDetail = new OrderDetail();
//
//        orderDetail.setCreatedAt(LocalDateTime.now());
        // orderDetail.setOrderGroupId(1L);
        //orderDetail.setItemId(1L);

//        OrderDetail newOrder = orderDetailRepository.save(orderDetail);
//
//        Assertions.assertNotNull(newOrder);
    }
}