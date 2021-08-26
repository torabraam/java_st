package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;
import ru.st.addressbook.model.GroupData;
import ru.st.addressbook.model.Groups;

public class AddContactToGroupTests extends TestBase {
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
    public void testAddContactToGroup() {
        app.goTo().returnToHomePage();
        Contacts contacts = app.db().contacts();
        Groups groups = app.db().groups();
        ContactData selectedContact = contacts.iterator().next();
        Groups contactGroups = selectedContact.getGroups();

        if (groups.size() == contactGroups.size()) {
            createNewGroup();
            groups = app.db().groups();
        }

        GroupData selectedGroup = getGroupWithoutContact(groups, selectedContact);
        app.contact().addContactToGroup(selectedContact, selectedGroup);

        ensureThatContactAddedToGroup(selectedContact, selectedGroup);
    }

    private void ensureThatContactAddedToGroup(ContactData selectedContact, GroupData selectedGroup) {
        selectedContact = app.db().contactById(selectedContact.getId());
        Assert.assertTrue(selectedContact.getGroups().stream().anyMatch((g) -> g.getId() == selectedGroup.getId()));
    }

    private GroupData getGroupWithoutContact(Groups groups, ContactData contact) {
        for (GroupData group : groups) {
            if (group.getContacts().stream().noneMatch((c) -> c.getId() == contact.getId())) {
                return group;
            }
        }

        Assert.fail();
        return new GroupData();
    }

    private void createNewGroup() {
        app.goTo().groupPage();
        app.group().createG(new GroupData()
                .withName("test2")
                .withHeader("test3")
                .withFooter("test4"));
        app.goTo().returnToHomePage();
    }
}
