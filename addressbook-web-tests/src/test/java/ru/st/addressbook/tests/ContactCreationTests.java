package ru.st.addressbook.tests;

import com.google.gson.Gson;
import org.openqa.selenium.json.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;
import ru.st.addressbook.model.GroupData;
import ru.st.addressbook.model.Groups;

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
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {
            }.getType()); //List<ContactData>.class
            //return contacts.stream().map((g) -> new Object[]{g}).collect(Collectors.toList()).iterator();
            return contacts
                    .stream()
                    .map(ContactData::withDefaultGroups)
                    .map((g) -> new Object[] {g})
                    .collect(Collectors.toList())
                    .iterator();
        }
    }

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();

        if (app.db().groups().size() == 0) {
            app.group().createG(new GroupData()
                    .withName("test2")
                    .withHeader("test3")
                    .withFooter("test4"));
        }
    }

    @Test(dataProvider = "validContactsFromJSON")
    public void testContactCreationTests(ContactData contact) { //before was void testContactCreationTests()

        File photo = new File("src/test/resources/pic1.png");
        Groups groups = app.db().groups();
        // Contacts before = app.contact().allc(); //use ui
        Contacts before = app.db().contacts(); //use db
        app.goTo().gotoAddNewContact();
        app.contact().createC(contact.inGroups(groups.iterator().next()), true);
        assertThat(app.contact().count(), equalTo(before.size() + 1));
        // Contacts after = app.contact().allc(); //use ui
        Contacts after = app.db().contacts(); //use db
        assertThat(after, equalTo(
                before.withAdded(contact.withId(after.stream().mapToInt((u) -> u.getId()).max().getAsInt()))));

        verifyContactListInUI();
    }

    @Test(enabled = false)
    public void testBadContactCreationTests() {

        // Contacts before = app.contact().allc(); //use ui
        Contacts before = app.db().contacts(); //use db
        app.goTo().gotoAddNewContact();
        ContactData user = new ContactData().
                withFirstname("name1'").withMiddlename("middle1'").withLastname("last1").
                withAddress("address1").withHomePhone("123456789").withEmail("mail@mail.qa");
        app.contact().createC(user, true);
        assertThat(app.contact().count(), equalTo(before.size()));
        // Contacts after = app.contact().allc(); //use ui
        Contacts after = app.db().contacts(); //use db
        assertThat(after, equalTo(before));
    }

}