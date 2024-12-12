package pl.agh.to.lang.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Value;

@Entity
@Value
public class Translation {
    @Id
    @GeneratedValue
    public long id;

    @Column
    public String word;

    @Column
    public String translation;
}
