package br.com.udemycourse.demo.Controllers;


import br.com.udemycourse.demo.Services.AuthServices;
import br.com.udemycourse.demo.data.vo.v1.security.AccountCredentialVO;
import br.com.udemycourse.demo.data.vo.v1.security.TokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication Endpoints")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates a user and returns a Token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialVO data){
        if(checkIfParamsIsNotNullOrBlankOrEmpty(data)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request...");
        }
        var token = authServices.signin(data);
        if(token == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @Operation(summary = "Returns a new access token when refresh token is provided")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable String username, @RequestHeader("Authorization") String refreshToken){
        if(refreshToken == null || refreshToken.isBlank() || refreshToken.isEmpty() ||
        username == null || username.isBlank() || username.isEmpty()
        ){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request...");
        }
        var token = authServices.refreshToken(username, refreshToken);
        if(token == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Client Request...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    private Boolean checkIfParamsIsNotNullOrBlankOrEmpty(AccountCredentialVO data) {
        if (data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getUsername().isEmpty() ||
            data.getPassword() == null || data.getPassword().isBlank() || data.getPassword().isEmpty()
        ){
            return true;
        } else {
            return  false;
        }
    }

}
