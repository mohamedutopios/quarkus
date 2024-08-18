package service;


import dto.ContactDTO;
import entity.Contact;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import repository.ContactRepository;
import java.util.List;
@ApplicationScoped
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public ContactDTO saveContact(ContactDTO contactDTO) {

        //Vérification des champs;

        Contact contact = new Contact();
        contact.setFirstname(contactDTO.getFirstname());
        contact.setLastname(contactDTO.getLastname());
        contact.setPhone(contactDTO.getPhone());
        contact.setEmail(contactDTO.getEmail());
        contactRepository.persist(contact);
        return contact.toDTO();
    }

    public List<ContactDTO> get() {
        return contactRepository.findAll().stream().map((c) -> c.toDTO()).toList();
    }

    public ContactDTO getById(Long id) {
        return contactRepository.findById(id).toDTO();
    }

    @Transactional
    public ContactDTO update(ContactDTO contactDTO) {
        Contact contact = contactRepository.findById(contactDTO.getId());
        contact.setFirstname(contactDTO.getFirstname());
        contact.setLastname(contactDTO.getLastname());
        contact.setPhone(contactDTO.getPhone());
        contact.setEmail(contactDTO.getEmail());
        contactRepository.persistAndFlush(contact);
        return contact.toDTO();
    }

    public boolean isStarted() {
        //Logique pour savoir s'il le service est démarré
        return true;
    }
    public boolean isReady() {
        //Logique pour savoir s'il le service est ready
        return true;
    }
    public boolean isLive() {
        //Logique pour savoir s'il le service est vivant
        return true;
    }
}
