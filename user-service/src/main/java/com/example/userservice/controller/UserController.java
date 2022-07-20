package com.example.userservice.controller;

import com.example.common.model.User;
import com.example.common.query.GetUserPaymentDetailQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private transient QueryGateway queryGateway;

    public UserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{userId}")
    public User getUserPaymentDetails(@PathVariable String userId) {
        GetUserPaymentDetailQuery getUserPaymentDetailsQuery
                = new GetUserPaymentDetailQuery(userId);
        User user = null;
        try {
             user =
                    queryGateway.query(getUserPaymentDetailsQuery,
                            ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception e){
            System.out.println("aaaa");
        }


        return user;
    }
}
