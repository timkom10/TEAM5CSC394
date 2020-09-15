package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.messaging.Message;
import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.model.workers.Manager;
import com.driver.workFlowManager.model.workers.StandardWorker;
import com.driver.workFlowManager.repository.AdminRepository;
import com.driver.workFlowManager.repository.ManagerRepository;
import com.driver.workFlowManager.repository.StandardWorkerRepository;
import com.driver.workFlowManager.service.MessagingService;
import com.driver.workFlowManager.service.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessagingServiceImp  implements MessagingService {


    private final UserService userService;
    private final StandardWorkerRepository standardWorkerRepository;
    private final ManagerRepository managerRepository;
    private final AdminRepository adminRepository;

    public MessagingServiceImp(UserService userService, StandardWorkerRepository standardWorkerRepository, ManagerRepository managerRepository, AdminRepository adminRepository) {
        this.userService = userService;
        this.standardWorkerRepository = standardWorkerRepository;
        this.managerRepository = managerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    @Transactional
    public void saveMessage(Message message, String from, String to)
    {
        message.setFrom(from);
        message.setTo(to);

        if(this.standardWorkerRepository.existsByUserName(to))
        {
            StandardWorker standardWorker = standardWorkerRepository.findByUserName(to);
            standardWorker.addMessage(message);
            this.standardWorkerRepository.save(standardWorker);
        }
        else if(this.managerRepository.existsByUserName(to))
        {
            Manager manager = this.managerRepository.findByUserName(to);
            manager.addMessage(message);
            this.managerRepository.save(manager);
        }
        else if(this.adminRepository.existsByUserName(to))
        {
            Admin admin = this.adminRepository.findByUserName(to);
            admin.addMessage(message);
            this.adminRepository.save(admin);
        }
    }

    @Override
    @Transactional
    public void deleteMessage(String username, Integer messageID) {
        List<Message> messages = this.getUsersMessages(username);
        if(messages != null) {
            for(Message m : messages)
            {
                if(m.getId().equals(messageID)) {
                    messages.remove(m);
                    this.userService.simpleSaveUserInRoleRepo(username);
                    return;
                }
            }
        }
    }

    @Override
    @Transactional
    public Message getByUsernameAndMessageId(String username, Integer messageId)
    {
        List<Message> messages = this.getUsersMessages(username);
        if(messages != null) {
            for(Message m : messages) {
                if(m.getId().equals(messageId)) { return m; }
            }
        }
        return new Message();
    }

    @Override
    @Transactional
    public List<Message> getUsersMessages(String username) {
        if(this.standardWorkerRepository.existsByUserName(username)) {
            return standardWorkerRepository.findByUserName(username).getMessages();
        }
        else if(this.managerRepository.existsByUserName(username)) {
            return this.managerRepository.findByUserName(username).getMessages();
        }
        else if(this.adminRepository.existsByUserName(username)) {
            return this.adminRepository.findByUserName(username).getMessages();
        }

        return new ArrayList<>();
    }

    @Override
    @Transactional
    public List<Message> getByUserWhereFromIsLike(String username, String fromLike) {
        List<Message>  tempList = getUsersMessages(username);
        List<Message> listFromLike = new ArrayList<>();
        for(Message m : tempList) {
            if(m.getFrom().contains(fromLike)) { listFromLike.add(m); }
        }
        return listFromLike;
    }
}
