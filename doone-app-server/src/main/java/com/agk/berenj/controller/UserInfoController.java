package com.agk.berenj.controller;

import com.agk.berenj.exception.ResourceNotFoundException;
import com.agk.berenj.model.User;
import com.agk.berenj.payload.UserInfoResponse;
import com.agk.berenj.payload.UserInfo;
import com.agk.berenj.repository.UserRepository;
import com.agk.berenj.security.CurrentUser;
import com.agk.berenj.security.UserPrincipal;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")

    private UserInfoResponse d(@CurrentUser UserPrincipal currentUser) {
        String username = currentUser.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        UserInfo userInfo = new UserInfo(username, user.getName(), user.getAddresses());
        return new UserInfoResponse().setUser(userInfo);
    }
}
