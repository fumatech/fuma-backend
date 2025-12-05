package com.backend.Service;

import java.util.List;

import com.backend.Entity.ContactLogin;

public interface ContactLoginService {
    ContactLogin saveContact(ContactLogin contactLogin);
    ContactLogin getContactById(Long id);
    List<ContactLogin> getAllContacts();
    ContactLogin updateContact(Long id, ContactLogin contactLogin);
    void deleteContact(Long id);
}
