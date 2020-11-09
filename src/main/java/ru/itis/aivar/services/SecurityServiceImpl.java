package ru.itis.aivar.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.aivar.models.User;
import ru.itis.aivar.services.abstracts.SecurityService;
import ru.itis.aivar.services.abstracts.UsersService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SecurityServiceImpl implements SecurityService {
    PasswordEncoder passwordEncoder;
    UsersService usersService;

    public SecurityServiceImpl(PasswordEncoder passwordEncoder , UsersService usersService) {
        this.passwordEncoder = passwordEncoder;
        this.usersService = usersService;
    }

    @Override
    public boolean isSigned(HttpServletRequest req) {
        return req.getSession().getAttribute("user") != null;
    }

    @Override
    public boolean singIn(HttpServletRequest req, String email, String password, boolean rememberMe) {
        User user = usersService.findByEmail(email);
        if (user == null){
            return false;
        }
        if (email.equals(user.getEmail()) && matches(password, user.getHashPassword())){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            if (rememberMe){
                session.setMaxInactiveInterval(60*60*24*7);
            } else {
                session.setMaxInactiveInterval(0);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean signUp(HttpServletRequest req, String email, String nickname, String password) {
        User user = User.builder()
                .id(null)
                .email(email)
                .hashPassword(encode(password))
                .nickname(nickname)
                .build();
        if (usersService.save(user)){
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(60*60*24*7);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void signOut(HttpServletRequest req) {
        req.getSession().removeAttribute("user");
    }

    @Override
    public String encode(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean matches(String rawPassword, String hashPassword) {
        return passwordEncoder.matches(rawPassword, hashPassword);
    }
}
