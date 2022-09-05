package com.example.backendproject.userRegistration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/registration") //path
@AllArgsConstructor

public class userRegistrationController {

    private final RegistrationService registrationService;

    //annotate to get POST Request
    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
    return registrationService.register(request);
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token")String token){
        return registrationService.confirmToken(token);
    }
}
