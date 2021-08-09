package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreationTests() {


        List<ContactData> before = app.getContactHelper().getContactList();
        app.goTo().gotoAddNewContact();
        ContactData user = new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa", "test1");
        app.getContactHelper().createContact(user, true);
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size() + 1);

        before.add(user);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
