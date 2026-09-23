package com.techcorp.securedocs.autenticacion;

import com.techcorp.securedocs.usuarios.Usuario;
import com.techcorp.securedocs.usuarios.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ServicioAutenticacion {
    private final UsuarioRepository usuarios;
    private final PasswordEncoder codificador;
    private final ServicioJwt jwt;

    public ServicioAutenticacion(UsuarioRepository usuarios, PasswordEncoder codificador, ServicioJwt jwt) {
        this.usuarios = usuarios;
        this.codificador = codificador;
        this.jwt = jwt;
    }

    public String ingresar(String username, String password) {
        Usuario usuario = usuarios.findByUsername(username).orElse(null);
        if (usuario == null || !codificador.matches(password, usuario.getPasswordHash())) {
            throw new AutenticacionException(HttpStatus.UNAUTHORIZED, "CREDENCIALES_INVALIDAS",
                "Credenciales inválidas");
        }
        if (!"ACTIVO".equals(usuario.getEstado())) {
            throw new AutenticacionException(HttpStatus.FORBIDDEN, "USUARIO_NO_ACTIVO",
                "El usuario no está activo");
        }
        return jwt.emitir(usuario.getId());
    }
}
