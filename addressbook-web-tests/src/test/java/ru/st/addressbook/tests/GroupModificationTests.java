package ru.st.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.st.addressbook.model.GroupData;
import ru.st.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod //local init
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().createG(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupModification() {

        //Groups before = app.group().all(); //use ui
        Groups before = app.db().groups(); //use db
        GroupData modifiedGroup = before.iterator().next();
        GroupData group = new GroupData()
                .withId(modifiedGroup.getId()).withName("test1").withHeader("test2").withFooter("test3"); //add local variable
        app.goTo().groupPage();
        app.group().modifyG(group);
        //Groups after = app.group().all(); //use ui
        Groups after = app.db().groups(); //use db
        assertThat(app.group().count(), equalTo(before.size()));
        assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
    }
}
