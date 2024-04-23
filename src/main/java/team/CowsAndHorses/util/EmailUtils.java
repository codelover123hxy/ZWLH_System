package team.CowsAndHorses.util;
import jakarta.activation.CommandMap;
import jakarta.activation.DataHandler;
import jakarta.activation.FileDataSource;
import jakarta.activation.MailcapCommandMap;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import team.CowsAndHorses.config.MyAuthenticator;

import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 * 使用javax.mail发送邮件
 *
 * @author: gblfy
 * @date 2021-06-22
 *
 * <p>
 * 参数列表:
 * 1.邮件服务器
 * 2.发件人邮箱
 * 3.发件人的授权密码
 * 4.邮件主题
 * 5.收件人，多个收件人以半角逗号分隔
 * 6.抄送，多个抄送以半角逗号分隔
 * 7.正文，可以用html格式的哟
 * </p>
 */
public class EmailUtils {
    private String smtpHost; // 邮件服务器地址
    private String sendUserName; // 发件人的用户名
    private String sendUserPass; // 发件人密码
    private MimeMessage mimeMsg; // 邮件对象
    private Multipart mp;// 附件添加的组件
    private void init() {
        // 创建一个密码验证器
        MyAuthenticator authenticator = null;
        authenticator = new MyAuthenticator(sendUserName, sendUserPass);
        // 实例化Properties对象
        Properties props = System.getProperties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.auth", "true"); // 需要身份验证
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.starttls.enable", "true");
        // 建立会话
        Session session = Session.getDefaultInstance(props, authenticator);
        // 置true可以在控制台（console)上看到发送邮件的过程
        session.setDebug(true);
        // 用session对象来创建并初始化邮件对象
        mimeMsg = new MimeMessage(session);
        // 生成附件组件的实例
        mp = new MimeMultipart();
    }

    private EmailUtils(String smtpHost, String sendUserName, String sendUserPass, String to, String cc, String mailSubject,
                       String mailBody, List<String> attachments) {
        this.smtpHost = smtpHost;
        this.sendUserName = sendUserName;
        this.sendUserPass = sendUserPass;

        init();
        setFrom(sendUserName);
        setTo(to);
        setCC(cc);
        setBody(mailBody);
        setSubject(mailSubject);
        if (attachments != null) {
            for (String attachment : attachments) {
                System.out.println(attachment);
                addFileAffix(attachment);
            }
        }

    }

    /**
     * 邮件实体
     *
     * @param smtpHost     邮件服务器地址
     * @param sendUserName 发件邮件地址
     * @param sendUserPass 发件邮箱密码
     * @param to           收件人，多个邮箱地址以半角逗号分隔
     * @param cc           抄送，多个邮箱地址以半角逗号分隔
     * @param mailSubject  邮件主题
     * @param mailBody     邮件正文
     * @param attachments  附件路径
     * @return
     */
    public static EmailUtils entity(String smtpHost, String sendUserName, String sendUserPass, String to, String cc,
                                    String mailSubject, String mailBody, List<String> attachments) {
        return new EmailUtils(smtpHost, sendUserName, sendUserPass, to, cc, mailSubject, mailBody, attachments);
    }

    /**
     * 设置邮件主题
     *
     * @param mailSubject
     * @return
     */
    private boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置邮件内容,并设置其为文本格式或HTML文件格式，编码方式为UTF-8
     *
     * @param mailBody
     * @return
     */
    private boolean setBody(String mailBody) {
        try {
            BodyPart bp = new MimeBodyPart();
            bp.setContent("<meta http-equiv=Content-Type content=text/html; charset=UTF-8>" + mailBody,
                    "text/html;charset=UTF-8");
            // 在组件上添加邮件文本
            mp.addBodyPart(bp);
        } catch (Exception e) {
            System.err.println("设置邮件正文时发生错误！" + e);
            return false;
        }
        return true;
    }

    /**
     * 添加一个附件
     *
     * @param filename 邮件附件的地址，只能是本机地址而不能是网络地址，否则抛出异常
     * @return
     */
    public boolean addFileAffix(String filename) {
        try {
            if (filename != null && filename.length() > 0) {
                BodyPart bp = new MimeBodyPart();
                FileDataSource files = new FileDataSource(filename);
                bp.setDataHandler(new DataHandler(files));
                System.out.println("附件名称");
                System.out.println(files.getName());
                bp.setFileName(files.getName());
//                bp.setFileName(MimeUtility.encodeText(files.getName(), "utf-8", null)); // 解决附件名称乱码
                mp.addBodyPart(bp);// 添加附件
            }
        } catch (Exception e) {
            System.err.println("增加邮件附件：" + filename + "发生错误！" + e);
            return false;
        }
        return true;
    }

    /**
     * 设置发件人地址
     *
     * @param from 发件人地址
     * @return
     */
    private boolean setFrom(String from) {
        try {
            mimeMsg.setFrom(new InternetAddress(from));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置收件人地址
     *
     * @param to 收件人的地址
     * @return
     */
    private boolean setTo(String to) {
        if (to == null)
            return false;
        try {
            mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 设置抄送
     *
     * @param cc
     * @return
     */
    private boolean setCC(String cc) {
        if (cc == null) {
            return false;
        }
        try {
            mimeMsg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * no object DCH for MIME type multipart/mixed报错解决
     */
    private void solveError() {
        jakarta.activation.MailcapCommandMap mc = (MailcapCommandMap) jakarta.activation.CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap(
                "multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed; x-java-fallback-entry=true");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);
        Thread.currentThread().setContextClassLoader(EmailUtils.class.getClassLoader());
    }

    /**
     * 发送邮件
     *
     * @return
     */
    public boolean send() throws Exception {
        mimeMsg.setContent(mp);
        mimeMsg.saveChanges();
        System.out.println("正在发送邮件....");
        solveError();
        Transport.send(mimeMsg);
        System.out.println("发送邮件成功！");
        return true;
    }
}


