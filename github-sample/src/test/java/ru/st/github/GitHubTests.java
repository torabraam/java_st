package ru.st.github;

import com.google.common.collect.ImmutableMap;
import com.jcabi.github.*;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubTests {
    @Test
    public void testCommits() throws IOException {
        Github github = new RtGithub("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");
        RepoCommits commits = github.repos().get(new Coordinates.Simple("torabraam", "java_st")).commits();
        for (RepoCommit commit : commits.iterate(new ImmutableMap.Builder<String, String>().build())) {
            System.out.println(new RepoCommit.Smart(commit).message());
        }
    }
}