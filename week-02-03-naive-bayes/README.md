# Weeks 02-03: Naive Bayes Classifier

This exercise implements a simple Naive Bayes classifier in Java.

## Directories

- `solution/` contains the student-written assignment classes.
- `teacher-provided-tests/` contains the test runner and sample data supplied by the course.

The source files use Java's default package, as required by the assignment.

## Compile and run the provided tests

From the repository root:

```bash
mkdir -p build
javac -d build \
  week-02-03-naive-bayes/solution/*.java \
  week-02-03-naive-bayes/teacher-provided-tests/TestMain.java

cd week-02-03-naive-bayes/teacher-provided-tests
java -cp ../../build TestMain
```

Running the tests creates `classifications.txt`, which is ignored by Git.
