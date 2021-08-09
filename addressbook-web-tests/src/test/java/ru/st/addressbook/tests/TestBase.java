package ru.st.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.st.addressbook.appmanager.ApplicationManager;

public class TestBase {

    protected static final ApplicationManager app = new ApplicationManager(BrowserType.FIREFOX); //передаем тип браузера в конструктор

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

}
