package com.github.bruce_mig.user_service.controller;

import com.github.bruce_mig.commons.model.User;
import com.github.bruce_mig.commons.queries.GetUserPaymentDetailsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private transient QueryGateway queryGateway;


    @GetMapping("/{userId}")
    public User getUserPaymentDetails(@PathVariable String userId){
        GetUserPaymentDetailsQuery getUserPaymentDetailsQuery = new GetUserPaymentDetailsQuery(userId);
        User user = queryGateway.query(getUserPaymentDetailsQuery,
                ResponseTypes.instanceOf(User.class))
                .join();
        return user;
    }
}
