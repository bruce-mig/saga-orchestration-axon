package com.github.bruce_mig.user_service.projection;

import com.github.bruce_mig.commons.model.CardDetails;
import com.github.bruce_mig.commons.model.User;
import com.github.bruce_mig.commons.queries.GetUserPaymentDetailsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query){
        // todo: query user details from actual database
        CardDetails cardDetails = CardDetails.builder()
                .name("Bruce Migeri")
                .validUntilMonth(8)
                .validUntilYear(2027)
                .cardNumber("123456789")
                .cvv(111)
                .build();
        return User.builder()
                .userId(query.getUserId())
                .firstName("Bruce")
                .lastName("Migeri")
                .cardDetails(cardDetails)
                .build();
    }
}
