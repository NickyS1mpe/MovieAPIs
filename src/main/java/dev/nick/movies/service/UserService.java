package dev.nick.movies.service;

import dev.nick.movies.model.Movie;
import dev.nick.movies.model.User;
import dev.nick.movies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    public Optional<User> findUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        query.fields().exclude("_id").exclude("password").exclude("emailAddress");
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    public User createUser(String username, String password, String emailAddress, String nickname) throws Exception {
        if (userRepository.existsUserByUsername(username)) {
            throw new Exception("Username already exists.");
        }
        String pw = SHA256(password), date = getDate();
        return userRepository.save(new User(username, pw, emailAddress, nickname, date, date, "", "", ""));
    }

    public Optional<User> userLogin(String username, String password) {
        String pw = SHA256(password);
        Query query = new Query(Criteria.where("username").is(username).and("password").is(pw));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            String date = getDate();
            user.setLastLogin(date);
            mongoTemplate.save(user);
            user.removeInfo();
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> updateUser(String username, String nickname, String twitter, String avatar, String notes) {
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            user.update(nickname, avatar, notes, twitter);
            mongoTemplate.save(user);
            user.removeInfo();
        }
        return Optional.ofNullable(user);
    }

    public Optional<User> addWatchList(String username, String imdbId) {
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            List<String> list = user.getWatchList();
            if (!list.contains(imdbId)) {
                list.add(imdbId);
                mongoTemplate.save(user);
                user.removeInfo();
            }
        }

        return Optional.ofNullable(user);
    }

    public Optional<User> removeWatchList(String username, String imdbId) {
        Query query = new Query(Criteria.where("username").is(username));
        User user = mongoTemplate.findOne(query, User.class);
        if (user != null) {
            List<String> list = user.getWatchList();
            if (list.contains(imdbId)) {
                list.remove(imdbId);
                mongoTemplate.save(user);
                user.removeInfo();
            }
        }

        return Optional.ofNullable(user);
    }

    public String SHA256(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(s.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getDate() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        return currentTime.format(formatter);
    }
}
