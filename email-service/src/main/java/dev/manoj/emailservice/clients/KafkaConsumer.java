package dev.manoj.emailservice.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.manoj.emailservice.dtos.UserDTO;
import dev.manoj.emailservice.utility.EmailUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Service
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {
    private EmailUtil emailUtil;
    private ObjectMapper objectMapper;
    @KafkaListener(topics = "sendEmail",
                    groupId = "emailService")
    public void consumeMessage(String message) throws JsonProcessingException {
        UserDTO userDTO = objectMapper.readValue(message, UserDTO.class);
        System.out.println("USER DTO "+userDTO.getEmail());

        final String fromEmail = "manojhari125@gmail.com";
        final String password = "reophistvoybxmql";




        System.out.println("TLSEmail Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);

        emailUtil.sendEmail(session,
                "hmanoj12@gmail.com",
                "Welcome to Friends Electricals!!",
                "Thanks for visiting our website. Enjoy shopping..");

    }
}
