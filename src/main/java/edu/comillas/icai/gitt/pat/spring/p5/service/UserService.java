package edu.comillas.icai.gitt.pat.spring.p5.service;

import edu.comillas.icai.gitt.pat.spring.p5.entity.AppUser;
import edu.comillas.icai.gitt.pat.spring.p5.entity.Token;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileRequest;
import edu.comillas.icai.gitt.pat.spring.p5.model.ProfileResponse;
import edu.comillas.icai.gitt.pat.spring.p5.model.RegisterRequest;
import edu.comillas.icai.gitt.pat.spring.p5.repository.TokenRepository;
import edu.comillas.icai.gitt.pat.spring.p5.repository.AppUserRepository;
import edu.comillas.icai.gitt.pat.spring.p5.util.Hashing;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TODO#6
 * Completa los métodos del servicio para que cumplan con el contrato
 * especificado en el interface UserServiceInterface, utilizando
 * los repositorios y entidades creados anteriormente
 */

@Service
public class UserService implements UserServiceInterface {

    Hashing hashing = new Hashing();

    @Autowired AppUserRepository appUserRepository;

    @Autowired TokenRepository tokenRepository;

    public Token login(String email, String password) {
        AppUser appUser = appUserRepository.findByEmail(email);
        if (appUser == null || !hashing.compare(appUser.password, password)) return null;

        Token token = tokenRepository.findByAppUser(appUser);
        if (token != null) return token;

        token = new Token();
        token.setAppUser(appUser);
        return tokenRepository.save(token);
    }

    public AppUser authentication(String tokenId) {
        Optional<Token> token = tokenRepository.findById(tokenId);
        if (token.isEmpty()) return null;

        return token.get().appUser;
    }

    public ProfileResponse profile(AppUser appUser) {
        return new ProfileResponse(appUser.name, appUser.email, appUser.role);
    }

    public ProfileResponse profile(AppUser appUser, @Valid ProfileRequest profile) {
        appUser.name = profile.name();
        appUser.role = profile.role();
        appUser.password = hashing.hash(profile.password());
        appUserRepository.save(appUser);

        return new ProfileResponse(appUser.name, appUser.email, appUser.role);
    }

    public ProfileResponse profile(@Valid RegisterRequest register) {
        AppUser appUser = new AppUser();
        appUser.name = register.name();
        appUser.email = register.email();
        appUser.role = register.role();
        appUser.password = hashing.hash(register.password());
        appUserRepository.save(appUser);

        return new ProfileResponse(appUser.name, appUser.email, appUser.role);
    }

    public void logout(String tokenId) {
        tokenRepository.deleteById(tokenId);
    }

    public void delete(AppUser appUser) {
        tokenRepository.deleteByAppUser(appUser);
        appUserRepository.delete(appUser);
    }

}
