package com.example.sw2.utils;

import com.example.sw2.entity.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;

@Component
public class CustomMailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendEmail() {

		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("to_1@gmail.com", "to_2@gmail.com", "to_3@yahoo.com");

		msg.setSubject("Testing from Spring Boot");
		msg.setText("Hello World \n Spring Boot Email");

		javaMailSender.send(msg);

	}

	public void sendTestEmailWithAtachment() throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		// true = multipart message
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo("gustavomeza27@gmail.com");
		helper.setSubject("Testing from Spring Boot");
		// default = text/plain
		//helper.setText("Check attachment for image!");
		// true = text/html
		helper.setText("<h1>Check attachment for image!</h1>", true);
		FileSystemResource file = new FileSystemResource(new File("/Users/Gustavo_Meza/Desktop/aaa/test.txt"));
		helper.addAttachment("test.txt", file);
		javaMailSender.send(msg);

	}

	public void sendEmailWithAtachment(String to, String subject, String title, String message, FileSystemResource file) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(message);
		helper.setText("<h1>"+title+"</h1>", true);
		helper.addAttachment(file.getFilename(), file);
		javaMailSender.send(msg);
	}

	public void sendEmail(String to, String subject, String title, String message) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText("<h1>"+title+"</h1>", true);
		helper.setText(message);
		javaMailSender.send(msg);
	}

	public void sendEmailPassword(Usuarios u) throws MessagingException, IOException {
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(u.getCorreo());
		helper.setSubject("Confirmación de cuenta");
		helper.setText("<h1>Bienvenido " + u.getFullname() + ", usuario "+u.getRoles().getNombrerol().toUpperCase()+" </h1>", true);
		helper.setText("Este es un mensaje de confirmación de cuenta, para ingresar al sistema use la siguiente contraseña:\n"+u.generateNewPassword());
		javaMailSender.send(msg);
	}


}

