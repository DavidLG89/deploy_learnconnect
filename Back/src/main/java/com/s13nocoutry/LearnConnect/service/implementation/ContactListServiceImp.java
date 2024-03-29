package com.s13nocoutry.LearnConnect.service.implementation;

import com.s13nocoutry.LearnConnect.models.contactList.ContactList;
import com.s13nocoutry.LearnConnect.models.contactList.ContactListRequest;
import com.s13nocoutry.LearnConnect.models.contactList.ContactListResponse;
import com.s13nocoutry.LearnConnect.models.user.User;
import com.s13nocoutry.LearnConnect.repository.ContactListRepository;
import com.s13nocoutry.LearnConnect.repository.UserRepository;
import com.s13nocoutry.LearnConnect.service.abstraction.ContactListService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactListServiceImp implements ContactListService {
    private final ContactListRepository contactListRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ContactListResponse getById(Long id) {
        ContactList contactList = contactListRepository.findById(id).orElse(null);
        return (contactList != null) ? modelMapper.map(contactList, ContactListResponse.class) : null;
    }

    @Override
    public ContactList getByUserId(Long id) {
        return contactListRepository.findByUser_Id(id);
    }

    @Override
    public List<ContactListResponse> getAllContactList() {
        List<ContactListResponse> contactListResponses = new ArrayList<>();
        for (ContactList contactList : contactListRepository.findAll()) {
            ContactListResponse contactListResponse = modelMapper.map(contactList, ContactListResponse.class);
            contactListResponses.add(contactListResponse);
        }
        return contactListResponses;
    }

    @Override
    public ContactListResponse create(ContactListRequest contactListRequest) {
        ContactList contactList = contactListRepository.save(modelMapper.map(contactListRequest, ContactList.class));
        return modelMapper.map(contactList, ContactListResponse.class);
    }

    @Override
    public ContactListResponse update(Long id, ContactListRequest contactListRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {
        contactListRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return contactListRepository.existsById(id);
    }

    @Override
    public ContactListResponse addContact(Long userId, Long contactId){
        ContactList contactList = contactListRepository.findByUser_Id(userId);
        User user = userRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException("Usuario No encontrado"));
        contactList.getUserList().add(user);
        return modelMapper.map(contactListRepository.save(contactList), ContactListResponse.class);


    }

}
