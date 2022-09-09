package com.stapelok.stapelok.service;

import com.stapelok.stapelok.models.User;
import com.stapelok.stapelok.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserServices {
    @Autowired
    UserRepository userRepository;

    public  void  updateResetPasswordToken(String email, String token){
        Optional<User> user=userRepository.findByEmail(email);
        if(!(user.isEmpty())){
            user.get().setResetPasswordToken(token);
            userRepository.save(user.get());
        }else{
            System.out.println("Could not find any customer with the email "+email);
        }
    }

    public  User getByResetPasswordToken(String token){
        return  userRepository.findByResetPasswordToken(token);
    }

    public void updatePassword(User user, String newPassword){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encoderPassword=passwordEncoder.encode(newPassword);
        user.setResetPasswordToken(null);
        userRepository.save(user);
    }
}
