package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1"), true);
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("name01", "middle01", "last01", "address01", "0123456789", "0mail@mail.qa", null), false);
        app.getContactHelper().submitContactModification();
    }
}

