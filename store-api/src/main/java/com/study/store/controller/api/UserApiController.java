package com.study.store.controller.api;

import com.study.store.controller.CrudController;
import com.study.store.model.entity.User;
import com.study.store.model.network.Header;
import com.study.store.model.network.request.UserApiRequest;
import com.study.store.model.network.response.UserApiResponse;
import com.study.store.model.network.response.UserJwtResponse;
import com.study.store.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserApiController extends CrudController<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private UserApiLogicService userApiLogicService;
//
//    @PostConstruct
//    public void init(){
//        this.baseService = userApiLogicService;
//    }

    @PostMapping("/login")
    public Header<UserJwtResponse> userLogin(@RequestBody Header<UserApiRequest> request){
        String account = request.getData().getAccount();
        String password = request.getData().getPassword();
        System.out.println(account + " " + password);
        return userApiLogicService.login(account,password);
    }

}
