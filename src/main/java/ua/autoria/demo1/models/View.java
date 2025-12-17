package ua.autoria.demo1.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate date;
    public View(Offer offer, LocalDate date) {
        this.offer = offer;
        this.date = date;
    }
}
