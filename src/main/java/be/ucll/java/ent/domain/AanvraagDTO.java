package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.util.Objects;

public class AanvraagDTO implements Serializable {
    private long id;
    private String naamUser;
    private String productNaam;

    public AanvraagDTO() {
        // Default constructor
    }
    public AanvraagDTO(long id) {
        this.id = id;
    }

    public AanvraagDTO(long id,String naamUser, String productNaam) {
        this.id = id;
        this.naamUser = naamUser;
        this.productNaam = productNaam;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getNaamUser() {
        return productNaam;
    }
    public void setNaamUser(String NaamUser) {
        this.productNaam = productNaam;
    }

    public String getProductNaam() {
        return productNaam;
    }

    public void setProductNaam(String productNaam) {
        this.productNaam = productNaam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AanvraagDTO that = (AanvraagDTO) o;
        return id == that.id &&
                Objects.equals(naamUser.trim().toLowerCase(), that.naamUser.trim().toLowerCase()) &&
                Objects.equals(productNaam.trim().toLowerCase(), that.productNaam.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,naamUser, productNaam);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", productnaam='" + productNaam + '\'' +
                '}';
    }
}