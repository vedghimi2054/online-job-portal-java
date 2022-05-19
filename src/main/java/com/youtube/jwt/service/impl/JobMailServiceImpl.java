package com.youtube.jwt.service.impl;

import com.youtube.jwt.dao.ApplyJobRepository;
import com.youtube.jwt.entity.ApplyJob;
import com.youtube.jwt.exception.NotFoundException;
import com.youtube.jwt.util.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class JobMailServiceImpl {
    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;

    private ApplyJobRepository applyJobRepository;

    public JobMailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, ApplyJobRepository applyJobRepository) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.applyJobRepository = applyJobRepository;
    }

    public boolean SendMail(ApplyJob applyJob,String body,String subject) throws MessagingException {
        MimeMessage mimeMailMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(mimeMailMessage);
        helper.setTo(applyJob.getEmail());
        helper.setSubject(subject);
        helper.setText(body,true);
        helper.setFrom(MailUtils.USERNAME);

        try{
            javaMailSender.send(mimeMailMessage);
        }
        catch (MailException me){
            log.error("Error sending mail",me);
            throw new NotFoundException("Error sending mail");
        }
        return true;
    }
    public boolean sendNotificationToUsers(ApplyJob applyJob) throws MessagingException {
        if(applyJob.getEmail()==null){
            throw new NotFoundException("Email Address not found");
        }
        Context context=new Context();
        context.setVariable("title","Dear"+ " " +applyJob.getFirstName()+ ", ");
        context.setVariable("body","Your resume has been successfully received. " +
                "Thank you for posting it. " +
                "If you pass through our initial screening process, " +
                "our HR will get in touch with you in the future for further steps. " +
                "With Regards, ITGLance pvt.ltd. This is an automated reply. " +
                "Please do not send emails to this email address.");
        String body=templateEngine.process("notification_template",context);
        return SendMail(applyJob,body,"Thank you for posting your resume");
    }
    @Async
    public boolean sendContactMail(String body, String subject) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo("ghimires2054@gmail.com");
        helper.setSubject("Information About Query");
        helper.setText(body,true);
        helper.setFrom(MailUtils.USERNAME);

        try {
            javaMailSender.send(mimeMessage);
        } catch (MailException ex){
            log.error("error seding mail: ", ex.getMessage());
            throw new NotFoundException("Error sending mail");
        }
        return true;
    }

}
