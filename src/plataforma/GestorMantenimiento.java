package plataforma;

import java.util.List;

public class GestorMantenimiento {

    private static int activosInspeccionados = 0;

    public static int getActivosInspeccionados() {
        return activosInspeccionados;
    }

    public void procesarLote(List<ActivoIndustrial> loteInspeccion) {
        for (ActivoIndustrial activo : loteInspeccion) {
            procesarLote(activo);
        }
    }

    public void procesarLote(ActivoIndustrial activo) {
        System.out.println("----------------------------------------");
        System.out.println("Serie: " + activo.getCodigoSerie());
        System.out.printf("Criticidad: %.2f%n", activo.calcularCriticidad());
        System.out.println("Parada urgente: " + activo.requiereParadaUrgente());

        if (activo instanceof ReguladoAmbiental) {
            ReguladoAmbiental regulado = (ReguladoAmbiental) activo;
            regulado.certificarEmisionesCO2();
        }

        if (activo instanceof GeneradorDiesel) {
            GeneradorDiesel generador = (GeneradorDiesel) activo;
            generador.purgarCombustible();
        }

        if (activo instanceof TelemetricoSCADA) {
            TelemetricoSCADA telemetrico = (TelemetricoSCADA) activo;
            telemetrico.enviarTelemetriaSCADA();
        }

        if (activo instanceof TurbinaEolica) {
            TurbinaEolica turbina = (TurbinaEolica) activo;
            turbina.bloquearRotorManual();
        }

        activosInspeccionados++;
    }
}
