package ru.st.mantis.appmanager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

    public LoginHelper(ApplicationManager app) {
        super(app);
    }

    public void loginFirstStep(String username) {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), username);
        click(By.xpath("//input[@type='submit']"));
    }

    public void loginSecondStep(String password) {
        type(By.name("password"), password);
        click(By.xpath("//input[@type='submit']"));
    }

    public void login(String username, String password) {
        loginFirstStep(username);
        loginSecondStep(password);
    }
}
