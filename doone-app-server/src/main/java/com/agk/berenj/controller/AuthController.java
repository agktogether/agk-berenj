package com.agk.berenj.controller;

import com.agk.berenj.exception.ExceptionType;
import com.agk.berenj.model.CodeSending;
import com.agk.berenj.repository.CodeSendingRepository;
import com.agk.berenj.repository.RoleRepository;
import com.agk.berenj.repository.UserRepository;
import com.agk.berenj.exception.AppException;
import com.agk.berenj.model.Role;
import com.agk.berenj.model.RoleName;
import com.agk.berenj.model.User;
import com.agk.berenj.payload.ApiResponse;
import com.agk.berenj.payload.JwtAuthenticationResponse;
import com.agk.berenj.payload.LoginRequest;
import com.agk.berenj.payload.SignUpRequest;
import com.agk.berenj.security.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Created by rajeevkumarsingh on 02/08/17.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    CodeSendingRepository codeSendingRepository;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signupold")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        if (!StringUtils.isNumeric(username)) {
            ApiResponse apiResponse = new ApiResponse(false, "have a string instead of numbers");
            apiResponse.setErrornumber(ExceptionType.ISNOTNUMERIC);
            return new ResponseEntity(apiResponse,
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity(new ApiResponse(false, "phone number is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
//
//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), username, signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> getPhonenumberpart(@Valid @RequestBody SignUpRequest signUpRequest) {
        String username = signUpRequest.getUsername();
        if (!StringUtils.isNumeric(username)) {
            ApiResponse apiResponse = new ApiResponse(false, "have a string instead of numbers");
            apiResponse.setErrornumber(ExceptionType.ISNOTNUMERIC);
            return new ResponseEntity(apiResponse,
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(username)) {
            return new ResponseEntity(new ApiResponse(false, "phone number is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }
//
//        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }


        //sending code to that phone number
        boolean codesended = false;
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        CodeSending codeSending = new CodeSending(username, String.valueOf(randomPIN), CodeSending.NOTSENTYET);
        CodeSending save = codeSendingRepository.save(codeSending);

//        while (!codesended) {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Optional<CodeSending> byId = codeSendingRepository.findById(save.getId());
//            CodeSending codeSending1 = byId.get();
//            if (codeSending1.getStatus() == CodeSending.SENT) {
//                return new ResponseEntity(new ApiResponse(false, "sent"),
//                        HttpStatus.OK);
//            }
//        }
        return new ResponseEntity<>(save, HttpStatus.OK);
    }



    @PostMapping("/getsmsreqs")
    public ResponseEntity<?> getPhonenumberpart(){
        List<CodeSending> byStatus = codeSendingRepository.findByStatus(CodeSending.NOTSENTYET);
        return new ResponseEntity<>(byStatus, HttpStatus.OK);
    }


    //token here is for not recognizable by hackers
    @PostMapping("/changesmsreqstatus/dslkfasdlfjasdlfjldksjflksdjJSKALSJSLksdksdks/{id}")
    public ResponseEntity<?> getPhonenumberpart(@PathVariable Long id){
        Optional<CodeSending> byId = codeSendingRepository.findById(id);
        CodeSending codeSending = byId.get();
        codeSending.setStatus(CodeSending.SENT);
        codeSendingRepository.save(codeSending);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

    @PostMapping("/ackstatus/{id}")
    public ResponseEntity<?> f(@PathVariable Long id){
        Optional<CodeSending> byId = codeSendingRepository.findById(id);
        CodeSending codeSending = byId.get();
        return new ResponseEntity<>(codeSending.getStatus(), HttpStatus.OK);
    }


}
