package no.ntnu.bicycle.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService{
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String body) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("keep.rolling.rolling.rolling16@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);

        System.err.println("Message sent");

    }

    public void sendAdvancedEmail(String toEmail, String subject, String body) throws MessagingException {
        JavaMailSender javaMailSender = new JavaMailSenderImpl();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");


        //mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
        helper.setText(body, true); // Use this or above line.
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setFrom("keep.rolling.rolling.rolling16@gmail.com");
        javaMailSender.send(mimeMessage);

        System.err.println("Message sent");
    }
}
