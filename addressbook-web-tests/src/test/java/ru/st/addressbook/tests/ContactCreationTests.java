package ru.st.addressbook.tests;

import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreationTests()  {

        app.getNavigationHelper().gotoAddNewContact();
        app.getContactHelper().fillContactForm(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa"));
        app.getContactHelper().submitContactCreation();
        app.getContactHelper().returnToHomePage();

    }




}
