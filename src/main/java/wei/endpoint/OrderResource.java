package wei.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        return new Order(orderId);
    }
}
