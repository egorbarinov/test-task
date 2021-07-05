package ru.egorbarinov.testtask;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Subject implements Comparable<Subject> {
    private String name;
    private Subject owner;
    private SortedSet<Subject> subjects;

    public Subject(String name) {
        this.name = name;
        this.subjects = new TreeSet<>();
    }

    public Subject getOwner() {
        return owner;
    }

    public void setOwner(Subject owner) throws CyclicSubmissionException {
        this.owner = owner;
        checkCyclicSubmission();
    }

    private void checkCyclicSubmission() throws CyclicSubmissionException {
        Subject subject = this;
        while (true) {
            subject = subject.owner;
            if (subject == null)
                return;
            if (subject.equals(this))
                throw new CyclicSubmissionException("critical error: cyclic submission");
        }
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public StringBuilder report(int step) {
        var reportForKing = new StringBuilder("    ".repeat(step));
        reportForKing.append(name).append("\r\n");
        for (Subject subject: subjects)
            reportForKing.append(subject.report(step + 1));
        return reportForKing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return Objects.equals(name, subject.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Subject o) {
        return this.name.compareTo(o.name);
    }
}
