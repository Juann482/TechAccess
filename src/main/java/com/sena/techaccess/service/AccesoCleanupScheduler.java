package com.sena.techaccess.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sena.techaccess.model.Acceso;

@Service
public class AccesoCleanupScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccesoCleanupScheduler.class);

    private final IAccesoService accesoService;

    public AccesoCleanupScheduler(IAccesoService accesoService) {
        this.accesoService = accesoService;
    }

    // Cada 12 horas
    @Scheduled(cron = "0 0 0/12 * * ?")
    public void limpiarHistorialAccesos() {
        List<Acceso> accesos = accesoService.findAll();
        for (Acceso a : accesos) {
            accesoService.delete(a.getIdacceso());
        }
        LOGGER.info("Historial de accesos limpiado. Registros eliminados: {}", accesos.size());
    }
}
