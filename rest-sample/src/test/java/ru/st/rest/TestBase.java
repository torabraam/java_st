package ru.st.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jayway.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import java.util.Set;

public class TestBase {

    @BeforeSuite(alwaysRun = true)
    public void init() {
        RestAssured.authentication = RestAssured.basic("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    public boolean isIssueOpen(int issueId) {
        return !getIssueById(issueId).getState().equals("Resolved");
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

    private Issue getIssueById(int id) {
        String json = RestAssured.get("https://bugify.stqa.ru/api/issues/" + id + ".json").asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        Set<Issue> issuesSet = new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());
        return issuesSet.iterator().next();
    }
}