package plataforma;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        GestorMantenimiento gestor = new GestorMantenimiento();

        demostrarLoteHeterogeneo(gestor);
        demostrarInspeccionIndividual(gestor);
        demostrarRechazoDeInvariantes();
        informarContadorEstatico();
    }

    private static void demostrarLoteHeterogeneo(GestorMantenimiento gestor) {
        System.out.println("========== SECCIÓN 1: Lote heterogéneo de inspección ==========");
        List<ActivoIndustrial> loteInspeccion = new ArrayList<>();

        loteInspeccion.add(new GeneradorDiesel(
                "GD-MINA-001",
                LocalDate.of(2021, 3, 15),
                4_250.0,
                98.5
        ));

        loteInspeccion.add(new GeneradorDiesel(
                "GD-TALLER-002",
                LocalDate.of(2022, 7, 1),
                800.0
        ));

        loteInspeccion.add(new TurbinaEolica(
                "TE-PARQUE-014",
                LocalDate.of(2019, 11, 8),
                18_600.0,
                3.4,
                22.0
        ));

        gestor.procesarLote(loteInspeccion);
        System.out.println();
    }

    private static void demostrarInspeccionIndividual(GestorMantenimiento gestor) {
        System.out.println("========== SECCIÓN 2: Inspección individual (sobrecarga) ==========");
        ActivoIndustrial unico = new TurbinaEolica(
                "TE-PARQUE-021",
                LocalDate.of(2020, 5, 20),
                9_100.0,
                1.2,
                4.0
        );
        gestor.procesarLote(unico);
        System.out.println();
    }

    private static void demostrarRechazoDeInvariantes() {
        System.out.println("========== SECCIÓN 3: Rechazo de datos inválidos ==========");
        intentarAlta("horas negativas", () -> new GeneradorDiesel(
                "GD-INVALIDO",
                LocalDate.of(2021, 3, 15),
                -10.0,
                80.0
        ));
        intentarAlta("serie vacía", () -> new TurbinaEolica(
                "   ",
                LocalDate.of(2019, 11, 8),
                100.0,
                1.0,
                10.0
        ));
        intentarAlta("temperatura negativa", () -> new GeneradorDiesel(
                "GD-TEMP",
                LocalDate.of(2021, 3, 15),
                100.0,
                -5.0
        ));
        intentarAlta("desgaste de palas fuera de rango", () -> new TurbinaEolica(
                "TE-DESGASTE",
                LocalDate.of(2019, 11, 8),
                100.0,
                1.0,
                140.0
        ));
        System.out.println();
    }

    private static void informarContadorEstatico() {
        System.out.println("========== SECCIÓN 4: Contador estático de inspecciones ==========");
        System.out.println("Activos inspeccionados en la sesión: "
                + GestorMantenimiento.getActivosInspeccionados());
    }

    private static void intentarAlta(String escenario, Runnable alta) {
        try {
            alta.run();
            System.out.println("[ACEPTADO] " + escenario);
        } catch (IllegalArgumentException excepcion) {
            System.out.println("[RECHAZADO] " + escenario + " → " + excepcion.getMessage());
        }
    }
}
