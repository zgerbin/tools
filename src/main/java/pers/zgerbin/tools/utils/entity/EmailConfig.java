package pers.zgerbin.tools.utils.entity;

public class EmailConfig {

    private String emailAccount;
    private String emailPassword;
    private String emailSMTPHost;
    private String emailTimeout;
    private String emailPort;
    private String emailSMTP;
    private String charset;
    private boolean debugModel;

    public EmailConfig() {
        setEmailPort("25");
        setDebugModel(false);
        setEmailTimeout("3000");
        setCharset("UTF-8");
    }

    public String getEmailAccount() {
        return emailAccount;
    }

    public void setEmailAccount(String emailAccount) {
        this.emailAccount = emailAccount;
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword;
    }

    public String getEmailSMTPHost() {
        return emailSMTPHost;
    }

    public void setEmailSMTPHost(String emailSMTPHost) {
        this.emailSMTPHost = emailSMTPHost;
    }

    public String getEmailTimeout() {
        return emailTimeout;
    }

    public void setEmailTimeout(String emailTimeout) {
        this.emailTimeout = emailTimeout;
    }

    public String getEmailPort() {
        return emailPort;
    }

    public void setEmailPort(String emailPort) {
        this.emailPort = emailPort;
    }

    public String getEmailSMTP() {
        return emailSMTP;
    }

    public void setEmailSMTP(String emailSMTP) {
        this.emailSMTP = emailSMTP;
    }

    public boolean isDebugModel() {
        return debugModel;
    }

    public void setDebugModel(boolean debugModel) {
        this.debugModel = debugModel;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}

