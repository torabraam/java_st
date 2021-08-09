package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (!app.getContactHelper().isThereAContact()) {
            app.getContactHelper().createContact(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1"), true);
        }
        List<ContactData> before = app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(before.size()-1);
        ContactData user = new ContactData(before.get(before.size()-1).getId(), "name01", "middle01", "last01", "address01", "0123456789", "0mail@mail.qa", null);
        app.getContactHelper().fillContactForm(user, false);
        app.getContactHelper().submitContactModification();
        app.goTo().returnToHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(user);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}

