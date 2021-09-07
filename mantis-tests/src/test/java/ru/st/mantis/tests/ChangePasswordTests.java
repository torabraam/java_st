package ru.st.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.st.mantis.model.MailMessage;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.List;
import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {
    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, ServiceException {
       // skipIfNotFixed(2); //check issue status
        app.login().login("administrator", "root");
        app.cp().goToSettings();
        app.cp().selectUserManagementTab();
        app.cp().openLastUserPage();

        String user = app.cp().getUsernameOnUserPage();
        String email = app.cp().getEmailOnUserPage();
        String newPassword = "newpassword";

        app.cp().resetUserPassword();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 30000);

        String resetPasswordLink = findResetPasswordLink(mailMessages, email);
        app.cp().editPasswordByLink(resetPasswordLink, newPassword);

        assertTrue(app.newSession().login(user, newPassword));
    }

    private String findResetPasswordLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
