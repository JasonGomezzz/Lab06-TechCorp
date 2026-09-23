package com.techcorp.securedocs.auditoria;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import com.techcorp.securedocs.autorizacion.ContextoAutorizacion;
import com.techcorp.securedocs.autorizacion.Decision;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditoriaService {
    private final AuditoriaRepository repositorio;
    private final Clock reloj;

    public AuditoriaService(AuditoriaRepository repositorio, Clock reloj) {
        this.repositorio = repositorio;
        this.reloj = reloj;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrar(ContextoAutorizacion contexto, Decision decision) {
        Auditoria registro = new Auditoria();
        registro.setFechaHora(LocalDateTime.now(reloj));
        registro.setUsuario(contexto.sujeto().username());
        registro.setUsuarioRol(contexto.sujeto().rol());
        registro.setUsuarioDepartamento(contexto.sujeto().departamento());
        registro.setRecurso(contexto.recurso().identificador());
        registro.setAccion(contexto.accion().name());
        registro.setResultado(decision.permitido() ? "PERMITIDO" : "DENEGADO");
        registro.setCapa(decision.capa());
        registro.setMotivo(String.join("; ", decision.motivos()));
        registro.setPoliticasEvaluadas(decision.politicas().stream()
            .map(p -> Map.<String, Object>of("codigo", p.codigo(), "cumple", p.cumple(), "detalle", p.detalle()))
            .toList());
        registro.setIp(contexto.entorno().direccionIp());
        registro.setUbicacion(contexto.entorno().ubicacion());
        registro.setDispositivo(contexto.entorno().dispositivo());
        registro.setDepartamentoRecurso(contexto.recurso().departamento());
        repositorio.saveAndFlush(registro);
    }
}
