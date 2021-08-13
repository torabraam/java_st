package ru.st.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.st.addressbook.model.ContactData;
import ru.st.addressbook.model.Contacts;

import java.util.List;


public class ContactHelper extends HelperBase {

    ApplicationManager manager;

    public ContactHelper(ApplicationManager manager) {
        super(manager.wd);
        this.manager = manager; // link to appMan
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstname());
        type(By.name("middlename"), contactData.getMiddlename());
        type(By.name("lastname"), contactData.getLastname());
        type(By.name("address"), contactData.getAddress());
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("mobile"), contactData.getMobilePhone());
        type(By.name("work"), contactData.getWorkPhone());
        type(By.name("email"), contactData.getEmail());
        type(By.name("email2"), contactData.getEmail2());
        type(By.name("email2"), contactData.getEmail3());
        attach(By.name("photo"), contactData.getPhoto());


        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactCreation() {
        click(By.name("submit"));
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector("a[href=\"edit.php?id=" + id + "\"]")).click();
    }

    public void submitContactModification() {
        click(By.name("update"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void returnToHomePage() {
        click(By.cssSelector("a[href=\"./\"]"));
    }

    public void deleteSelectedContacts() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    public void createC(ContactData contact, boolean creation) {
        manager.goTo().gotoAddNewContact();
        fillContactForm(contact, creation);
        submitContactCreation();
        contactCache = null;
        returnToHomePage();
        //manager.goTo().returnToHomePage();
    }

    public void modifyC(ContactData user) {
        initContactModificationById(user.getId());
        fillContactForm(user, false);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void deleteC(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContacts();
        contactCache = null;

    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts allc() {
        if (contactCache != null) {
            return new Contacts(contactCache); // copy of contactCache
        }
        Contacts contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));

            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();

            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));

            contactCache.add(new ContactData().withId(id).withFirstname(firstname).withLastname(lastname).
                    withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
        }
        return new Contacts(contactCache);

    }


    public ContactData infoFromEditForm(ContactData contact) {

        initContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String middleName = wd.findElement(By.name("middlename")).getAttribute("value");
        String address = wd.findElement(By.xpath("/html/body/div/div[4]/form/textarea[1]")).getAttribute("value");
        String email = wd.findElement(By.xpath("/html/body/div/div[4]/form[1]/input[15]")).getAttribute("value");
        String email2 = wd.findElement(By.xpath("/html/body/div/div[4]/form[1]/input[16]")).getAttribute("value");
        String email3 = wd.findElement(By.xpath("/html/body/div/div[4]/form[1]/input[17]")).getAttribute("value");
        String homePhone = wd.findElement(By.name("home")).getAttribute("value");
        String mobilePhone = wd.findElement(By.name("mobile")).getAttribute("value");
        String workPhone = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstname(firstName).withLastname(lastName).
                withMiddlename(middleName).withAddress(address).withEmail(email).withEmail2(email2).
                withEmail3(email3).withHomePhone(homePhone).withMobilePhone(mobilePhone).withWorkPhone(workPhone);
    }
}