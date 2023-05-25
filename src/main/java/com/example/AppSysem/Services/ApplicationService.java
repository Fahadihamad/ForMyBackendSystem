package com.example.AppSysem.Services;

import com.example.AppSysem.Entity.Application;
import com.example.AppSysem.Entity.Massjid_build;
import com.example.AppSysem.Exception.ResourceNotFoundException;
import com.example.AppSysem.Repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class ApplicationService {

    public ApplicationRepository applicationRepository;

    public Application createNewApplication(Application application) {

        return applicationRepository.save(application);
    }

    public List<Application> getApplication() {

        return applicationRepository.findAll();
    }


}