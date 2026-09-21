package plataforma;

import java.util.ArrayList;
import java.util.List;

public class GestorMantenimiento {

    public void procesarLote(List<ActivoIndustrial> loteInspeccion) {
        for (ActivoIndustrial activo : loteInspeccion) {
            System.out.println("----------------------------------------");
            System.out.println("Serie: " + activo.getCodigoSerie());
            System.out.printf("Criticidad: %.2f%n", activo.calcularCriticidad());
            System.out.println("Mantenimiento urgente: " + activo.indicarMantenimientoUrgente());

            if (activo instanceof GeneradorDiesel) {
                GeneradorDiesel generador = (GeneradorDiesel) activo;
                generador.purgarCombustible();
                generador.certificarEmisionesCO2();
            }

            if (activo instanceof TurbinaEolica) {
                TurbinaEolica turbina = (TurbinaEolica) activo;
                turbina.bloquearRotorManual();
                turbina.enviarTelemetriaSCADA();
            }
        }
    }
}
