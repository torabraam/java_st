package ru.st.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.st.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {
    @Parameter(names = "-c", description = "Group count")
    public int count;
    @Parameter(names = "-f", description = "Target file")
    public String file;
    @Parameter(names = "-d", description = "File format")
    public String format;



    public static void main(String[] args) throws IOException {
        ContactDataGenerator generator = new ContactDataGenerator();
        JCommander jCommander = new JCommander(generator);
        try {
            jCommander.parse(args);
        } catch (ParameterException ex) {
            jCommander.usage();
            return;
        }
        generator.run();
    }

    private void run() throws IOException {
        List<ContactData> groups = generateContacts(count);
        if (format.equals("json")){
            saveAsJSON(groups, new File(file));
        } else {
            System.out.println("Unrecognized format " + format);
        }

    }

    private List<ContactData> generateContacts(int count) {
        List<ContactData> contacts = new ArrayList<ContactData>();
        for (int i = 0; i < count; i++) {
            contacts.add(new ContactData().withFirstname(String.format("Fname%s", i))
                    .withMiddlename(String.format("Mname%s", i)).withLastname(String.format("Lname%s", i))
                    .withHomePhone(String.format("123%s", i)).withMobilePhone(String.format("1-2-3%s", i)).withWorkPhone(String.format("12-3%s", i))
                    .withAddress(String.format("address %s", i))
                    .withEmail(String.format("email%s",i)).withEmail2(String.format("email@mail%s",i)).withEmail3(String.format("email%s@ui.ui",i))
                    .withGroup("test1"));
        }
        return contacts;
    }

    private void saveAsJSON(List<ContactData> contacts, File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(contacts);
        Writer writer = new FileWriter(file);
        writer.write(json);
        writer.close();
    }
}
