package com.MainDriver.WorkFlowManager.controller;

import com.MainDriver.WorkFlowManager.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/*
    Any authenticated user can view an announcement (if they have one), managers and admins
    Can send an announcement
 */

@Controller
public class AnnouncementController {

    @Autowired
    AnnouncementService announcementService;



}
