package ru.st.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestAssuredTests extends TestBase{

    @Test
    public void testCreateIssue() throws IOException {
        try {
            skipIfNotFixed(1368);
        } catch (SkipException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        Set<Issue> oldIssues = getIssues();
        Issue newIssue = new Issue().withSubject("myTestIssue").withDescription("myDesc test");
        int issueId = createIssue(newIssue);
        System.out.println(issueId);
        Set<Issue> newIssues = getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

    private Set<Issue> getIssues() throws IOException {
        String json = RestAssured.get("https://bugify.stqa.ru/api/issues.json").asString();
        //JsonElement parsed = new JsonParser().parse(json);
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {
        }.getType());
    }

    private int createIssue(Issue newIssue) throws IOException {
        String json = RestAssured.given()
                .parameter("subject", newIssue.getSubject())
                .parameter("description", newIssue.getDescription())
                .post("https://bugify.stqa.ru/api/issues.json").asString();
        JsonElement parsed = JsonParser.parseString(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();

    }


}
