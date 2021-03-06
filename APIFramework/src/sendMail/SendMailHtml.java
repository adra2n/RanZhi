package sendMail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
/*杨凯静*/
import javax.mail.internet.MimeMultipart;

public class SendMailHtml {
	//发件人地址
    public static String senderAddress = "sunliying666@yeah.net";
    //收件人地址
    public static String recipientAddress = "736624557@qq.com";
    //发件人账户名
    public static String senderAccount = "sunliying666@yeah.net";
    //发件人账户密码
    public static String senderPassword = "sunliying666";
//    以正文的形式发送
    public static void main(String[] args) throws Exception {
        //1、连接邮件服务器的参数配置
        Properties props = new Properties();
        //设置用户的认证方式
        props.setProperty("mail.smtp.auth", "true");
        //设置传输协议
        props.setProperty("mail.transport.protocol", "smtp");
        //设置发件人的SMTP服务器地址
        props.setProperty("mail.smtp.host", "smtp.yeah.net");
        //2、创建定义整个应用程序所需的环境信息的 Session 对象
        Session session = Session.getInstance(props);
        //设置调试信息在控制台打印出来
        session.setDebug(true);
        //3、创建邮件的实例对象
        Message msg = getMimeMessage(session);
        //4、根据session对象获取邮件传输对象Transport
        Transport transport = session.getTransport();
        //设置发件人的账户名和密码
        transport.connect(senderAccount, senderPassword);
        //发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(msg,msg.getAllRecipients());
         
        //如果只想发送给指定的人，可以如下写法
        //transport.sendMessage(msg, new Address[]{new InternetAddress("xxx@qq.com")});
         
        //5、关闭邮件连接
        transport.close();
    }
    /**
     * 获得创建一封邮件的实例对象
     * @param session
     * @return
     * @throws MessagingException
     * @throws AddressException
     */
    public static MimeMessage getMimeMessage(Session session) throws Exception{
        //创建一封邮件的实例对象
        MimeMessage msg = new MimeMessage(session);
//        Message msg = new MimeMessage(session, new FileInputStream(new File("C:/Users/H__D/Desktop/demo.eml")));
        //设置发件人地址
        msg.setFrom(new InternetAddress(senderAddress));
        /**
         * 设置收件人地址（可以增加多个收件人、抄送、密送），即下面这一行代码书写多行
         * MimeMessage.RecipientType.TO:发送
         * MimeMessage.RecipientType.CC：抄送
         * MimeMessage.RecipientType.BCC：密送
         */
        msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(recipientAddress));
        //设置邮件主题
        msg.setSubject("邮件主题","UTF-8");
        //设置邮件正文
        Multipart m = new MimeMultipart();
        List<String> fileUrl = new ArrayList<String>();
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\index.html");
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\overview.html");
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\reportng.css");
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\reportng.js");
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\sorttable.js");
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\suite1_test1_results.html");
        fileUrl.add("F:\\JavaEE-Program\\UI_Test_Framework\\test-output\\html\\suites.html");
        if (fileUrl != null && fileUrl.size() > 0) {
		    for (int i = 0; i < fileUrl.size(); i++) {
		        BodyPart body = new MimeBodyPart();
		        FileDataSource fds = new FileDataSource(fileUrl.get(i)); 
		        body.setDataHandler(new DataHandler(fds)); 
//		        bp.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
		        m.addBodyPart(body); 
		    }
        }
//        DataSource source = new FileDataSource(new File("F:\\QQ下载文档\\setZip\\index.html"));
//        body.setDataHandler(new DataHandler(source));
//        m.addBodyPart(body);
        msg.setContent(m, "file");
//        msg.setContent("简单的纯文本邮件！HelloWorld", "text/html;charset=UTF-8");
        //设置邮件的发送时间,默认立即发送
        msg.setSentDate(new Date());
        return msg;
    }
}
