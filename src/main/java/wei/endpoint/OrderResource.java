package wei.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @GetMapping("/orders/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        OAuth2AccessToken accessToken = oAuth2ClientContext.getAccessToken();
        LOGGER.info("Access token: {}", accessToken.getValue());
        LOGGER.info("Refresh Token: {}", accessToken.getRefreshToken());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken.getValue());
        HttpEntity<String> httpEntity = new HttpEntity<>("", httpHeaders);
        ResponseEntity<Order> responseEntity = new RestTemplate().exchange("http://localhost:8280/order-service/orders/{orderId}", HttpMethod.GET, httpEntity, Order.class, orderId);
        return responseEntity.getBody();
    }
}