package plataforma;

import java.time.LocalDate;

public class GeneradorDiesel extends ActivoIndustrial implements ReguladoAmbiental {

    public static final double LIMITE_TEMPERATURA_REFRIGERANTE = 95.0;
    public static final double FACTOR_HORAS_OPERACION = 0.02;
    public static final double FACTOR_TEMPERATURA = 0.8;
    public static final double TEMPERATURA_REPOSO = 80.0;

    private double temperaturaRefrigerante;

    public GeneradorDiesel(String codigoSerie,
                           LocalDate fechaInstalacion,
                           double horasOperacion) {
        this(codigoSerie, fechaInstalacion, horasOperacion, TEMPERATURA_REPOSO);
    }

    public GeneradorDiesel(String codigoSerie,
                           LocalDate fechaInstalacion,
                           double horasOperacion,
                           double temperaturaRefrigerante) {
        super(codigoSerie, fechaInstalacion, horasOperacion);
        validarTemperatura(temperaturaRefrigerante);
        this.temperaturaRefrigerante = temperaturaRefrigerante;
    }

    @Override
    public double calcularCriticidad() {
        return (getHorasOperacion() * FACTOR_HORAS_OPERACION)
                + (temperaturaRefrigerante * FACTOR_TEMPERATURA);
    }

    @Override
    public boolean requiereParadaUrgente() {
        return temperaturaRefrigerante > LIMITE_TEMPERATURA_REFRIGERANTE;
    }

    public void purgarCombustible() {
        System.out.println("Generador " + getCodigoSerie()
                + ": purga de combustible ejecutada.");
    }

    @Override
    public void certificarEmisionesCO2() {
        System.out.println("Generador " + getCodigoSerie()
                + ": certificación de emisiones de CO2 registrada.");
    }

    public double getTemperaturaRefrigerante() {
        return temperaturaRefrigerante;
    }

    public void setTemperaturaRefrigerante(double temperaturaRefrigerante) {
        validarTemperatura(temperaturaRefrigerante);
        this.temperaturaRefrigerante = temperaturaRefrigerante;
    }

    private static void validarTemperatura(double temperaturaRefrigerante) {
        if (Double.isNaN(temperaturaRefrigerante) || temperaturaRefrigerante < 0) {
            throw new IllegalArgumentException(
                    "La temperatura del refrigerante no puede ser negativa.");
        }
    }
}
