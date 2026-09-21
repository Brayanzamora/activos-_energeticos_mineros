package plataforma;

import java.time.LocalDate;

public class TurbinaEolica extends ActivoIndustrial implements TelemetricoSCADA {

    public static final double FACTOR_VIBRACION = 12.0;
    public static final double FACTOR_DESGASTE_PALAS = 8.5;
    public static final double UMBRAL_CRITICIDAD_URGENTE = 50.0;

    private double vibracionRodamientos;
    private double desgastePalas;

    public TurbinaEolica(String codigoSerie,
                         LocalDate fechaInstalacion,
                         double horasOperacion,
                         double vibracionRodamientos,
                         double desgastePalas) {
        super(codigoSerie, fechaInstalacion, horasOperacion);
        validarVibracion(vibracionRodamientos);
        validarDesgaste(desgastePalas);
        this.vibracionRodamientos = vibracionRodamientos;
        this.desgastePalas = desgastePalas;
    }

    @Override
    public double calcularCriticidad() {
        return (vibracionRodamientos * FACTOR_VIBRACION)
                + (desgastePalas * FACTOR_DESGASTE_PALAS);
    }

    @Override
    public boolean requiereParadaUrgente() {
        return calcularCriticidad() >= UMBRAL_CRITICIDAD_URGENTE;
    }

    public void bloquearRotorManual() {
        System.out.println("Turbina " + getCodigoSerie()
                + ": rotor bloqueado manualmente.");
    }

    @Override
    public void enviarTelemetriaSCADA() {
        System.out.println("Turbina " + getCodigoSerie()
                + ": telemetría enviada a SCADA "
                + "[vibración=" + vibracionRodamientos
                + " mm/s, desgastePalas=" + desgastePalas + "%].");
    }

    public double getVibracionRodamientos() {
        return vibracionRodamientos;
    }

    public void setVibracionRodamientos(double vibracionRodamientos) {
        validarVibracion(vibracionRodamientos);
        this.vibracionRodamientos = vibracionRodamientos;
    }

    public double getDesgastePalas() {
        return desgastePalas;
    }

    public void setDesgastePalas(double desgastePalas) {
        validarDesgaste(desgastePalas);
        this.desgastePalas = desgastePalas;
    }

    private static void validarVibracion(double vibracionRodamientos) {
        if (Double.isNaN(vibracionRodamientos) || vibracionRodamientos < 0) {
            throw new IllegalArgumentException(
                    "La vibración de rodamientos no puede ser negativa.");
        }
    }

    private static void validarDesgaste(double desgastePalas) {
        if (Double.isNaN(desgastePalas) || desgastePalas < 0 || desgastePalas > 100) {
            throw new IllegalArgumentException(
                    "El desgaste de palas debe estar entre 0 y 100 %.");
        }
    }
}
