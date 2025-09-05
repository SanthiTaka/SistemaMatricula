
import java.time.LocalDateTime;

public class PeriodoMatricula {
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private boolean aberto;

    public PeriodoMatricula(LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.aberto = false;
    }

    public void abrirPeriodo() {
        this.aberto = true;
    }

    public void fecharPeriodo() {
        this.aberto = false;
    }

    public boolean isAberto() {
        LocalDateTime now = LocalDateTime.now();
        if (dataInicio != null && dataFim != null) {
            return aberto && (now.isEqual(dataInicio) || now.isAfter(dataInicio)) && now.isBefore(dataFim);
        }
        return aberto;
    }

    public LocalDateTime getDataInicio() { return 
        dataInicio; 
    }

    public LocalDateTime getDataFim() { 
        return dataFim; 
    }
}
