package com.example.patientmvc;

import com.example.patientmvc.entities.Patient;
import com.example.patientmvc.repositories.PatientRepository;
import com.example.patientmvc.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class PatientMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientMvcApplication.class, args);
    }

    //@Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(
                    new Patient(null,"Hassan", new Date(),false,12));
            patientRepository.save(
                    new Patient(null,"Ahmed", new Date(),true,35));
            patientRepository.save(
                    new Patient(null,"Hanae", new Date(),true,56));
            patientRepository.save(
                    new Patient(null,"Imane", new Date(),false,85));

            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(JdbcUserDetailsManager jdbcUserDetailsManager) {
        PasswordEncoder passwordEncoder = passwordEncoder();
        return args -> {
            UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user11");
            if(u1 == null)
                jdbcUserDetailsManager.createUser(User
                    .withUsername("user11")
                    .password(passwordEncoder.encode("123456"))
                    .roles("USER")
                    .build());
            UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user22");
            if(u2 == null)
                jdbcUserDetailsManager.createUser(User
                    .withUsername("user22")
                    .password(passwordEncoder.encode("123456"))
                    .roles("USER")
                    .build());
            UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("admin2");
            if(u3 == null)
                jdbcUserDetailsManager.createUser(User
                    .withUsername("admin2")
                    .password(passwordEncoder.encode("123456"))
                    .roles("USER", "ADMIN")
                    .build());
        };
    }

    //@Bean
    CommandLineRunner commandLineRunner(AccountService accountService) {
        return args -> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");

            accountService.addNewUser("user1", "user1@gmail.com","123456","123456");
            accountService.addNewUser("user2", "user2@gmail.com","123456","123456");
            accountService.addNewUser("admin", "admin@gmail.com","123456","123456");

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
