package plataforma;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<ActivoIndustrial> loteInspeccion = new ArrayList<>();

        loteInspeccion.add(new GeneradorDiesel(
                "GD-MINA-001",
                LocalDate.of(2021, 3, 15),
                4_250.0,
                98.5
        ));

        loteInspeccion.add(new TurbinaEolica(
                "TE-PARQUE-014",
                LocalDate.of(2019, 11, 8),
                18_600.0,
                3.4,
                22.0
        ));

        GestorMantenimiento gestor = new GestorMantenimiento();
        gestor.procesarLote(loteInspeccion);
    }
}
