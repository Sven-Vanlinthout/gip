package be.ucll.java.ent.controller;

import be.ucll.java.ent.domain.UserDTO;
import be.ucll.java.ent.model.UserEntity;
import be.ucll.java.ent.repository.UserDAO;
import be.ucll.java.ent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Transactional
public class UserController {

    @Autowired
    private UserDAO dao;

    @Autowired
    private UserRepository studRepo;

    @Autowired
    @Qualifier("messageSource")
    private MessageSource msg;



    public long createStudent(UserDTO user) throws IllegalArgumentException {
        if (user == null) {
            //throw new IllegalArgumentException("Alle data vereist voor het aanmaken van een student ontbreekt");
            throw new IllegalArgumentException("Data vereist voor het aanmaken van een user ontbreekt");
        }


        if (user.getNaam() == null || user.getNaam().length() == 0)
            throw new IllegalArgumentException("User aanmaken gefaald. Naam ontbreekt");
        if (user.getVoornaam() == null || user.getVoornaam().length() == 0)
            throw new IllegalArgumentException("User aanmaken gefaald. Voornaam ontbreekt");
        if (user.getGeboortedatum() == null)
            throw new IllegalArgumentException("User aanmaken gefaald. Geboortedatum ontbreekt");

        if (user.getNaam().trim().length() >= 128)
            throw new IllegalArgumentException("User aanmaken gefaald. Naam langer dan 128 karakters");
        if (user.getVoornaam().trim().length() >= 128)
            throw new IllegalArgumentException("User aanmaken gefaald. Voornaam langer dan 128 karakters");

        if (user.getGeboortedatum().after(new Date()))
            throw new IllegalArgumentException("User aanmaken gefaald. Geboortedatum in de toekomst");

        List<UserDTO> lst = this.getStudents(user.getNaam(), user.getVoornaam());
        for (UserDTO stud : lst) {
            if (user.getNaam().equalsIgnoreCase(stud.getNaam()) &&
                    user.getVoornaam().equalsIgnoreCase(stud.getVoornaam()) && user.getGeboortedatumstr().equalsIgnoreCase(stud.getGeboortedatumstr())) {
                throw new IllegalArgumentException("Er is al een User in de databank met deze gegevens");
            }
        }

        UserEntity s = new UserEntity(0L, user.getNaam(), user.getVoornaam(), user.getGeboortedatum(), user.getAdres(), user.getTelefoon());
        dao.create(s);

        user.setId(s.getId());
        return s.getId();
    }


    public UserDTO getUserById(long userId) throws IllegalArgumentException {
        if (userId <= 0L) throw new IllegalArgumentException("User ID ontbreekt");

        Optional<UserEntity> value = dao.get(userId);
        if (value.isPresent()) {
            return new UserDTO(value.get().getId(), value.get().getNaam(), value.get().getVoornaam(), value.get().getGeboortedatum(), value.get().getadres(), value.get().getTelefoon());
        } else {
            throw new IllegalArgumentException("Geen user gevonden met ID: " + userId);
        }
    }

    public UserDTO getUserByName(String userName) throws IllegalArgumentException {
        if (userName == null) throw new IllegalArgumentException("Ongeldige naam meegegeven");
        if (userName.trim().length() == 0) throw new IllegalArgumentException("Geen naam meegegeven");

        Optional<UserEntity> value = dao.getOneByName(userName);
        if (value.isPresent()) {
            return new UserDTO(value.get().getId(), value.get().getNaam(), value.get().getVoornaam(), value.get().getGeboortedatum(), value.get().getadres(), value.get().getTelefoon());
        } else {
            throw new IllegalArgumentException("Geen student gevonden met naam: " + userName);
        }
    }

    public UserDTO getUserByUserName(String userName){
        Optional<UserEntity> value = dao.getOneByName(userName);
        if (value.isPresent()) {
            return new UserDTO(value.get().getId(), value.get().getNaam(), value.get().getVoornaam(), value.get().getGeboortedatum(), value.get().getadres(), value.get().getTelefoon());
        } else {
            return null;
        }
    }


    public void updateUser(UserDTO user) throws IllegalArgumentException {
        if (user == null) throw new IllegalArgumentException("User wijzigen gefaald. Inputdata ontbreekt");
        if (user.getId() <= 0) throw new IllegalArgumentException("User wijzigen gefaald. Student ID ontbreekt");
        if (user.getNaam() == null || user.getNaam().trim().equals(""))
            throw new IllegalArgumentException("User wijzigen gefaald. Inputdata ontbreekt");
        if (user.getVoornaam() == null || user.getVoornaam().trim().equals(""))
            throw new IllegalArgumentException("User wijzigen gefaald. Inputdata ontbreekt");
        if (user.getNaam().trim().length() >= 128)
            throw new IllegalArgumentException("User wijzigen gefaald. Naam langer dan 128 karakters");
        if (user.getVoornaam().trim().length() >= 128)
            throw new IllegalArgumentException("User wijzigen gefaald. Naam langer dan 128 karakters");
        if (user.getGeboortedatum() == null)
            throw new IllegalArgumentException("User wijzigen gefaald. Geboortedatum ontbreekt");
        if (user.getGeboortedatum().after(new Date()))
            throw new IllegalArgumentException("User wijzigen gefaald. Geboortedatum in de toekomst");


        dao.update(new UserEntity(user.getId(), user.getNaam(), user.getVoornaam(), user.getGeboortedatum(), user.getAdres(), user.getTelefoon()));
    }

    // Delete methods

    public void deleteStudent(long userId) throws IllegalArgumentException {
        if (userId <= 0L) throw new IllegalArgumentException("Ongeldig ID");

        getUserById(userId);

        dao.delete(userId);
    }


    public List<UserDTO> getUsersByName(String naam) throws IllegalArgumentException {
        if (naam == null) throw new IllegalArgumentException("User opzoeken op naam gefaald. Inputdata ontbreekt");
        if (naam.trim().length() == 0)
            throw new IllegalArgumentException("User opzoeken op naam gefaald. Naam leeg");

        return (queryListToStudentDTOList(studRepo.findAllByNaamContainsIgnoreCaseOrderByNaam(naam)));
    }

    public List<UserDTO> getStudents(String naam, String voornaam) throws IllegalArgumentException {
        if (naam == null && voornaam == null)
            throw new IllegalArgumentException("User opzoeken op naam + voornaam gefaald. Inputdata ontbreekt");
        if (voornaam == null && naam != null && naam.trim().length() == 0)
            throw new IllegalArgumentException("User opzoeken op naam + voornaam gefaald. Naam leeg");
        if (naam == null && voornaam != null && voornaam.trim().length() == 0)
            throw new IllegalArgumentException("USer opzoeken op naam + voornaam gefaald. Voornaam leeg");

        List<UserDTO> lst = queryListToStudentDTOList(dao.getUsers(naam, voornaam));
        return lst;
    }

    public List<UserDTO> getAllStudents() {
        return queryListToStudentDTOList(dao.getAll());
    }

    public long countStudents() {
        return dao.countAll();
    }


    private List<UserDTO> queryListToStudentDTOList(List<UserEntity> lst) {
        Stream<UserDTO> stream = lst.stream()
                .map(rec -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(rec.getId());
                    dto.setNaam(rec.getNaam());
                    dto.setVoornaam(rec.getVoornaam());
                    dto.setGeboortedatum(rec.getGeboortedatum());
                    dto.setAdres(rec.getadres());
                    dto.setTelefoon(rec.getTelefoon());
                    return dto;
                });
        return stream.collect(Collectors.toList());
    }
}



