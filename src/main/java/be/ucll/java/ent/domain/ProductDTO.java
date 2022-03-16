package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.util.Objects;

public class ProductDTO implements Serializable {
    private long id;
    private String productNaam;

    public ProductDTO() {
        // Default constructor
    }
    public ProductDTO(long id) {
        this.id = id;
    }

    public ProductDTO(long id, String productNaam) {
        this.id = id;
        this.productNaam = productNaam;
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

    public void setProductNaam(String naam) {
        this.productNaam = productNaam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id &&
                Objects.equals(productNaam.trim().toLowerCase(), that.productNaam.trim().toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productNaam);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", productnaam='" + productNaam + '\'' +
                '}';
    }
}
