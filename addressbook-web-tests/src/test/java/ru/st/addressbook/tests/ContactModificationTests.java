package ru.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().createC(new ContactData().
                    withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                    withAddress("address1").withHomePhone("123456789").withEmail("mail@mail.qa").
                    withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification() {
        // Contacts before = app.contact().allc(); //use ui
        Contacts before = app.db().contacts(); //use db
        ContactData modifiedUser = before.iterator().next();
        ContactData user = new ContactData().withId(modifiedUser.getId()).
                withFirstname("name01").withMiddlename("middle01").withLastname("last01").
                withAddress("address01").withHomePhone("0123456789").withEmail("0mail@mail.qa").withGroup(null);
        app.contact().modifyC(user);
        //app.goTo().returnToHomePage();
        assertThat(app.contact().count(), equalTo(before.size()));
        // Contacts after = app.contact().allc(); //use ui
        Contacts after = app.db().contacts(); //use db
        assertThat(after, equalTo(before.without(modifiedUser).withAdded(user)));
    }


}