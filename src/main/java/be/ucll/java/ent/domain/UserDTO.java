package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class UserDTO implements Serializable {

    private long id;
    private String naam;
    private String voornaam;
    private Date geboortedatum;
    private String adres;
    private String telefoon;
    public UserDTO() {
    }

    public UserDTO(long id) {
        this.id = id;
    }

    public UserDTO(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public UserDTO(long id, String naam, String voornaam, Date geboortedatum, String adres, String telefoon) {
        this.id = id;
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboortedatum = geboortedatum;
        this.adres = adres;
        this.telefoon = telefoon;
    }

    // Getters and Setters

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
    public String getAdres() {
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
        UserDTO that = (UserDTO) o;
        return id == that.id &&
                Objects.equals(naam.trim().toLowerCase(), that.naam.trim().toLowerCase()) &&
                Objects.equals(voornaam.trim().toLowerCase(), that.voornaam.trim().toLowerCase()) &&
                Objects.equals(geboortedatum, that.geboortedatum)&&
                Objects.equals(adres.trim().toLowerCase(), that.adres.trim().toLowerCase()) &&
                Objects.equals(telefoon.trim().toLowerCase(), that.telefoon.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam, voornaam, geboortedatum, adres, telefoon);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", geboortedatum=" + getGeboortedatumstr() +
                ", adres='" + adres + '\'' +
                ", telefoon='" + telefoon + '\'' +
                '}';
    }
}