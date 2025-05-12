package com.banca.microservicios.Feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "personaClient", url = "http://localhost:9098")
public interface PersonaClientFeign {

    @GetMapping("/personas/consultarPersonaPorIdentificacion/{identificacion}")
    PersonaDTO consultarPorIdentificacion(@PathVariable("identificacion") String identificacion);
}
