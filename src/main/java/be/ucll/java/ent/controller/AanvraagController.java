package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.AanvraagDTO;
import be.ucll.java.ent.domain.ProductDTO;
import be.ucll.java.ent.model.AanvraagEntity;
import be.ucll.java.ent.model.ProductEntity;
import be.ucll.java.ent.repository.AanvraagDAO;
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
public class AanvraagController {

    @Autowired
    private AanvraagDAO dao;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource msg;

    public long createAanvraag(AanvraagDTO aanvr) throws IllegalArgumentException {
        // Algemene input controle
        if (aanvr == null) {
            //throw new IllegalArgumentException("Alle data vereist voor het aanmaken van een student ontbreekt");
            throw new IllegalArgumentException("Data vereist voor het aanmaken van een aanvraag ontbreekt");
        }

        // Controle op verplichte velden
        if (aanvr.getNaamUser() == null || aanvr.getNaamUser().length() == 0)
            throw new IllegalArgumentException("aanvraag aanmaken gefaald. Naam ontbreekt");

        // Waarde te lang
        if (aanvr.getNaamUser().trim().length() >= 128)
            throw new IllegalArgumentException("aanvraag aanmaken gefaald. Naam langer dan 128 karakters");

        if (aanvr.getProductNaam() == null || aanvr.getProductNaam().length() == 0)
            throw new IllegalArgumentException("aanvraag aanmaken gefaald. Naam ontbreekt");

        // Waarde te lang
        if (aanvr.getProductNaam().trim().length() >= 128)
            throw new IllegalArgumentException("aanvraag aanmaken gefaald. Naam langer dan 128 karakters");

        // Controleer dat er al een student bestaat met dezelfde naam, voornaam en geboortedatum.
        /*List<ProductDTO> lst = this.getStudents(prod.getProductNaam());
        for (ProductDTO pro : lst) {
            if (prod.getProductNaam().equalsIgnoreCase(pro.getProductNaam()) {
                throw new IllegalArgumentException("Er is al een User in de databank met deze gegevens");
            }
        }*/

        AanvraagEntity s = new AanvraagEntity(0L, aanvr.getNaamUser(), aanvr.getProductNaam(), aanvr.getDatum());
        dao.create(s);

        aanvr.setId(s.getId());
        return s.getId();
    }

    public AanvraagDTO getAanvraagById(long AanvraagId) throws IllegalArgumentException {
        if (AanvraagId <= 0L) throw new IllegalArgumentException("Aanvraaag ID ontbreekt");

        Optional<AanvraagEntity> value = dao.get(AanvraagId);
        if (value.isPresent()) {
            return new AanvraagDTO(value.get().getId(), value.get().getNaamUser(), value.get().getProductNaam(), value.get().getDatum());
        } else {
            throw new IllegalArgumentException("Geen aanvraag gevonden met ID: " + AanvraagId);
        }
    }

    public AanvraagDTO getAanvraagByName(String UserNaam) throws IllegalArgumentException {
        if (UserNaam == null) throw new IllegalArgumentException("Ongeldige usernaam meegegeven");
        if (UserNaam.trim().length() == 0) throw new IllegalArgumentException("Geen usernaam meegegeven");

        Optional<AanvraagEntity> value = dao.getOneByName(UserNaam);
        if (value.isPresent()) {
            return new AanvraagDTO(value.get().getId(), value.get().getNaamUser(),value.get().getProductNaam(), value.get().getDatum());
        } else {
            throw new IllegalArgumentException("Geen aanvraag gevonden met naam: " + UserNaam);
        }
    }

    // Update / Modify / Change methods

    public void updateAanvraag(AanvraagDTO aanvr) throws IllegalArgumentException {
        if (aanvr == null) throw new IllegalArgumentException("Aanvraag wijzigen gefaald. Inputdata ontbreekt");
        if (aanvr.getId() <= 0) throw new IllegalArgumentException("Aanvraag wijzigen gefaald. Aanvraag ID ontbreekt");
        if (aanvr.getProductNaam() == null || aanvr.getProductNaam().trim().equals(""))
            throw new IllegalArgumentException("User wijzigen gefaald. Inputdata ontbreekt");
        if (aanvr.getProductNaam().trim().length() >= 128)
            throw new IllegalArgumentException("User wijzigen gefaald. Naam langer dan 128 karakters");

        dao.update(new AanvraagEntity(aanvr.getId(), aanvr.getNaamUser(),aanvr.getProductNaam(), aanvr.getDatum()));
    }

    // Delete methods

    public void deleteAanvraag(long AanvraagId) throws IllegalArgumentException {
        if (AanvraagId <= 0L) throw new IllegalArgumentException("Ongeldig ID");

        getAanvraagById(AanvraagId);

        dao.delete(AanvraagId);
    }


    public List<AanvraagDTO> getAanvragen(String userName) throws IllegalArgumentException {
        if (userName == null)
            throw new IllegalArgumentException("Usernaam opzoeken op naam + voornaam gefaald. Inputdata ontbreekt");

        List<AanvraagDTO> lst = queryListToAanvraagDTOList(dao.getAanvragen(userName));
        return lst;
    }

    public List<AanvraagDTO> getAllStudents() {
        return queryListToAanvraagDTOList(dao.getAll());
    }

    public long countAanvragen() {
        return dao.countAll();
    }

    // private methods

    private List<AanvraagDTO> queryListToAanvraagDTOList(List<AanvraagEntity> lst) {
        Stream<AanvraagDTO> stream = lst.stream()
                .map(rec -> {
                    AanvraagDTO dto = new AanvraagDTO();
                    dto.setId(rec.getId());
                    dto.setNaamUser(rec.getNaamUser());
                    dto.setProductNaam(rec.getProductNaam());
                    dto.setDatum(rec.getDatum());
                    return dto;
                });
        return stream.collect(Collectors.toList());
    }
}
