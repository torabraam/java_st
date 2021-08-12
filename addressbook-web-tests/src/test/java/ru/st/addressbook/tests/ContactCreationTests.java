package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreationTests() {

        Contacts before = app.contact().allc();
        app.goTo().gotoAddNewContact();
        ContactData user = new ContactData().
                withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                withAddress("address1").withHomeph("123456789").withEmail("mail@mail.qa").
                withGroup("test1");
        app.contact().createC(user, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().allc();
        assertThat(after, equalTo(
                before.withAdded(user.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));
    }

    @Test
    public void testBadContactCreationTests() {

        Contacts before = app.contact().allc();
        app.goTo().gotoAddNewContact();
        ContactData user = new ContactData().
                withFirstname("name1'").withMiddlename("middle1'").withLastname("last1").
                withAddress("address1").withHomeph("123456789").withEmail("mail@mail.qa").
                withGroup("test1");
        app.contact().createC(user, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().allc();
        assertThat(after, equalTo(before));
    }

}