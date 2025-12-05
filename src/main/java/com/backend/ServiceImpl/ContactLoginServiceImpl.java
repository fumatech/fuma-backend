package com.backend.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.Entity.ContactLogin;
import com.backend.Repository.ContactLoginRepo;
import com.backend.Service.ContactLoginService;

@Service
public class ContactLoginServiceImpl implements ContactLoginService {

    @Autowired
    private ContactLoginRepo contactLoginRepo;

    @Override
    public ContactLogin saveContact(ContactLogin contactLogin) {
        return contactLoginRepo.save(contactLogin);
    }

    @Override
    public ContactLogin getContactById(Long id) {
        return contactLoginRepo.findById(id).orElse(null);
    }

    @Override
    public List<ContactLogin> getAllContacts() {
        return contactLoginRepo.findAll();
    }

    @Override
    public ContactLogin updateContact(Long id, ContactLogin contactLogin) {
        ContactLogin existing = contactLoginRepo.findById(id).orElse(null);
        if (existing != null) {
            existing.setType(contactLogin.getType());
            existing.setUserName(contactLogin.getUserName());
            existing.setName(contactLogin.getName());
            existing.setEmail(contactLogin.getEmail());
            existing.setPassword(contactLogin.getPassword());
            return contactLoginRepo.save(existing);
        }
        return null;
    }

    @Override
    public void deleteContact(Long id) {
        contactLoginRepo.deleteById(id);
    }
}
