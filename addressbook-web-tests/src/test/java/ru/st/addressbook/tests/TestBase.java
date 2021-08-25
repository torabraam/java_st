package ru.st.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.st.addressbook.appmanager.ApplicationManager;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;
import ru.st.addressbook.model.GroupData;
import ru.st.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);
    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME)); //передаем тип браузера в конструктор (из свойств или дефолтный)

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {

        logger.info("Start test " + m.getName() + " with params " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m) {

        logger.info("Finish test" + m.getName());
    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups dbGroups = app.db().groups(); //список из бд
            Groups uiGroups = app.group().all(); //список из UI
            assertThat(uiGroups, equalTo(dbGroups.stream()
                    .map((g) -> new GroupData().withId(g.getId()).withName(g.getName()))
                    .collect(Collectors.toSet()))); //преобразуем данные из бд во множество с id и name
        }

    }

    public void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts dbContacts = app.db().contacts(); //список из бд
            Contacts uiContacts = app.contact().allc(); //список из UI
            assertThat(uiContacts, equalTo(dbContacts.stream()
                    .map((c) -> new ContactData().withId(c.getId()).withFirstname(c.getFirstname())
                            .withLastname(c.getLastname()).withEmail(c.getEmail()).withAddress(c.getAddress()))
                    .collect(Collectors.toSet()))); //преобразуем данные из бд во множество с id и name
        }

    }

}
