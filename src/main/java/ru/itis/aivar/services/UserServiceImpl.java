package ru.itis.aivar.services;

import ru.itis.aivar.exceptions.db.NoneUniqueValueException;
import ru.itis.aivar.models.Title;
import ru.itis.aivar.models.User;
import ru.itis.aivar.repositories.abstracts.UsersRepository;
import ru.itis.aivar.services.abstracts.UsersService;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UsersService {
    private UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    public boolean save(User user) {
        user = usersRepository.save(user);
        return user != null;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> optionalUser = usersRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return user;
        }
        return null;
    }

    @Override
    public User findById(Long id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public boolean update(User user) {
        try {
            usersRepository.update(user);
            return true;
        } catch (NoneUniqueValueException e){
            return false;
        }
    }

    @Override
    public void addTitle(User user, Title title) {
        usersRepository.addTitle(user, title);
    }
}
