package dev.nick.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    public User(String username, String password, String emailAddress, String nickname, String joinDate, String lastLogin) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
        this.nickname = nickname;
        this.joinDate = joinDate;
        this.lastLogin = lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }
}
