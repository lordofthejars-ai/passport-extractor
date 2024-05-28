package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Passport extends PanacheEntity  {

    public String name;
    public String surname;
    public LocalDate expireDate;
    public String passportNumber;

}
