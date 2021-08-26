package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.GroupData;
import ru.st.addressbook.model.Groups;

public class RemoveContactFromGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.contact().createC(new ContactData()
                    .withFirstname("first Name")
                    .withLastname("last name")
                    .withMiddlename("middle name")
                    .withAddress("some adr")
                    .withHomePhone("3420000")
                    .withMobilePhone("89131234567")
                    .withEmail("test@test.ru"), true);
        }
    }

    @Test
    public void testRemoveContactFromGroup() {
        Groups groups = app.db().groups();
        GroupData selectedGroup = groups.stream().filter((g) -> g.getContacts().size() > 0).findFirst().orElse(null);

        if (selectedGroup == null) {
            selectedGroup = groups.iterator().next();
            addContactToGroup(selectedGroup);
            selectedGroup = app.db().groupById(selectedGroup.getId());
        }

        ContactData selectedContact = selectedGroup.getContacts().iterator().next();
        app.contact().removeContactFromGroup(selectedContact, selectedGroup);

        ensureThatContactRemovedFromGroup(selectedGroup, selectedContact);
    }

    private void ensureThatContactRemovedFromGroup(GroupData selectedGroup, ContactData selectedContact) {
        selectedContact = app.db().contactById(selectedContact.getId());
        Assert.assertTrue(selectedContact.getGroups().stream().noneMatch((g) -> g.getId() == selectedGroup.getId()));
    }

    public void addContactToGroup(GroupData group) {
        ContactData contact = app.db().contacts().iterator().next();
        app.contact().addContactToGroup(contact, group);
        app.goTo().returnToHomePage();
    }
}
