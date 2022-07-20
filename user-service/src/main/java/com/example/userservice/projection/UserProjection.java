package com.example.userservice.projection;

import com.example.common.model.CardDetails;
import com.example.common.model.User;
import com.example.common.query.GetUserPaymentDetailQuery;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class UserProjection  {

    @QueryHandler
    public User getUserPaymentDetail(GetUserPaymentDetailQuery getUserPaymentDetailQuery){
        log.info("User Methodu çalıştı");
        CardDetails cardDetail = new CardDetails();
        cardDetail.setCardNumber("1111-2222-3333-4444");
        cardDetail.setName("cengiz");
        cardDetail.setValidUntilMonth("2025");
        User user = new User();
        user.setUserId(getUserPaymentDetailQuery.getUserId());
        user.setCardDetails(cardDetail);
        user.setName("Cengiz HAN");

        return user;
    }
}
