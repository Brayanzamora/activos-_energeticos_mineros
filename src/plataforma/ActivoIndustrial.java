package plataforma;

import java.time.LocalDate;
import java.util.Objects;

public abstract class ActivoIndustrial {

    private final String codigoSerie;
    private final LocalDate fechaInstalacion;
    private double horasOperacion;

    protected ActivoIndustrial(String codigoSerie, LocalDate fechaInstalacion, double horasOperacion) {
        if (codigoSerie == null || codigoSerie.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de serie no puede ser nulo ni vacío.");
        }
        if (fechaInstalacion == null) {
            throw new IllegalArgumentException("La fecha de instalación no puede ser nula.");
        }
        if (horasOperacion < 0) {
            throw new IllegalArgumentException("Las horas de operación no pueden ser negativas.");
        }
        this.codigoSerie = codigoSerie.trim();
        this.fechaInstalacion = fechaInstalacion;
        this.horasOperacion = horasOperacion;
    }

    public abstract double calcularCriticidad();

    public abstract boolean indicarMantenimientoUrgente();

    public String getCodigoSerie() {
        return codigoSerie;
    }

    public LocalDate getFechaInstalacion() {
        return fechaInstalacion;
    }

    public double getHorasOperacion() {
        return horasOperacion;
    }

    public void setHorasOperacion(double horasOperacion) {
        if (horasOperacion < 0) {
            throw new IllegalArgumentException("Las horas de operación no pueden ser negativas.");
        }
        this.horasOperacion = horasOperacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ActivoIndustrial)) {
            return false;
        }
        ActivoIndustrial that = (ActivoIndustrial) o;
        return Objects.equals(codigoSerie, that.codigoSerie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoSerie);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "{codigoSerie='" + codigoSerie + '\''
                + ", fechaInstalacion=" + fechaInstalacion
                + ", horasOperacion=" + horasOperacion
                + '}';
    }
}
