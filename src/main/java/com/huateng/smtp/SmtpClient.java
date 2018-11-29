package com.huateng.smtp;

import com.huateng.utils.Prop;
import com.huateng.utils.PropKit;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

/**
 * SMTP客户端
 *
 * @author 陈晓伟 on 2017/2/22
 * @version 1.0
 */
public  class SmtpClient {
    Logger logger = Logger.getLogger(SmtpClient.class);
    private boolean ssl;
    private String to;
    private String cc;
    private String bcc;
    private String from;
    private String replyTo;
    private String subject;
    private String smtpHost;
    private String smtpUsername;
    private String smtpPassword;
    private String smtpProtocol;
    private int smtpPort;
    private boolean smtpDebug;
    private String contentType;

   public SmtpClient() {
       //Read property file
        Prop prop = PropKit.use("mail.properties");
        this.smtpProtocol = prop.get("protocol");
        this.smtpHost = prop.get("host");
        this.smtpPort = prop.getInt("port");
        this.smtpUsername = prop.get("username");
        this.smtpPassword = prop.get("password");
        this.from = prop.get("from");
        this.to = prop.get("to");
        this.contentType = "text/plain";
        this.ssl = prop.getBoolean("ssl");
    }

    /**
     * 创建session
     *
     * @return session
     */
    public Session createSession() throws GeneralSecurityException {
        Properties props = new Properties();
        if (ssl) {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
        }
        String prefix = "mail.smtp";
        String protocol = "smtp";
        if (getSmtpProtocol() != null) {
            protocol = getSmtpProtocol();
            props.put("mail.transport.protocol", protocol);
            prefix = "mail." + protocol;
        }

        if (getSmtpHost() != null) {
            props.put(prefix + ".host", getSmtpHost());
        }

        if (getSmtpPort() > 0) {
            props.put(prefix + ".port", getSmtpPort());
        }
        props.put(prefix + ".auth", "true");
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUsername,smtpPassword);
            }
        };

        Session session = Session.getInstance(props, auth);
        session.setProtocolForAddress("rfc822", protocol);
        session.setDebug(isSmtpDebug());
        return session;
    }

    /**
     * 给指定收件人发邮件
     * @param text 邮件内容
     * @param to 收件人
     */
    public void sendMessage(String text, String to) {
        this.to = to;
        sendMessage(text);
    }

    /**
     * 给指定收件人发邮件
     * @param subject 标题
     * @param text 内容
     * @param to 收件人
     */
    public void sendMessage(String subject, String text, String to) {
        this.to = to;
        this.subject = subject;
        sendMessage(text);
    }

    /**
     * 异步发送邮件
     * @param text 邮件内容
     */
    public void syncSendMessage(final String text) {
        new Thread() {
            @Override
            public void run() {
                sendMessage(text);
            }
        }.run();
    }

    /**
     * 发送邮件
     * @param text 邮件内容
     */
    public void sendMessage(String text) {
        try {
            Message msg = new MimeMessage(this.createSession());
            this.addressMessage(msg);
            if (this.subject != null) {
                try {
                    msg.setSubject(MimeUtility.encodeText(this.subject, "UTF-8", null));
                } catch (UnsupportedEncodingException var3) {
                    logger.error("Unable to encode SMTP subject", var3);
                }
            }
            boolean allAscii = true;

            for (int part = 0; part < text.length() && allAscii; ++part) {
                allAscii = text.charAt(part) <= 127;
            }

            MimeBodyPart mimeBodyPart;
            if (allAscii) {
                mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(text, contentType);
            } else {
                try {
                    ByteArrayOutputStream mp = new ByteArrayOutputStream();
                    OutputStreamWriter var12 = new OutputStreamWriter(MimeUtility.encode(mp, "quoted-printable"), "UTF-8");
                    var12.write(text);
                    var12.close();
                    InternetHeaders var13 = new InternetHeaders();
                    var13.setHeader("Content-Type", contentType + "; charset=UTF-8");
                    var13.setHeader("Content-Transfer-Encoding", "quoted-printable");
                    mimeBodyPart = new MimeBodyPart(var13, mp.toByteArray());
                } catch (Exception var7) {
                    StringBuffer sbuf = new StringBuffer(text);

                    for (int i = 0; i < sbuf.length(); ++i) {
                        if (sbuf.charAt(i) >= 128) {
                            sbuf.setCharAt(i, '?');
                        }
                    }

                    mimeBodyPart = new MimeBodyPart();
                    mimeBodyPart.setContent(sbuf.toString(), contentType);
                }
            }

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            msg.setContent(multipart);
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException var8) {
            LogLog.error("Error occured while sending e-mail notification.", var8);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    protected void addressMessage(Message msg) throws MessagingException {
        if (this.from != null) {
            msg.setFrom(this.getAddress(this.from));
        } else {
            msg.setFrom();
        }

        if (this.replyTo != null && this.replyTo.length() > 0) {
            msg.setReplyTo(this.parseAddress(this.replyTo));
        }

        if (this.to != null && this.to.length() > 0) {
            msg.setRecipients(Message.RecipientType.TO, this.parseAddress(this.to));
        }

        if (this.cc != null && this.cc.length() > 0) {
            msg.setRecipients(Message.RecipientType.CC, this.parseAddress(this.cc));
        }

        if (this.bcc != null && this.bcc.length() > 0) {
            msg.setRecipients(Message.RecipientType.BCC, this.parseAddress(this.bcc));
        }
    }

    InternetAddress getAddress(String addressStr) {
        try {
            return new InternetAddress(addressStr);
        } catch (AddressException var3) {
            this.logger.error("Could not parse address [" + addressStr + "].", var3);
            return null;
        }
    }

    InternetAddress[] parseAddress(String addressStr) {
        try {
            return InternetAddress.parse(addressStr, true);
        } catch (AddressException var3) {
            this.logger.error("Could not parse address [" + addressStr + "].", var3);
            return null;
        }
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpUsername() {
        return smtpUsername;
    }

    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getSmtpProtocol() {
        return smtpProtocol;
    }

    public void setSmtpProtocol(String smtpProtocol) {
        this.smtpProtocol = smtpProtocol;
    }

    public int getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(int smtpPort) {
        this.smtpPort = smtpPort;
    }

    public boolean isSmtpDebug() {
        return smtpDebug;
    }

    public void setSmtpDebug(boolean smtpDebug) {
        this.smtpDebug = smtpDebug;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
