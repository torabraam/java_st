package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreationTests()  {

        app.gotoAddNewContact();
        app.fillContactForm(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa"));
        app.submitContactCreation();
        app.returnToHomePage();
        app.logout();
    }




}
