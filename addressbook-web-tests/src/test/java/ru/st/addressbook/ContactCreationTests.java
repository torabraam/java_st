package ru.st.addressbook;

import org.testng.annotations.Test;

public class ContactCreationTests extends TestBase{

    @Test
    public void testContactCreationTests()  {

        gotoAddNewContact();
        fillContactForm(new ContactData("name1", "middle1", "last1", "address1", "123456789", "mail@mail.qa"));
        submitContactCreation();
        returnToHomePage();
        logout();
    }




}
