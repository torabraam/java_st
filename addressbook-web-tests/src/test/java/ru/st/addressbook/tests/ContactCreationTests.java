package ru.st.addressbook.tests;

import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validContactsFromJSON() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
        }.getType()); //List<ContactData>.class
        return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
    }

    @Test (dataProvider = "validContactsFromJSON")
    public void testContactCreationTests(ContactData contact) { //before was void testContactCreationTests()

        File photo = new File("src/test/resources/pic1.png");
        Contacts before = app.contact().allc();
        app.goTo().gotoAddNewContact();
        //before dataProvider
/*        ContactData contact = new ContactData().
                withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                withAddress("address1").withHomePhone("123456789").withEmail("mail@mail.qa").
                withGroup("test1").withPhoto(photo);*/
        app.contact().createC(contact, true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        Contacts after = app.contact().allc();
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));
    }

    @Test (enabled = false)
    public void testBadContactCreationTests() {

        Contacts before = app.contact().allc();
        app.goTo().gotoAddNewContact();
        ContactData user = new ContactData().
                withFirstname("name1'").withMiddlename("middle1'").withLastname("last1").
                withAddress("address1").withHomePhone("123456789").withEmail("mail@mail.qa").
                withGroup("test1");
        app.contact().createC(user, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.contact().allc();
        assertThat(after, equalTo(before));
    }

}