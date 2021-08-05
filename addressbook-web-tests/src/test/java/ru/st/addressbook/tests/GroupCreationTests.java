package ru.st.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.st.addressbook.model.GroupData;
import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {


    @Test
    public void testGroupCreation() {

        app.getNavigationHelper().gotoGroupPage();
        List<GroupData> before = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("test5", null, null);
        app.getGroupHelper().createGroup(group);
        List<GroupData> after = app.getGroupHelper().getGroupList();
        Assert.assertEquals(after.size(), before.size() + 1);

        //lyambda ф-я, найти мах айдишник, поток
        //group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());

        before.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
        //Assert.assertEquals(new HashSet<>(before), new HashSet<>(after)); //modify list to set

    }

}
