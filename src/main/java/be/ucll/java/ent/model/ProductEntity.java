package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Product")
@NamedQueries({
        @NamedQuery(name = "Product.getAll", query = "SELECT e FROM ProductEntity e ORDER BY e.id"),
        @NamedQuery(name = "Product.countAll", query = "SELECT count(e) FROM ProductEntity e")
})
public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sseq")
    @SequenceGenerator(name = "sseq", sequenceName = "product_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 128)
    private String productNaam;

    @Column(length = 128)
    private String naamUser;

    public ProductEntity() {
        // Default constructor
    }

    // Constructor with all MANDATORY fields
    public ProductEntity(long id, String productNaam, String naamUser) {
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

    public void setNaamUser(String naamUser) {
        this.naamUser = naamUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductEntity that = (ProductEntity) o;
        return id == that.id &&
                Objects.equals(productNaam, that.productNaam)&&
                Objects.equals(naamUser, that.naamUser) ;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, productNaam, naamUser);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", naam='" + productNaam + '\'' +
                '}';
    }
}
