package br.com.microservice.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@Document
public class User {

    @Id
    private String id;
    @NotBlank
    private String name;

    public User(String id, @NotBlank String name) {
        this.id = id;
        this.name = name;
    }
}
