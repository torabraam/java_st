package ru.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().allc().size() == 0) {
            app.contact().createC(new ContactData().
                    withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                    withAddress("address1").withHomeph("123456789").withEmail("mail@mail.qa").
                    withGroup("test1"), true);
        }
    }

    @Test (enabled = false)
    public void testContactPhones(){
        app.goTo().returnToHomePage();
        ContactData contact = app.contact().allc().iterator().next();
       // ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
    }

}
