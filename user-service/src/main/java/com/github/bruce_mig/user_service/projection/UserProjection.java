package com.github.bruce_mig.user_service.projection;

import com.github.bruce_mig.commons.model.CardDetails;
import com.github.bruce_mig.commons.model.User;
import com.github.bruce_mig.commons.queries.GetUserPaymentDetailsQuery;
import com.github.bruce_mig.user_service.data.UserEntity;
import com.github.bruce_mig.user_service.data.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Slf4j
public class UserProjection {

    private final UserRepository userRepository;

    public UserProjection(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailsQuery query){
        String userId = query.getUserId();
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + userId));

        CardDetails cardDetails = null;
        if (userEntity.getCardDetails() != null) {
            cardDetails = CardDetails.builder()
                    .name(userEntity.getCardDetails().getName())
                    .cardNumber(userEntity.getCardDetails().getCardNumber())
                    .validUntilMonth(userEntity.getCardDetails().getValidUntilMonth())
                    .validUntilYear(userEntity.getCardDetails().getValidUntilYear())
                    .cvv(userEntity.getCardDetails().getCvv())
                    .build();
        }
        User user = User.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .cardDetails(cardDetails)
                .build();
        log.info("Handled GetUserPaymentDetailsQuery for user with id: {}", userId );
        return user;
    }
}
