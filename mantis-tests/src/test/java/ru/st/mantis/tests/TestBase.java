package ru.st.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.st.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.io.IOException;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); //передаем тип браузера в конструктор (из свойств или дефолтный)

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config/config_inc.php", "config/config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws IOException {
        app.ftp().restore("config/config_inc.php.bak", "config/config_inc.php");
        app.stop();
    }


}
