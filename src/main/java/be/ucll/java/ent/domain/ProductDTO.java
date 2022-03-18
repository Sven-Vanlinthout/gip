package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.util.Objects;

public class ProductDTO implements Serializable {
    private long id;
    private String productNaam;
    private String naamUser;

    public ProductDTO() {
        // Default constructor
    }
    public ProductDTO(long id) {
        this.id = id;
    }

    public ProductDTO(long id, String productNaam,String naamUser) {
        this.id = id;
        this.productNaam = productNaam;
        this.naamUser = naamUser;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductNaam() {
        return productNaam;
    }

    public void setProductNaam(String productNaam) {
        this.productNaam = productNaam;
    }

    public String getNaamUser() {
        return naamUser;
    }
    public void setNaamUser(String NaamUser) {
        this.naamUser = NaamUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id &&
                Objects.equals(productNaam.trim().toLowerCase(), that.productNaam.trim().toLowerCase())&&
                Objects.equals(naamUser.trim().toLowerCase(), that.naamUser.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productNaam, naamUser);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", productnaam='" + productNaam + '\'' +
                '}';
    }
}
