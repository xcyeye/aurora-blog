package xyz.xcye.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

/**
 * @author xcye
 * @description
 * @date 2022-12-19 22:02:13
 */

// @RequestMapping("/auth/login")
// @RestController
// public class TokenEndpointConfig extends TokenEndpoint {
//     @RequestMapping(value = "/oauth/token", method= RequestMethod.POST)
//     @Override
//     public ResponseEntity<OAuth2AccessToken> postAccessToken(@RequestBody Principal principal, @RequestBody Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
//         return super.postAccessToken(principal, parameters);
//     }
// }
