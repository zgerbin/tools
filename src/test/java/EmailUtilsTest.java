import pers.zgerbin.tools.utils.EmailUtils;
import pers.zgerbin.tools.utils.entity.EmailConfig;
import pers.zgerbin.tools.utils.entity.EmailEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmailUtilsTest {

    public static void main(String[] args) {
        EmailConfig config = new EmailConfig();
        config.setDebugModel(true);
        config.setEmailAccount("zgb320@qq.com");
        config.setEmailPassword("vasytpfskghmbcde");
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
        emailEntity.setContent("<div>你不在？</div><br/><hr/><div>在不？</div>");
        emailEntity.setSubject("test");
        emailEntity.setAttachment(list);
        EmailUtils.sendTextMailBySSL(config, emailEntity);
    }
}
