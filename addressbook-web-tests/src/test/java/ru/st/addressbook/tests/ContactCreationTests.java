package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreationTests() {

        app.getNavigationHelper().gotoAddNewContact();
        // replacd by  app.getContactHelper().createContact
        // app.getContactHelper().fillContactForm(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1"), true);
        // app.getContactHelper().submitContactCreation();
        // app.getNavigationHelper().returnToHomePage();
        app.getContactHelper().createContact(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1"), true);

    }

}
