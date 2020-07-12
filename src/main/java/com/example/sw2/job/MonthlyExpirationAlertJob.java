package com.example.sw2.job;

import com.example.sw2.entity.Usuarios;
import com.example.sw2.repository.InventarioRepository;
import com.example.sw2.repository.UsuariosRepository;
import com.example.sw2.utils.CustomMailService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.mail.MessagingException;
import java.util.ArrayList;

public class MonthlyExpirationAlertJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(MonthlyExpirationAlertJob.class);

    @Autowired
    CustomMailService customMailService;
    @Autowired
    UsuariosRepository usuariosRepository;
    @Autowired
    InventarioRepository inventarioRepository;

    private static final int ROL = 2; //A gestores
    
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());

        ArrayList<String> emails = new ArrayList<>();
        for(Usuarios u : usuariosRepository.findUsuariosByRoles_idroles(ROL)){
            emails.add(u.getCorreo());
        }
        String[] strs = new String[emails.size()];
        try {
            customMailService.sendProductExpiration(
                    inventarioRepository.findAll(), emails.toArray(strs)
            );
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
