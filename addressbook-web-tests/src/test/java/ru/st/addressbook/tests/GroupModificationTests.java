package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.st.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGroupPage();

        if (!app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }
        List<GroupData> before = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(before.size() - 1);
        app.getGroupHelper().initGroupModification();
        // save old id
        GroupData group = new GroupData(before.get(before.size() - 1).getId(), "test1", "test2", "test3"); //add local variable
        app.getGroupHelper().fillGroupForm(group);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size());


        before.remove(before.size() - 1); //delete modifying element
        before.add(group); //add new modified element
        Assert.assertEquals(new HashSet<>(before), new HashSet<>(after)); //modify list to set


    }

}
