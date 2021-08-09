package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {


        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(before.size() - 1);
        app.getContactHelper().deleteSelectedContacts();
        app.goTo().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(before.size() - 1);
        Assert.assertEquals(before, after);


    }
}
