package ru.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().createC(new ContactData().
                    withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                    withAddress("address1").withHomePhone("123456789").withEmail("mail@mail.qa"), true);
        }
    }

    @Test
    public void testContactDeletion() {
        // Contacts before = app.contact().allc(); //use ui
        Contacts before = app.db().contacts(); //use db
        ContactData deletedUser = before.iterator().next(); //вернет какой-н элемент множества
        app.contact().deleteC(deletedUser);
        app.goTo().returnToHomePage();
        assertThat(app.contact().count(), equalTo(before.size()-1));
        // Contacts after = app.contact().allc(); //use ui
        Contacts after = app.db().contacts(); //use db
        assertThat(after, equalTo(before.without(deletedUser)));

        verifyContactListInUI();
    }
}