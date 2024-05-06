
/**
 * The UniversityCourseManagementSystem program implements an application that solves assignment3
 *
 * @author  Ksenia Korchagina
 * @version 1.1
 * @since   2023-11-20
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * UniversityCourseManagementSystem is the main class of the task that
 * contains an entry point for the program.
 */
public class UniversityCourseManagementSystem {
    private static List<Course> courses = new ArrayList<>();
    private static List<Professor> professors = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();

    /**
     * Checks whether input string contains only english letter
     * @param str String which must be checked
     * @return true in case String contains only english letters, false in other
     *         cases
     */
    private static boolean isEnglishAlphabet(String str) {
        return str.matches("[a-zA-Z]+");
    }

    /**
     * Checks the name of a Course on correctness.
     * Correct course name should contain english characters only,
     * should not start or finish with underscore (_), and should
     * not contain more than one underscore in a row.
     * @param name a name of a Course to check
     * @return true if the name is valid, false otherwise
     * @see Course
     */
    public static boolean isValidCourseName(String name) {
        if (name.isEmpty()) {
            return false;
        }

        if (name.charAt(0) == '_' || name.charAt(name.length() - 1) == '_') {
            return false;
        }

        String[] parts = name.split("_");
        for (String part : parts) {
            if (!isEnglishAlphabet(part) || part.isEmpty()) {
                return false;
            }
        }
        return parts.length >= 1;
    }

    /**
     * main is the main function, which process input data,
     * check all conditions & make an output
     * @param args command line arguments
     */
    public static void main(String[] args) {
        List<String> commands = Arrays.asList("course", "student", "professor", "enroll", "drop", "teach", "exempt");
        fillInitialData();
        Scanner s = new Scanner(System.in);
        try {
            while (s.hasNextLine()) {
                String givenRow = s.nextLine();
                if (givenRow.isEmpty()) {
                    break;
                }

                if (givenRow.equals("course")) {
                    String courseName = s.nextLine().toLowerCase();
                    if (commands.contains(courseName)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    if (!isValidCourseName(courseName)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    String courseLevel = s.nextLine().toLowerCase();

                    if (commands.contains(courseLevel)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }

                    CourseLevel courseLevelEnum = null;
                    switch (courseLevel) {
                        case "bachelor":
                            courseLevelEnum = CourseLevel.BACHELOR;
                            break;
                        case "master":
                            courseLevelEnum = CourseLevel.MASTER;
                            break;
                        default:
                            System.out.println("Wrong inputs");
                            continue;
                    }

                    // check that course does not exist
                    boolean flag = false;
                    for (Course course : courses) {
                        if (course.getCourseName().equals(courseName)) {
                            System.out.println("Course exists");
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        continue;
                    }

                    Course course = new Course(courseName, courseLevelEnum);
                    courses.add(course);
                    System.out.println("Added successfully");
                } else if (givenRow.equals("student")) {
                    String studentName = s.nextLine().toLowerCase();

                    if (commands.contains(studentName)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }

                    if (!isEnglishAlphabet(studentName)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Student student = new Student(studentName);
                    students.add(student);
                    System.out.println("Added successfully");
                } else if (givenRow.equals("professor")) {
                    String professorName = s.nextLine().toLowerCase();
                    if (!isEnglishAlphabet(professorName)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }

                    if (commands.contains(professorName)) {
                        System.out.println("Wrong inputs");
                        continue;
                    }

                    Professor professor = new Professor(professorName);
                    professors.add(professor);
                    System.out.println("Added successfully");
                } else if (givenRow.equals("enroll")) {
                    int mID = 0;
                    int cID = 0;
                    String memberID = s.nextLine();
                    try {
                        mID = Integer.parseInt(memberID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }

                    String courseID = s.nextLine();

                    try {
                        cID = Integer.parseInt(courseID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Student student = null;
                    boolean flag = false;
                    for (Student currentStudent : students) {
                        if (currentStudent.getMemberId() == mID) {
                            flag = true;
                            student = currentStudent;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Course course = null;
                    flag = false;
                    for (Course currentCourse : courses) {
                        if (currentCourse.getCourseId() == cID) {
                            flag = true;
                            course = currentCourse;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    List<Course> enrolledCourses = student.getEnrolledCourses();
                    flag = false;
                    for (Course enrolledCourse : enrolledCourses) {
                        if (enrolledCourse.getCourseId() == course.getCourseId()) {
                            System.out.println("Student is already enrolled in this course");
                            flag = true;
                            break;
                        }
                    }

                    if (flag) {
                        continue;
                    }

                    if (enrolledCourses.size() == student.getMaxEnrollment()) {
                        System.out.println("Maximum enrollment is reached for the student");
                        continue;
                    }
                    if (course.isFull()) {
                        System.out.println("Course is full");
                        continue;
                    }
                    student.enroll(course);
                    System.out.println("Enrolled successfully");
                    course.enroll(student);
                } else if (givenRow.equals("drop")) {
                    int mID = 0;
                    int cID = 0;
                    String memberID = s.nextLine();
                    try {
                        mID = Integer.parseInt(memberID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    String courseID = s.nextLine();

                    try {
                        cID = Integer.parseInt(courseID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Student student = null;
                    boolean flag = false;
                    for (Student currentStudent : students) {
                        if (currentStudent.getMemberId() == mID) {
                            flag = true;
                            student = currentStudent;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Course course = null;
                    flag = false;
                    for (Course currentCourse : courses) {
                        if (currentCourse.getCourseId() == cID) {
                            flag = true;
                            course = currentCourse;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    flag = false;
                    List<Course> enrolledCourses = student.getEnrolledCourses();
                    for (Course enrolledCourse : enrolledCourses) {
                        if (enrolledCourse.getCourseId() == course.getCourseId()) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Student is not enrolled in this course");
                        continue;
                    }
                    student.drop(course);
                    course.drop(student);
                    System.out.println("Dropped successfully");

                } else if (givenRow.equals("teach")) {
                    int mID = 0;
                    int cID = 0;
                    String memberID = s.nextLine();
                    try {
                        mID = Integer.parseInt(memberID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    String courseID = s.nextLine();

                    try {
                        cID = Integer.parseInt(courseID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Professor professor = null;
                    boolean flag = false;
                    for (Professor currentProfessor : professors) {
                        if (currentProfessor.getMemberId() == mID) {
                            flag = true;
                            professor = currentProfessor;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Course course = null;
                    flag = false;
                    for (Course currentCourse : courses) {
                        if (currentCourse.getCourseId() == cID) {
                            flag = true;
                            course = currentCourse;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    List<Course> assignedCourses = professor.getAssignedCourses();
                    if (assignedCourses.size() == professor.getMaxLoad()) {
                        System.out.println("Professor's load is complete");
                        continue;
                    }
                    flag = false;
                    for (Course asiidgnedCourse : assignedCourses) {
                        if (asiidgnedCourse.getCourseId() == course.getCourseId()) {
                            System.out.println("Professor is already teaching this course");
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        continue;
                    }
                    professor.teach(course);
                    System.out.println("Professor is successfully assigned to teach this course");
                } else if (givenRow.equals("exempt")) {
                    int mID = 0;
                    int cID = 0;
                    String memberID = s.nextLine();
                    try {
                        mID = Integer.parseInt(memberID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    String courseID = s.nextLine();

                    try {
                        cID = Integer.parseInt(courseID);
                    } catch (NumberFormatException ex) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Professor professor = null;
                    boolean flag = false;
                    for (Professor currentProfessor : professors) {
                        if (currentProfessor.getMemberId() == mID) {
                            flag = true;
                            professor = currentProfessor;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    Course course = null;
                    flag = false;
                    for (Course currentCourse : courses) {
                        if (currentCourse.getCourseId() == cID) {
                            flag = true;
                            course = currentCourse;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Wrong inputs");
                        continue;
                    }
                    List<Course> assignedCourses = professor.getAssignedCourses();
                    flag = false;
                    for (Course asiidgnedCourse : assignedCourses) {
                        if (asiidgnedCourse.getCourseId() == course.getCourseId()) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag) {
                        System.out.println("Professor is not teaching this course");
                        continue;
                    }
                    professor.exempt(course);
                    System.out.println("Professor is exempted");
                } else {
                    System.out.println("Wrong inputs");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * fillInitialData is a function, which is used to put
     * initial data about students, professors and courses into
     * lists.
     */
    public static void fillInitialData() {
        final int id0 = 0;
        final int id1 = 1;
        final int id2 = 2;
        final int id3 = 3;
        final int id4 = 4;
        final int id5 = 5;

        courses.add(new Course("java_beginner", CourseLevel.BACHELOR));
        courses.add(new Course("java_intermediate", CourseLevel.BACHELOR));
        courses.add(new Course("python_basics", CourseLevel.BACHELOR));
        courses.add(new Course("algorithms", CourseLevel.MASTER));
        courses.add(new Course("advanced_programming", CourseLevel.MASTER));
        courses.add(new Course("mathematical_analysis", CourseLevel.MASTER));
        courses.add(new Course("computer_vision", CourseLevel.MASTER));

        Student studentAlice = new Student("Alice");
        studentAlice.enroll(courses.get(id0));
        courses.get(id0).enroll(studentAlice);
        studentAlice.enroll(courses.get(id1));
        courses.get(id1).enroll(studentAlice);
        studentAlice.enroll(courses.get(id2));
        courses.get(id2).enroll(studentAlice);
        students.add(studentAlice);

        Student studentBob = new Student("Bob");
        studentBob.enroll(courses.get(id0));
        courses.get(id0).enroll(studentBob);
        studentBob.enroll(courses.get(id3));
        courses.get(id3).enroll(studentBob);
        students.add(studentBob);

        Student studentAlex = new Student("Alex");
        studentAlex.enroll(courses.get(id4));
        courses.get(id4).enroll(studentAlex);
        students.add(studentAlex);

        Professor professorAli = new Professor("Ali");
        professorAli.teach(courses.get(id0));
        professorAli.teach(courses.get(id1));
        professors.add(professorAli);

        Professor professorAhmed = new Professor("Ahmed");
        professorAhmed.teach(courses.get(id2));
        professorAhmed.teach(courses.get(id4));
        professors.add(professorAhmed);

        Professor professorAndrey = new Professor("Andrey");
        professorAndrey.teach(courses.get(id5));
        professors.add(professorAndrey);
    }
}

/**
 * UniversityMember is an abstract class, which is used to represent data
 * about professor o student
 */
abstract class UniversityMember {
    private static int numberOfMembers = 0;
    private int memberId = 0;
    private String memberName;

    /**
     * getMemberId is used to get an ID
     * @return ID
     */
    public int getMemberId() {
        return memberId;
    }

    /**
     * public function universityMember initialize ID and Name of a new Student
     * @param memberId   we get an ID and increase it by one, because our ID stats
     *                   from 1, not from 0
     * @param memberName name of a new Student
     */
    public UniversityMember(int memberId, String memberName) {
        this.memberId = memberId + 1;
        this.memberName = memberName;
    }

    /**
     * getNumberOfMembers counts amount of students and professors together
     * @return this amount
     */
    public static int getNumberOfMembers() {
        return numberOfMembers;
    }

    /**
     * setNumberOfMembers add an information about amount of members
     * to necessary university member
     * @param numberOfMembers given amount of members
     */
    public static void setNumberOfMembers(int numberOfMembers) {
        UniversityMember.numberOfMembers = numberOfMembers;
    }

}

/**
 * CourseLevel is the enum contains all levels of students,
 * in which they can study
 */
enum CourseLevel {
    BACHELOR,
    MASTER
}

/**
 * class Student extends UniversityMember class, so it contains basic
 * information about
 * student as a member and extra data
 */
class Student extends UniversityMember implements Enrollable {
    private static final int MAX_ENROLLMENT = 3;

    /**
     * this way we get information about enrolled courses
     * @return courses, in which our student is enrolled
     */
    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    /**
     * this way we get get information about maximum possible enrollments
     * @return this maximum
     */
    public int getMaxEnrollment() {
        return MAX_ENROLLMENT;
    }

    private List<Course> enrolledCourses = new ArrayList<>();

    /**
     * it is a constructor of a student class, where we use super, to
     * take functions getNumberOfMembers and memberName from extended class
     * UniversityMember
     * @param memberName is needed to design a Student
     */
    public Student(String memberName) {
        super(getNumberOfMembers(), memberName);
        setNumberOfMembers(getNumberOfMembers() + 1);
    }

    @Override
    /**
     * drop is used to discharge a student from a course.
     * We override this function for all students
     * @param course from which our student must be dropped
     * @return true in case we can drop student, false in case student does not
     *         attend this course,
     *         so cannot be dropped
     */
    public boolean drop(Course course) {
        for (int i = 0; i < enrolledCourses.size(); i++) {
            if (course.getCourseId() == enrolledCourses.get(i).getCourseId()) {
                enrolledCourses.remove(i);
                return true;
            }
        }
        return false;
    }

    // assume that max capacity and course enrolement is checked one layer above
    @Override
    /**
     * enroll is used to add a student to a course.
     * We override this function for all students
     * @param course in which course our student must be enrolled
     * @return true in case we can enroll student, false in case student cannot be
     *         enrolled because
     *         of not satisfying conditions,
     */
    public boolean enroll(Course course) {
        enrolledCourses.add(course);
        return true;
    }

}

/**
 * class Professor extend UniversityMember, so it contains its functions and
 * extra ones
 */
class Professor extends UniversityMember {
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    private static final int MAX_LOAD = 2;

    /**
     * this way we get get information about maximum possible loads
     * @return this maximum
     */
    public int getMaxLoad() {
        return MAX_LOAD;
    }

    private List<Course> assignedCourses = new ArrayList<>();

    /**
     * it is a constructor of a professor class, where we use super, to
     * take functions getNumberOfMembers and memberName from extended class
     * UniversityMember
     * @param memberName is needed to design a Professor
     */
    public Professor(String memberName) {
        super(getNumberOfMembers(), memberName);
        setNumberOfMembers(getNumberOfMembers() + 1);
    }

    /**
     * teach is used to add a professor to a course.
     * @param course is a course which our professor start to teach
     * @return true in case he can start it, false in case professor cannot start
     *         teaching because
     *         of not satisfying conditions,
     */
    public boolean teach(Course course) {
        assignedCourses.add(course);
        return true;
    }

    /**
     * exempt is used to dismiss a professor from a course.
     * @param course is a course from which this professor should be dropped
     * @return true in case we can dismiss him, false in case he cannot be dismissed
     *         because
     *         does not teach this course or by other reasons
     */
    public boolean exempt(Course course) {
        for (int i = 0; i < assignedCourses.size(); i++) {
            if (course.getCourseId() == assignedCourses.get(i).getCourseId()) {
                assignedCourses.remove(i);
                return true;
            }
        }
        return false;
    }

}

/**
 * Enrollable interface is used in class Student and contains drop and enroll
 * functions,
 * which we override in Student class
 */
interface Enrollable {
    boolean drop(Course course);

    boolean enroll(Course course);
}

/**
 * class Course represent information about all given courses
 */
class Course {
    private static final int CAPACITY = 3;
    private static int numberOfCourses = 0;

    /**
     * getCourseId is needed to get privet information about Id
     * @return Id of a given course
     */
    public int getCourseId() {
        return courseId;
    }

    private int courseId;
    private String courseName;
    private List<Student> enrolledStudents = new ArrayList<>();

    /**
     * getCourseName is needed to get privet information about name of a course
     * @return name of a given course
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * getCourseLevel is needed to get privet information about level of a course
     * @return level of a given course
     */
    public CourseLevel getCourseLevel() {
        return courseLevel;
    }

    private CourseLevel courseLevel;

    /**
     * isFull is needed to check whether our course is full or not
     * @return comparison of amount of enrolled students with maximum capacity
     */
    public boolean isFull() {
        return enrolledStudents.size() == CAPACITY;
    }

    /**
     * enroll is used to enroll student on a course
     * @param student which must be added
     * @return true if we can enroll this Student, false if it is impossible
     */
    public boolean enroll(Student student) {
        enrolledStudents.add(student);
        return true;
    }

    /**
     * drop is used to delete a student from a course
     * @param student which must be deleted
     * @return true if we can delete this Student, false if it is impossible
     */
    public boolean drop(Student student) {
        for (int i = 0; i < enrolledStudents.size(); i++) {
            if (student.getMemberId() == enrolledStudents.get(i).getMemberId()) {
                enrolledStudents.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * it is a constructor of a course class. Here we also increase number of course
     * by 1
     * @param courseName  name of course which must be given to a new course
     * @param courselevel level of course which must be given to a new course
     */
    public Course(String courseName, CourseLevel courselevel) {
        this.courseName = courseName;
        this.courseLevel = courselevel;
        numberOfCourses++;
        this.courseId = numberOfCourses; // assume that it's possible to create this course
    }
}