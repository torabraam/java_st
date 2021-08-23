package ru.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.GroupData;
import ru.st.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {
    @BeforeMethod //local init
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().createG(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupDeletion() {
        //Groups before = app.group().all(); //use ui
        Groups before = app.db().groups(); //use db
        GroupData deletedGroup = before.iterator().next();
        app.goTo().groupPage();
        app.group().deleteG(deletedGroup);
        //Groups after = app.group().all(); //use ui
        Groups after = app.db().groups(); //use db
        assertEquals(after.size(), before.size() - 1);
        assertThat(after, equalTo(before.without(deletedGroup)));

    }


}