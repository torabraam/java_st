package ru.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase {

    @BeforeMethod(enabled = true)
    public void ensurePreconditions() {
        if (app.contact().allc().size() == 0) {
            app.contact().createC(new ContactData().
                    withFirstname("name1").withMiddlename("middle1").withLastname("last1").
                    withAddress("address1").withHomePhone("123-123").withMobilePhone("6(7)").withWorkPhone("").
                    withEmail("mail@mail.qa").withEmail2("").withEmail3("mail@)@mail.qa"), true);
        }
    }

    @Test(enabled = true)
    public void testContactPhones() {

        ContactData contact = app.contact().allc().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));

    }

    @Test(enabled = true)
    public void testContactEmails() {
        ContactData contact = app.contact().allc().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));

    }

    @Test(enabled = true)
    public void testContactAddress() {
        ContactData contact = app.contact().allc().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);
        assertThat(contact.getAddress(), equalTo(cleanedA(contactInfoFromEditForm.getAddress())));
        //1 - AR=contact, 2 - ER=editcont
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactInfoTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactInfoTests::cleanedE)
                .collect(Collectors.joining("\n"));
    }


    public static String cleaned(String ph) {
        return ph.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    public static String cleanedE(String e) {
        return e.replaceAll("\\s", "");
    }

    public static String cleanedA (String a) {
        return a.replaceAll("\\u0020{2,}", " ");
    }

}