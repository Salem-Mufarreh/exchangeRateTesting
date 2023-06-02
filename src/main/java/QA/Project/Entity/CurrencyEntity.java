package QA.Project.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table
public class CurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public Long id;
    public String sourceCurrency;
    public String targetCurrency;
    public Double rate;
    public Date date;

}
