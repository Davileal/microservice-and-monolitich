package br.com.microservice.user.web.rest;

import br.com.microservice.user.domain.User;
import br.com.microservice.user.enums.EntityStatusEnum;
import br.com.microservice.user.exception.CustomException;
import br.com.microservice.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Status;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserResource {

    private UserRepository repository;

    public UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable String id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            throw new CustomException("User not found", Status.NOT_FOUND);
        }
        return new ResponseEntity<>(repository.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody User user) {
        return new ResponseEntity<>(repository.save(user), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody User user) {
        return new ResponseEntity<>(repository.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {
            throw new CustomException("User not found", Status.NOT_FOUND);
        }
        user.get().setStatus(EntityStatusEnum.INACTIVE);
        return new ResponseEntity<>(repository.save(user.get()), HttpStatus.OK);
    }

}
