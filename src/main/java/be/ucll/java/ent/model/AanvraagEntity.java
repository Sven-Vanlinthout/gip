package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Aanvraag")
@NamedQueries({
        @NamedQuery(name = "Aanvraag.getAll", query = "SELECT e FROM AanvraagEntity e ORDER BY e.id"),
        @NamedQuery(name = "Aanvraag.countAll", query = "SELECT count(e) FROM AanvraagEntity e")
})
public class AanvraagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sseq")
    @SequenceGenerator(name = "sseq", sequenceName = "product_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 128)
    private String naamUser;

    @Column(length = 128)
    private String productNaam;

    @Column
    private Date Datum;
    public AanvraagEntity() {
        // Default constructor
    }

    // Constructor with all MANDATORY fields
    public AanvraagEntity(long id, String naamUser, String productNaam,Date Datum) {
        this.id = id;
        this.naamUser = naamUser;
        this.productNaam = productNaam;
        this.Datum = Datum;

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

    public void setNaamUser(String naamUser) {
        this.naamUser = naamUser;
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
    public void setDatum(Date Datum) {
        this.Datum = Datum;
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
        AanvraagEntity that = (AanvraagEntity) o;
        return id == that.id &&
                Objects.equals(naamUser, that.naamUser) &&
                Objects.equals(productNaam, that.productNaam)&&
                Objects.equals(Datum, that.Datum);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id,naamUser, productNaam, Datum);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", naamUuser='" + naamUser + '\'' +
                ", naam='" + productNaam + '\'' +
                '}';
    }
}
