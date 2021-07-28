package ru.st.addressbook.model;

public class ContactData {
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final String address;
    private final String homeph;
    private final String email;
    private static String group;

    public ContactData(String firstname, String middlename, String lastname, String address, String homeph, String email, String group) {
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.address = address;
        this.homeph = homeph;
        this.email = email;
        this.group = group;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getHomeph() {
        return homeph;
    }

    public String getEmail() {
        return email;
    }

    public static String getGroup() {
        return group;
    }
}
