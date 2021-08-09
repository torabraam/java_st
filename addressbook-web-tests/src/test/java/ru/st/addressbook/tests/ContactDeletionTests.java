package ru.st.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;

import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().allc().size() == 0) {
            app.contact().createC(new ContactData().
                    withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                    withAddress("address1").withHomeph("123456789").withEmail("mail@mail.qa").
                    withGroup("test1"), true);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().allc();
        ContactData deletedUser = before.iterator().next(); //вернет какой-н элемент множества
        app.contact().deleteC(deletedUser);
        app.goTo().returnToHomePage();
        Contacts after = app.contact().allc();
        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.without(deletedUser)));
    }
}
