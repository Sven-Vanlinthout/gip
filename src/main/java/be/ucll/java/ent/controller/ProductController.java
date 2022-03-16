package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.ProductDTO;
import be.ucll.java.ent.model.ProductEntity;
import be.ucll.java.ent.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Transactional
public class ProductController {
   @Autowired
    private ProductDAO dao;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource msg;


    // Create methods

    public long createProdukt(ProductDTO prod) throws IllegalArgumentException {
        // Algemene input controle
        if (prod == null) {
            //throw new IllegalArgumentException("Alle data vereist voor het aanmaken van een student ontbreekt");
            throw new IllegalArgumentException("Data vereist voor het aanmaken van een student ontbreekt");
        }

        // Controle op verplichte velden
        if (prod.getProductNaam() == null || prod.getProductNaam().length() == 0)
            throw new IllegalArgumentException("User aanmaken gefaald. Naam ontbreekt");

        // Waarde te lang
        if (prod.getProductNaam().trim().length() >= 128)
            throw new IllegalArgumentException("User aanmaken gefaald. Naam langer dan 128 karakters");

        // Controleer dat er al een student bestaat met dezelfde naam, voornaam en geboortedatum.
        /*List<ProductDTO> lst = this.getStudents(prod.getProductNaam());
        for (ProductDTO pro : lst) {
            if (prod.getProductNaam().equalsIgnoreCase(pro.getProductNaam()) {
                throw new IllegalArgumentException("Er is al een User in de databank met deze gegevens");
            }
        }*/

        ProductEntity s = new ProductEntity(0L, prod.getProductNaam());
        dao.create(s);

        prod.setId(s.getId());
        return s.getId();
    }

    // Read / get-one methods

    public ProductDTO getProductById(long ProductId) throws IllegalArgumentException {
        if (ProductId <= 0L) throw new IllegalArgumentException("Product ID ontbreekt");

        Optional<ProductEntity> value = dao.get(ProductId);
        if (value.isPresent()) {
            return new ProductDTO(value.get().getId(), value.get().getProductNaam());
        } else {
            throw new IllegalArgumentException("Geen product gevonden met ID: " + ProductId);
        }
    }

    public ProductDTO getProductByName(String productNaam) throws IllegalArgumentException {
        if (productNaam == null) throw new IllegalArgumentException("Ongeldige naam meegegeven");
        if (productNaam.trim().length() == 0) throw new IllegalArgumentException("Geen naam meegegeven");

        Optional<ProductEntity> value = dao.getOneByName(productNaam);
        if (value.isPresent()) {
            return new ProductDTO(value.get().getId(), value.get().getProductNaam());
        } else {
            throw new IllegalArgumentException("Geen student gevonden met naam: " + productNaam);
        }
    }

    // Update / Modify / Change methods

    public void updateProduct(ProductDTO prod) throws IllegalArgumentException {
        if (prod == null) throw new IllegalArgumentException("User wijzigen gefaald. Inputdata ontbreekt");
        if (prod.getId() <= 0) throw new IllegalArgumentException("User wijzigen gefaald. Student ID ontbreekt");
        if (prod.getProductNaam() == null || prod.getProductNaam().trim().equals(""))
            throw new IllegalArgumentException("User wijzigen gefaald. Inputdata ontbreekt");
        if (prod.getProductNaam().trim().length() >= 128)
            throw new IllegalArgumentException("User wijzigen gefaald. Naam langer dan 128 karakters");

        dao.update(new ProductEntity(prod.getId(), prod.getProductNaam()));
    }

    // Delete methods

    public void deleteUSer(long ProductId) throws IllegalArgumentException {
        if (ProductId <= 0L) throw new IllegalArgumentException("Ongeldig ID");

        // First check if this student exists
        // If not the thrown IllegalArgumentException exits out of this method
        getProductById(ProductId);

        // TODO Check of er inschrijvingen zijn voor student. Dan best niet verwijderen.

        // If all is good effectively delete
        dao.delete(ProductId);
    }

    // Search methods

    /*public List<ProductDTO> getProductsByName(String productNaam) throws IllegalArgumentException {
        if (productNaam == null) throw new IllegalArgumentException("Produkt opzoeken op naam gefaald. Inputdata ontbreekt");
        if (productNaam.trim().length() == 0)
            throw new IllegalArgumentException("User opzoeken op naam gefaald. Naam leeg");

       return (queryListToProductDTOList(prodRepo.findAllByNaamContainsIgnoreCaseOrderByNaam(productNaam)));
    }*/

    public List<ProductDTO> getStudents(String produktNaam) throws IllegalArgumentException {
        if (produktNaam == null && produktNaam == null)
            throw new IllegalArgumentException("User opzoeken op naam + voornaam gefaald. Inputdata ontbreekt");

        List<ProductDTO> lst = queryListToProductDTOList(dao.getProducts(produktNaam));
        return lst;
    }

    public List<ProductDTO> getAllStudents() {
        return queryListToProductDTOList(dao.getAll());
    }

    public long countProducts() {
        return dao.countAll();
    }

    // private methods

    private List<ProductDTO> queryListToProductDTOList(List<ProductEntity> lst) {
        Stream<ProductDTO> stream = lst.stream()
                .map(rec -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(rec.getId());
                    dto.setProductNaam(rec.getProductNaam());
                    ;
                    return dto;
                });
        return stream.collect(Collectors.toList());
    }
}
