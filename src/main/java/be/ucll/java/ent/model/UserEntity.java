package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Users")
@NamedQueries({
        @NamedQuery(name = "User.getAll", query = "SELECT e FROM UserEntity e ORDER BY e.id"),
        @NamedQuery(name = "User.countAll", query = "SELECT count(e) FROM UserEntity e")
})
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sseq")
    @SequenceGenerator(name = "sseq", sequenceName = "student_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 128)
    private String naam;

    @Column(length = 128)
    private String voornaam;

    @Column
    private Date geboortedatum;

    @Column
    private String adres;

    @Column
    private String telefoon;


    /* ***** Constructors ***** */
    public UserEntity() {
        // Default constructor
    }

    // Constructor with all MANDATORY fields
    public UserEntity(long id, String naam, String voornaam, Date geboortedatum, String adres, String telefoon) {
        this.id = id;
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
        this.telefoon = telefoon;
    }

    /* ***** Getters en Setters ***** */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getGeboortedatumstr() {
        if (geboortedatum != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(geboortedatum);
        }
        return "";
    }

    public String getadres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefoon() {
        return telefoon;
    }

    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(naam, that.naam) &&
                Objects.equals(voornaam, that.voornaam) &&
                Objects.equals(geboortedatum, that.geboortedatum) &&
                Objects.equals(adres, that.adres) &&
                Objects.equals(telefoon, that.telefoon);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam, voornaam, geboortedatum,adres, telefoon );
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", geboortedatum=" + getGeboortedatumstr() +
                ", adres=" + adres +
                ", telefoon=" + telefoon +
                '}';
    }
}