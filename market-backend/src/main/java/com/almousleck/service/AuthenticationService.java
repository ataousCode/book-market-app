package com.almousleck.service;

import com.almousleck.email.EmailService;
import com.almousleck.email.EmailTemplateName;
import com.almousleck.entites.Token;
import com.almousleck.entites.User;
import com.almousleck.jwt.JwtService;
import com.almousleck.repositories.RoleRepository;
import com.almousleck.repositories.TokenRepository;
import com.almousleck.repositories.UserRepository;
import com.almousleck.request.AuthenticationRequest;
import com.almousleck.request.RegistrationRequest;
import com.almousleck.response.AuthenticationResponse;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtService jwtService;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    public void  register(RegistrationRequest request) throws MessagingException {
        var userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        // send validation email
        sendValidationEmail(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                ));
        var claims = new HashMap<String, Object>();
        var user = ((User) authentication.getPrincipal());
        claims.put("email", user.getEmail());

        var jwt_token = jwtService.generateToken(claims, (User) authentication.getPrincipal());
        return AuthenticationResponse.builder()
                .token(jwt_token)
                .build();
    }

    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiresAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation failed token has expired, A new token has been send to your email.");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidateAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndActivateToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATION_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndActivateToken(User user) {
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(30))
                .user(user).build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String character = "0123456789";
        StringBuilder activationCode = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(character.length());
            activationCode.append(character.charAt(randomIndex));
        }
        return activationCode.toString();
    }















}
