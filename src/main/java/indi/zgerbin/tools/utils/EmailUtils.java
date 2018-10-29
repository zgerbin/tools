package indi.zgerbin.tools.utils;

import indi.zgerbin.tools.utils.entity.EmailConfig;
import indi.zgerbin.tools.utils.entity.EmailEntity;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

public class EmailUtils {

    private static void send(EmailConfig config, EmailEntity email, boolean SSL, boolean isHtmlMail) {
        String charset = config.getCharset();
        Properties properties = initProperties(config);
        if (SSL) {
            properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.setProperty("mail.smtp.socketFactory.fallback", "false");
            properties.setProperty("mail.smtp.socketFactory.port", config.getEmailPort());
            properties.setProperty("mail.smtp.ssl.enable", "true");
        }

        Session session = Session.getInstance(properties);
        session.setDebug(config.isDebugModel());

        MimeMessage mimeMessage = createEmail(session, email, charset, isHtmlMail);
        Transport transport = null;
        try {
            transport = session.getTransport();
            transport.connect(config.getEmailAccount(), config.getEmailPassword());
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendHtmlMailBySSL(EmailConfig config, EmailEntity email) {
        send(config, email, true, true);
    }

    public static void sendHtmlMail(EmailConfig config, EmailEntity email) {
        send(config, email, false, true);
    }

    public static void sendTextMailBySSL(EmailConfig config, EmailEntity email) {
        send(config, email, true, false);
    }

    public static void sendTextMail(EmailConfig config, EmailEntity email) {
        send(config, email, false, false);
    }

    private static Properties initProperties(EmailConfig config) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", config.getEmailSMTP().toLowerCase());
        properties.setProperty("mail.smtp.host", config.getEmailSMTPHost().toLowerCase());
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.timeout", config.getEmailTimeout());
        properties.setProperty("mail.smtp.port", config.getEmailPort());
        return properties;
    }

    private static MimeMessage createEmail(Session session, EmailEntity email, String charset, boolean isHtmlMail) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);

            for (Map.Entry<String, String> entry : email.getFrom().entrySet()) {
                mimeMessage.setFrom(new InternetAddress(entry.getKey(), entry.getValue(), charset));
            }
            for (Map.Entry<String, String> entry : email.getTo().entrySet()) {
                mimeMessage.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(entry.getKey(), entry
                        .getValue(), charset));
            }
            if (email.getCc() != null) {
                for (Map.Entry<String, String> entry : email.getCc().entrySet()) {
                    mimeMessage.addRecipient(MimeMessage.RecipientType.CC, new InternetAddress(entry.getKey(), entry
                            .getValue(), charset));
                }
            }
            if (email.getBcc() != null) {
                for (Map.Entry<String, String> entry : email.getBcc().entrySet()) {
                    mimeMessage.addRecipient(MimeMessage.RecipientType.BCC, new InternetAddress(entry.getKey(), entry
                            .getValue(), charset));
                }
            }
            mimeMessage.setSubject(email.getSubject(), charset);

            Multipart multipart = new MimeMultipart();

            // 添加邮件正文
            BodyPart contentBodyPart = new MimeBodyPart();
            if (isHtmlMail) {
                contentBodyPart.setContent(email.getContent(), "text/html;charset=" + charset);
            } else {
                ((MimeBodyPart) contentBodyPart).setText(email.getContent(), charset);
            }

            multipart.addBodyPart(contentBodyPart);


            // 添加附件
            if (email.getAttachment() != null) {
                for (String path : email.getAttachment()) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    FileDataSource dataSource = new FileDataSource(path);
                    attachmentBodyPart.setDataHandler(new DataHandler(dataSource));
                    attachmentBodyPart.setFileName(MimeUtility.encodeWord(dataSource.getFile().getName()));
                    multipart.addBodyPart(attachmentBodyPart);
                }
            }

            mimeMessage.setContent(multipart);
            mimeMessage.setSentDate(new Date());
            mimeMessage.saveChanges();
            return mimeMessage;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*public static void main(String[] args) {
        EmailConfig config = new EmailConfig();
        config.setDebugModel(true);
        config.setEmailAccount("zgb320@qq.com");
        config.setEmailPassword("******");
        config.setEmailSMTP("smtp");
        config.setEmailSMTPHost("smtp.qq.com");
        config.setEmailPort("465");
        EmailEntity emailEntity = new EmailEntity();
        Map<String, String> map = new HashMap();
        map.put("zgb320@qq.com", "zgb");
        List<String> list = new ArrayList<>();
        list.add("C:\\Users\\Administrator\\Documents\\my.cnf");
        emailEntity.setFrom(map);
        emailEntity.setTo(map);
        emailEntity.setContent("<div>你不在学校吗？</div><br/><hr/><div>记得28号来学校</div>");
        emailEntity.setSubject("test");
        emailEntity.setAttachment(list);
        sendTextMailBySSL(config, emailEntity);
    }*/


}
