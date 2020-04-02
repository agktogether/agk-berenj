package com.agk.berenj.controller;

import com.agk.berenj.exception.BadRequestException;
import com.agk.berenj.exception.ExceptionType;
import com.agk.berenj.exception.ResourceNotFoundException;
import com.agk.berenj.model.Address;
import com.agk.berenj.model.User;
import com.agk.berenj.payload.ApiResponse;
import com.agk.berenj.payload.UserInfoResponse;
import com.agk.berenj.payload.UserInfo;
import com.agk.berenj.repository.AddressRepository;
import com.agk.berenj.repository.UserRepository;
import com.agk.berenj.security.CurrentUser;
import com.agk.berenj.security.UserPrincipal;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/userinfo")
public class UserInfoController {
    @Autowired
    private AddressRepository addressRepository;
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


    @PostMapping("/addnewaddress")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    private ResponseEntity d(@RequestBody Address address, @CurrentUser UserPrincipal currentUser) {
        String username = currentUser.getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Set<Address> addresses = user.getAddresses();
        for (Address address1 : addresses) {
            if (address1.getLat()==address.getLat() && address1.getLng()==address.getLng())
            {      ApiResponse apiResponse = new ApiResponse(false, "lat and lng aleady exists");
                apiResponse.setErrornumber(ExceptionType.ADDRESSLATLNGALEADYEXISTS);
                return new ResponseEntity(apiResponse,
                        HttpStatus.BAD_REQUEST);
            }
            if (address1.getName().equals(address.getName()))
            {      ApiResponse apiResponse = new ApiResponse(false, "name aleady exists");
                apiResponse.setErrornumber(ExceptionType.ADDRESSNAMEALEADYEXISTS);
                return new ResponseEntity(apiResponse,
                        HttpStatus.BAD_REQUEST);
            }
        }

        addresses.add(address);
        addressRepository.save(address);
        userRepository.save(user);
        UserInfo userInfo = new UserInfo(username, user.getName(), addresses);
        return new ResponseEntity<>( new UserInfoResponse().setUser(userInfo), HttpStatus.OK);
    }
}
