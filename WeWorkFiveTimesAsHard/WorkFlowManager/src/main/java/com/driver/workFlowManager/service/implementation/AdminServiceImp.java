package com.driver.workFlowManager.service.implementation;

import com.driver.workFlowManager.model.workers.Admin;
import com.driver.workFlowManager.repository.AdminRepository;
import com.driver.workFlowManager.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImp implements AdminService {
    private AdminRepository adminRepository;

    public AdminServiceImp(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findByUserName(String name) {
            return adminRepository.findByUserName(name);
    }

    @Override
    public boolean existsByUsername(String username) {
        return this.adminRepository.existsByUserName(username);
    }
}
