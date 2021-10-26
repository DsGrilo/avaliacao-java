package com.dsgrilo.coffeebreak.stok.controller;
import com.dsgrilo.coffeebreak.stok.exception.DataBaseException;
import com.dsgrilo.coffeebreak.stok.model.UserModel;
import com.dsgrilo.coffeebreak.stok.repository.UserRepository;
import com.dsgrilo.coffeebreak.stok.security.EncoderPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    // Adicionando Repository como atributo para utilizar a inversão de controle
    private final UserRepository userRepository;
    // Adicionando PasswordEncoder como atributo para utilizar a inversão de controle
    private final EncoderPassword encoder;

    public UserController(UserRepository userRepository, EncoderPassword encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    // Método de GET
    @GetMapping("/find")
    public ResponseEntity<List<UserModel>> findAllUsers(){

            List<UserModel> users = userRepository.findAll();

            return ResponseEntity.ok().body(users);

    }

    // Método de GET BY ID
    @GetMapping("/find/{uuid_user}")
    public ResponseEntity<Optional<UserModel>> findByUUID(@PathVariable("uuid_user") UUID uuid_user) throws DataBaseException {

            Optional<UserModel> user = userRepository.findById(uuid_user);

            if (user.isEmpty()){
                return ResponseEntity.badRequest().body(user);
            }

            return ResponseEntity.ok().body(user);

    }

    //METODO GET

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyPassword(@RequestParam String login,
                                                  @RequestParam String password)
    {
            // Utilizado Optional para evitar o NULLPOINTEREXCEPTION
            Optional<UserModel> optUserModel = userRepository.findByLogin(login);

            // Verifica se o retorno da chamada está vazio
            if(optUserModel.isEmpty())
            {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
            }

            UserModel userModel = optUserModel.get();

            // faz a comparação da senha criptografada recebida com a senha que esta salva (criptografada) no Banco
            boolean valid = encoder.getPasswordEncoder().matches(password, userModel.getPassword());

            // Retorno em HTTP caso senha seja igual ou não
            HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

            return ResponseEntity.status(status).body(valid);
    }

    // MÉTODO DE POST
    @PostMapping("/create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userModel){

        // Interceptação da Senha Recebida para realizar a encriptação
        userModel.setPassword(encoder.getPasswordEncoder().encode(userModel.getPassword()));

        return ResponseEntity.ok().body(userRepository.save(userModel));

    }

}
