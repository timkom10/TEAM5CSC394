package com.MainDriver.WorkFlowManager.service.implementation;

import com.MainDriver.WorkFlowManager.model.workers.Admin;
import com.MainDriver.WorkFlowManager.repository.AdminRepository;
import com.MainDriver.WorkFlowManager.service.AdminService;
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
