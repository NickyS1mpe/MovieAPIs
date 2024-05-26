package dev.nick.movies.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    private ObjectId id;
    private String body;
    private String nickname;
    private  String create_time;

    public Review(String body, String nickname, String create_time) {
        this.body = body;
        this.nickname = nickname;
        this.create_time = create_time;
    }
}
