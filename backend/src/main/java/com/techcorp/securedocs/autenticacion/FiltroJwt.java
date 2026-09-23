package com.techcorp.securedocs.autenticacion;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techcorp.securedocs.usuarios.Usuario;
import com.techcorp.securedocs.usuarios.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class FiltroJwt extends OncePerRequestFilter {
    private final ServicioJwt jwt;
    private final UsuarioRepository usuarios;
    private final ObjectMapper json;

    public FiltroJwt(ServicioJwt jwt, UsuarioRepository usuarios, ObjectMapper json) {
        this.jwt = jwt;
        this.usuarios = usuarios;
        this.json = json;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String cabecera = request.getHeader("Authorization");
        if (cabecera == null) {
            chain.doFilter(request, response);
            return;
        }
        if (!cabecera.startsWith("Bearer ") || cabecera.substring(7).isBlank()) {
            error(response, "TOKEN_INVALIDO");
            return;
        }
        try {
            ServicioJwt.DatosToken datos = jwt.verificar(cabecera.substring(7));
            Usuario usuario = usuarios.findWithRolAndDepartamentoById(datos.usuarioId())
                .orElseThrow(() -> new TokenInvalidoException("TOKEN_INVALIDO"));
            if (!"ACTIVO".equals(usuario.getEstado())) {
                throw new TokenInvalidoException("USUARIO_NO_ACTIVO");
            }
            UsernamePasswordAuthenticationToken autenticacion =
                new UsernamePasswordAuthenticationToken(usuario, null, List.of());
            autenticacion.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(autenticacion);
            chain.doFilter(request, response);
        } catch (TokenInvalidoException ex) {
            SecurityContextHolder.clearContext();
            error(response, ex.getMessage());
        }
    }

    private void error(HttpServletResponse response, String codigo) throws IOException {
        response.setStatus(401);
        response.setContentType("application/problem+json");
        json.writeValue(response.getWriter(), Map.of(
            "type", "about:blank", "title", "No autenticado", "status", 401, "codigo", codigo));
    }
}
