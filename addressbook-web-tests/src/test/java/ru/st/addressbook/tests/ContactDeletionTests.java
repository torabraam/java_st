package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {

        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1"), true);
        }
        app.getContactHelper().selectContact();
        app.getContactHelper().deleteSelectedContacts();
        app.getNavigationHelper().returnToHomePage();


    }
}
