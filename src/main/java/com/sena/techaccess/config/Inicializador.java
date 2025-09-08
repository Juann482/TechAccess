package com.sena.techaccess.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.sena.techaccess.service.IEstadoPermanenciaService;
import com.sena.techaccess.service.IEstadoCuentaService;

@Component
public class Inicializador implements CommandLineRunner {

    private final IEstadoPermanenciaService estadoPermanenciaService;
    private final IEstadoCuentaService estadoCuentaService;

    // Inyectamos los servicios vía constructor
    public Inicializador(    		
            IEstadoPermanenciaService estadoPermanenciaService,
            IEstadoCuentaService estadoCuentaService) {
        this.estadoPermanenciaService = estadoPermanenciaService;
        this.estadoCuentaService = estadoCuentaService;
    }

    @Override
    public void run(String... args) throws Exception {
        
    	// Lo que Ejecuta en la aplicacion
        estadoPermanenciaService.ingresoPermanencias();
        estadoCuentaService.inicializarEstados();
    }
}
