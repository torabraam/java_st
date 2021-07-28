package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

    @Test
    public void testContactModification() {
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("name01", "middle01", "last01", "address01", "0123456789", "0mail@mail.qa", null), false);
        app.getContactHelper().submitContactModification();
    }
}

