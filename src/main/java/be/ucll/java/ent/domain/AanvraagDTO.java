package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class AanvraagDTO implements Serializable {
    private long id;
    private String naamUser;
    private String productNaam;
    private Date Datum;

    public AanvraagDTO() {
        // Default constructor
    }
    public AanvraagDTO(long id) {
        this.id = id;
    }

    public AanvraagDTO(long id,String naamUser, String productNaam, Date datum) {
        this.id = id;
        this.naamUser = naamUser;
        this.productNaam = productNaam;
        this.Datum = datum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getNaamUser() {
        return naamUser;
    }
    public void setNaamUser(String NaamUser) {
        this.naamUser = NaamUser;
    }

    public String getProductNaam() {
        return productNaam;
    }

    public void setProductNaam(String productNaam) {
        this.productNaam = productNaam;
    }

    public Date getDatum() {
        return Datum;
    }
    public void setDatum(Date datum) {
        this.Datum = datum;
    }

    public String getDatumstr() {
        if (Datum != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(Datum);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AanvraagDTO that = (AanvraagDTO) o;
        return id == that.id &&
                Objects.equals(naamUser.trim().toLowerCase(), that.naamUser.trim().toLowerCase()) &&
                Objects.equals(productNaam.trim().toLowerCase(), that.productNaam.trim().toLowerCase())&&
                Objects.equals(Datum, that.Datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,naamUser, productNaam, Datum);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", productnaam='" + productNaam + '\'' +
                '}';
    }
}