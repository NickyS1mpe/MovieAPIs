package dev.nick.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    ObjectId id;
    private String username;
    private String password;
    private String emailAddress;
    private String nickname;
    private String joinDate;
    private String lastLogin;
    private String avatar;
    private String twitter;
    private String notes;

    @DocumentReference
    private List<Review> reviewIds;

    private List<String> watchList;

    public User(String username, String password, String emailAddress, String nickname, String joinDate, String lastLogin, String avatar, String twitter, String notes) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.nickname = nickname;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
        this.avatar = avatar;
        this.twitter = twitter;
        this.notes = notes;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void removeInfo() {
        this.id = null;
        this.password = null;
    }

    public void update(String nickname, String avatar, String notes, String twitter) {
        this.nickname = nickname;
        this.twitter = twitter;
        this.avatar = avatar;
        this.notes = notes;
    }
}
