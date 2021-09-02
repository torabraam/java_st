package ru.st.mantis.tests;

import org.testng.annotations.Test;
import ru.st.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends TestBase {

    @Test
    public void testLogin() throws IOException {
        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.isLoggedOnAs("administrator"));


    }

}
