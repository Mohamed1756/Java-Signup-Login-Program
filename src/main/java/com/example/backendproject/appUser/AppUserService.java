package com.example.backendproject.appUser;


import com.example.backendproject.userRegistration.token.ConfirmationToken;
import com.example.backendproject.userRegistration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor // check def
public class AppUserService implements UserDetailsService {
    // find users when they login

    //save
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;


    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG,email)));
    }

    //confirmation mail
    public String signUpUser(AppUser appUser){
        //check if user exist
      boolean userExist = appUserRepository.findByEmail(appUser.getEmail())
                .isPresent();

      if(userExist){
          throw new IllegalStateException("Email already in use");
      }

      String encodePassword = bCryptPasswordEncoder.encode(appUser.getPassword());

      //sets password & saves user in DB
      appUser.setPassword(encodePassword);
      appUserRepository.save(appUser);

      //send confirmation token
        //1. initialize token uuid, 2.create object of token class, pass into parameters
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15), //15 min deadline to confirm
                appUser
        );
        //save token
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        //TO DO: SEND EMAIL

      return token;
    }


    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
