package ru.netology.manager;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ManagerTest {

    private IssueRepository repository = new IssueRepository();
    private IssueManager manager = new IssueManager(repository);

    private Issue issue1 = new Issue(1001, true, "Author1", 7, new HashSet<String>(Arrays.asList("label1", "label2", "label3")), new HashSet<String>(Arrays.asList("Assignee1", "Assignee2", "Assignee3")));
    private Issue issue2 = new Issue(1002, false, "Author2", 12, new HashSet<String>(Arrays.asList("label13", "label4", "label7")), new HashSet<String>(Arrays.asList("Assignee1", "Assignee3", "Assignee4")));
    private Issue issue3 = new Issue(1003, true, "Author1", 2, new HashSet<String>(Arrays.asList("label1", "label3", "label5")), new HashSet<String>(Arrays.asList("Assignee3", "Assignee5", "Assignee6")));
    private Issue issue4 = new Issue(1004, false, "Author2", 21, new HashSet<String>(Arrays.asList("label4", "label3", "label9")), new HashSet<String>(Arrays.asList("Assignee1", "Assignee2", "Assignee4")));

    @Nested
    public class Empty {

        @Test
        void shouldReturnEmptyIfNothingToSortByNewest() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNothingToSortByOldest() {
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyWhenFindByAuthor() {
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAuthor("Lada");
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyWhenFindByLabel() {
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label1")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyWhenFindByAssignee() {
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("Assignee1")));
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class SingleItem {

        @Test
        void shouldReturnOneToSortByNewest() {
            manager.issueAdd(issue1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnOneToSortByOldest() {
            manager.issueAdd(issue1);
            List<Issue> expected = new ArrayList<>(List.of(issue1));
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindByAuthor() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>(List.of(issue1));
            Collection<Issue> actual = manager.findByAuthor("Author1");
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoAuthor() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAuthor("Author3");
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindByLabel() {
            manager.issueAdd(issue2);
            Collection<Issue> expected = new ArrayList<>(List.of(issue2));
            Collection<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label7")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoLabel() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label19")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindByAssignee() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>(List.of(issue1));
            Collection<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("Assignee1")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyWhenFindByAssignee() {
            manager.issueAdd(issue1);
            Collection<Issue> expected = new ArrayList<>();
            Collection<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("Assignee5")));
            assertEquals(expected, actual);
        }
    }

    @Nested
    public class MultipleItems {

        @Test
        void shouldAddByOne() {
            manager.issueAdd(issue1);
            manager.issueAdd(issue2);
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue2));
            List<Issue> actual = manager.getAll();
            assertEquals(expected, actual);
        }

        @Test
        void shouldSortByNewest() {
            manager.addAll(List.of(issue1, issue2, issue3));
            List<Issue> expected = new ArrayList<>(List.of(issue3, issue1, issue2));
            List<Issue> actual = manager.sortByNewest();
            assertEquals(expected, actual);
        }

        @Test
        void shouldSortByOldest() {
            manager.addAll(List.of(issue1, issue2, issue3));
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue1, issue3));
            List<Issue> actual = manager.sortByOldest();
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindByAuthor() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = manager.findByAuthor("Author2");
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoAuthor() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByAuthor("Author3");
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindByLabel() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue2, issue4));
            List<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label4")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyIfNoLabel() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByLabel(new HashSet<String>(Arrays.asList("label17")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldFindByAssignee() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>(List.of(issue1, issue2, issue3));
            List<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("Assignee3")));
            assertEquals(expected, actual);
        }

        @Test
        void shouldReturnEmptyWhenFindByAssignee() {
            manager.addAll(List.of(issue1, issue2, issue3, issue4));
            List<Issue> expected = new ArrayList<>();
            List<Issue> actual = manager.findByAssignee(new HashSet<String>(Arrays.asList("Assignee14")));
            assertEquals(expected, actual);
        }
    }
}