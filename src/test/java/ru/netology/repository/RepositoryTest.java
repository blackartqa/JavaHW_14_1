package ru.netology.repository;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    private IssueRepository repository = new IssueRepository();

    private Issue issue1 = new Issue(1001, true, "Author1", 7, new HashSet<String>(Arrays.asList("label1", "label2", "label3")), new HashSet<String>(Arrays.asList("Assignee1", "Assignee2", "Assignee3")));
    private Issue issue2 = new Issue(1002, false, "Author2", 12, new HashSet<String>(Arrays.asList("label13", "label4", "label7")), new HashSet<String>(Arrays.asList("Assignee1", "Assignee3", "Assignee4")));
    private Issue issue3 = new Issue(1003, true, "Author1", 2, new HashSet<String>(Arrays.asList("label1", "label3", "label5")), new HashSet<String>(Arrays.asList("Assignee3", "Assignee5", "Assignee6")));
    private Issue issue4 = new Issue(1004, false, "Author2", 21, new HashSet<String>(Arrays.asList("label4", "label3", "label9")), new HashSet<String>(Arrays.asList("Assignee1", "Assignee2", "Assignee4")));

    @Nested
    public class EmptyRepository {
        @Test
        void shouldReturnEmptyWhenFindByOpen() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyWhenFindByClosed() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfClose() {
            repository.closeById(1001);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfOpen() {
            repository.openById(1003);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class SingleItem {

        @Test
        void shouldFindOpenIfOpen() {
            repository.save(issue1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoOpen() {
            repository.save(issue2);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindClosedIfClosed() {
            repository.save(issue2);
            List<Issue> expected = new ArrayList<>(List.of(issue2));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoClosed() {
            repository.save(issue3);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldClose() {
            repository.save(issue1);
            repository.closeById(1001);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfWrongIdWhenClose() {
            repository.save(issue1);
            repository.closeById(1003);
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfNothingToClose() {
            repository.save(issue2);
            repository.closeById(1002);
            List<Issue> expected = new ArrayList<>(List.of(issue2));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldOpen() {
            repository.save(issue2);
            repository.openById(1002);
            List<Issue> expected = new ArrayList<>(List.of(issue2));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfWrongIdWhenOpen() {
            repository.save(issue1);
            repository.openById(1005);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfNothingToOpen() {
            repository.save(issue1);
            repository.openById(1001);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class MultipleItems {

        @Test
        void shouldFindOpenIfOpen() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue3));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoOpen() {
            repository.addAll(List.of(issue2, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindClosedIfClosed() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoClosed() {
            repository.addAll(List.of(issue1, issue3));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldClose() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.closeById(1001);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfWrongIdWhenClose() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.closeById(1005);
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfNothingToClose() {
            repository.addAll(List.of(issue2, issue4));
            repository.closeById(1004);
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = repository.findClosed();
            assertEquals(expected, actual);
        }

        @Test
        void shouldOpen() {
            repository.addAll(List.of(issue2, issue4));
            repository.openById(1004);
            List<Issue> expected = new ArrayList<>(List.of(issue4));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfWrongIdWhenOpen() {
            repository.addAll(List.of(issue1, issue2, issue3, issue4));
            repository.openById(1005);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue3));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }

        @Test
        void shouldDoNothingIfNothingToOpen() {
            repository.addAll(List.of(issue1, issue3));
            repository.openById(1001);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue3));
            List<Issue> actual = repository.findOpen();
            assertEquals(expected, actual);
        }
    }
}