package com.study.store.service;

import com.study.store.component.JwtUtil;
import com.study.store.interfaces.CrudInterface;
import com.study.store.model.entity.User;
import com.study.store.model.enumclass.UserStatus;
import com.study.store.model.network.Header;
import com.study.store.model.network.request.UserApiRequest;
import com.study.store.model.network.response.UserApiResponse;
import com.study.store.model.network.response.UserJwtResponse;
import com.study.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service // 서비스로 동작
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = baseRepository.save(user);

        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        Optional<User> optional = baseRepository.findById(id);


        return optional
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        UserApiRequest userApiRequest = request.getData();

        Optional<User> optional = baseRepository.findById(userApiRequest.getId());
        return optional.map(user -> {
            user.setAccount(user.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;

        })
                .map(user -> baseRepository.save(user))
                .map(user -> response(user))
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    @Override
    public Header delete(Long id) {
        Optional<User> optional = baseRepository.findById(id);
        return optional.map(user -> {
            baseRepository.delete(user);
            return Header.OK();
        })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    public Header<UserJwtResponse> login(String account, String password){
        Optional<User> optional = userRepository.findByAccountAndPassword(account,password);
        return optional.map(user -> {
            JwtUtil jwtUtil = new JwtUtil();
            String token = jwtUtil.createToken(user.getId(),user.getAccount());
            return response(token);
        })
                .orElseGet(() -> Header.ERROR("데이터 없음"));
    }

    private Header<UserApiResponse> response(User user){
        UserApiResponse userApiResponse = UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();
        // Header + data

        return Header.OK(userApiResponse);
    }
    private Header<UserJwtResponse> response(String token){
        UserJwtResponse userJwtResponse = UserJwtResponse.builder()
                .token(token)
                .build();
        // Header + data

        return Header.OK(userJwtResponse);
    }
}
