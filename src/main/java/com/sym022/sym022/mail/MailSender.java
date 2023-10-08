package com.sym022.sym022.mail;

import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailSender {
    private static final Logger log	= Logger.getLogger(MailSender.class);
    public static boolean sendMail(Mail mail) throws MessagingException {

        boolean sendFlag = false;

        final String username = "SYM022_Safety@outlook.com";
        final String password = "Sym@1234";



        // Check nick and replyTo
        if(mail.getNick() == null || mail.getNick().equals(""))
            mail.setNick(mail.getFrom());
        if(mail.getReplyTo() == null || mail.getReplyTo().equals(""))
            mail.setReplyTo(mail.getFrom());

        // Debug
        for(String s : mail.getListTo())
            log.debug("To: " + s);
        log.debug("Subject: " + mail.getSubject());
        log.debug("MsgBody: " + mail.getMsgBody());
        log.debug("From: " + mail.getFrom());
        log.debug("Nick: " + mail.getNick());
        log.debug("ReplyTo: " + mail.getReplyTo());

        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp-mail.outlook.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");//TLS
        props.put("mail.smtp.ssl.trust","*");
        props.put("mail.transport.protocol", "smtp");
        /*
         props.put("mail.smtp.starttls.required", "true");
         props.put("mail.smtp.sasl.enable", "false");
         props.put("mail.smtp.user", username);
         props.put("mail.smtp.password", password);
        */

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        //setter le mail
        try {
            MimeMessage msg = new MimeMessage(session);

            // ********* attach **********
            // Set Subject: header field
            msg.setSubject(mail.getSubject());

            // Create the message part
            MimeBodyPart messageBodyPartMsg = new MimeBodyPart();

            // Fill the message
            messageBodyPartMsg.setText(mail.getMsgBody(),"UTF-8");

            // Part two is attachment
            MimeBodyPart messageBodyPartPDF = new MimeBodyPart();
            String filename = mail.getFilename();
            log.info(filename);
            DataSource source = new FileDataSource("C:\\Users\\devog\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\"+filename);
            //DataSource source = new FileDataSource("C:\\Users\\debet\\IdeaProjects\\SYM022\\src\\main\\webapp\\PDF\\"+filename);
            log.info(source.toString());
            messageBodyPartPDF.setDataHandler(new DataHandler(source));
            messageBodyPartPDF.setFileName(filename);
            //messageBodyPartPDF.attachFile("C:\\Users\\debet\\IdeaProjects\\LocaCar\\src\\main\\webapp\\css\\PDF\\Reservation_.pdf");

            // Create a multipart message
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPartMsg);
            multipart.addBodyPart(messageBodyPartPDF);



            if(mail.isEncodeUTF8()) {

                msg.setHeader("Content-Type", "text/html;charset=\"UTF-8\"");
                msg.setHeader("Content-Transfert-Encoding", "8bit");
            }

            msg.setFrom(new InternetAddress(mail.getFrom(), mail.getNick()));

            for(String email : mail.getListTo()) {

                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            }

            msg.setReplyTo(new Address[]{new InternetAddress(mail.getReplyTo())});
            msg.setSubject(mail.getSubject());
            msg.setText(mail.getMsgBody());
            //Send the complete message parts
            msg.setContent(multipart );
            Transport.send(msg);

            sendFlag = true;

        } catch (AddressException e) {

            //e.printStackTrace();
            log.error("AddressException\n" + e.toString());
        } catch (MessagingException e) {

            //e.printStackTrace();
            log.error("MessagingException\n" + e.toString());
        } catch (UnsupportedEncodingException e) {

            //e.printStackTrace();
            log.error("UnsupportedEncodingException\n" + e.toString());
        }

        return sendFlag;
    }
}
