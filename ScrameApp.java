import java.util.*;
import java.io.*;

public class ScrameApp{
    public static void main(String[] args) {
        Scanner sc = new Scanner (System.in);
        Student student;
        Course course;
        String fileName = "test.bat";
        Database db = loadData(fileName);
        StudentManager studMg = new StudentManager();
        CourseManager courseMg = new CourseManager();
        int choice;
        int result;
        String type;
        String studentName = "";
        String school = "";
        int acadYear = 0;
        char gender = 'N';
        char confirm = 'N';
        String courseCode = "";
        String courseName = "";
        String courseCoordinator = "";
        int AU = 0;
        String matricNumber = "";
        String group;
        boolean cont = true;
        ArrayList<Assessment> results;
        int i = 0;
        double marks = 0;
        while (cont){
            System.out.println("Menu");
            System.out.println("1. Add a student ");
            System.out.println("2. Remove a student ");
            System.out.println("3. Add a course");
            System.out.println("4. Remove course");
            System.out.println("5. Register student for a course");
            System.out.println("6. Deregister student from a course");
            System.out.println("7. Check available slot in a class");
            System.out.println("8. Print student list");
            System.out.println("9. Enter course's assessment weightage");
            System.out.println("10. Enter coursework mark");
            System.out.println("11. Save data");
            System.out.println("12. Print course statistics");
            System.out.println("13. Print student transcript");
            System.out.println("14. Print all courses");
            System.out.println("15. Print all students");
            System.out.println("16. Exit");
            System.out.println("17. Modify Session");
            System.out.print("Enter your action: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1: //add a student
                    confirm = 'N';
                    while(confirm != 'Y'){
                        try{
                            System.out.println("Enter student's name: ");
                            studentName = sc.nextLine();
                            System.out.println("Enter student's matric No.: ");
                            matricNumber = sc.nextLine();
                            System.out.println("Enter student's school (SCSE): ");
                            school = sc.nextLine();
                            System.out.println("Enter student's year of study: ");
                            acadYear = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter student's gender: (M/F)");
                            gender = sc.nextLine().toUpperCase().charAt(0);
                        }
                        catch(InputMismatchException e){
                            System.out.println("Data entered is invalid, please try again!");
                            sc.nextLine();
                            continue;
                        }
                        if(db.getStudent(matricNumber) != null){
                            System.out.println("Student with matric number " + matricNumber + " already exists!");
                        }
                        else{
                            System.out.println("Student: "+ studentName +", Matric Number: " + matricNumber + ", "+ school + " Year " + acadYear + " , "+gender);
                            System.out.println("Are you sure you want to add in this student? (Y/N)");
                            confirm = sc.nextLine().charAt(0);
                        }
                    }
                    student = db.addStudent(studentName, matricNumber, gender, school, acadYear);
                    System.out.println(student + " is added into the records.");
                    db.printStudentCatalog();
                    break;
                case 2: //remove a student
                    db.printStudentCatalog();
                    System.out.println("Enter the matriculation number of the student: ");
                    matricNumber = sc.nextLine();
                    student = db.getStudent(matricNumber);
                    System.out.println(student);
                    System.out.println("Confirm to remove? (Y/N)");
                    confirm = sc.nextLine().charAt(0);
                    if(student != null && confirm == 'Y'){
                        db.removeStudent(student);
                        System.out.println(student + " is removed!");
                    }else{
                        System.out.println("Student not removed!");
                    }
                    break;
                case 3: //add a course
                    boolean contin = true;
                    boolean added = false;
                    char addMore = 'Y';
                    confirm = 'N';
                    while(confirm != 'Y'){
                        try{
                            System.out.println("Enter course name: (Computer Vision/Object-Orientated Design & Programming)");
                            courseName = sc.nextLine();
                            System.out.println("Enter course code: (CE2005/CE4001/CZ1023)");
                            courseCode = sc.nextLine();
                            System.out.println("Enter course AU: (2/3)");
                            AU = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Enter name of course coordinator: ");
                            courseCoordinator = sc.nextLine();
                        }
                        catch(InputMismatchException e){
                            System.out.println("Data entered is invalid, please try again!");
                            sc.nextLine();
                            continue;
                        }
                        System.out.println(courseCode + " " + courseName + " AU: " + AU + " by " + courseCoordinator);
                        System.out.println("Are you sure you want to add in this course? (Y/N)");
                        confirm = sc.nextLine().charAt(0);
                    }
                    if(db.getCourse(courseCode) != null){
                        System.out.println(courseCode + " is already registered and used! Please choose another course code!");
                        break;
                    }
                    course = db.addCourse(courseName, courseCode, AU, courseCoordinator);
                    System.out.println(course + " is added.");
                    db.printCourseCatalog();
                    do{
                        added = course.addSession();
                        if(!added) continue; //ensures at least one is added!
                        System.out.println("Do you want to add more session? Y/N");
                        addMore = sc.nextLine().charAt(0);
                        switch(addMore){
                            case 'N': contin = false;
                                        break;
                        }
                    }while(contin);
                    break;
                case 4: //Remove Course
                    db.printCourseCatalog();
                    System.out.println("Enter the course code you want to remove: ");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    if(course != null){
                        System.out.println("Are you sure you want to remove " + course + " ? (Y/N)");
                        confirm = sc.nextLine.charAt(0);
                        if(confirm == 'Y'){
                            db.removeCourse(course);
                            System.out.println(course  + " is removed!");
                        }
                    }else{
                        System.out.println("Error! Course doesn't exist!");
                    }
                    break;
                case 5: //register student for a course
                    db.printStudentCatalog();
                    System.out.println("Enter student's matriculation number: ");
                    matricNumber = sc.nextLine();
                    db.printCourseCatalog();
                    System.out.println("Enter course code to be registered: ");
                    courseCode = sc.nextLine();
                    student = db.getStudent(matricNumber);
                    course = db.getCourse(courseCode);
                    //if any of them do not exist
                    if (student == null || course == null){ 
                        System.out.println("Student or course is not in our records!");
                        break;
                    }
                    else{
                        course.printSessions();
                        System.out.println("Please enter the group ID: (SEP1/CE3)");
                        group = sc.nextLine();
                        System.out.println("Please enter the session type: (LEC/TUT/LAB)");
                        type = sc.nextLine();
                        result = courseMg.regStudentToCourse(student, course, group, type);
                        switch (result){
                            case 0: 
                                studMg.updateCourseTaken(course, student); 
                                System.out.println("Student added to " + type + " with Group ID: " + group);
                                break;
                            case -1: System.out.println("Error! Group is already full!"); break;
                            case -2: System.out.println("Error! Student is already registered in that group session!"); break;
                            case -3: System.out.println("Error! You have entered a wrong group session!"); break;
                        }
                    }
                    break;
                case 6: //deregister a student from a course
                    db.printStudentCatalog();
                    System.out.println("Enter the student matriculation number: ");
                    matricNumber = sc.nextLine();
                    System.out.println("Enter the course code you want to remove the student from");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    student = db.getStudent(matricNumber);
                    if(course != null && student != null){
                        if(course.deregisterStudent(student) > 0){
                            student.deregisterCourse(course);
                            System.out.println("Student deregistered successfully!");
                        }else{
                            System.out.println("Error! Student is not registered for this course!");
                        }
                    }else{
                        System.out.println("Course or student does not exist!");
                    }
                    break;
                case 7: //Check available slots in a class
                    db.printCourseCatalog();
                    System.out.println("Enter the course code you need check vacancy for: ");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    courseMg.checkVacancy(course);
                    break;
                case 8: //print student list
                    db.printCourseCatalog();
                    System.out.println("Please enter the course code that you would like to print out the student list");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    courseMg.printSessionStudent(course);
                    break;
                case 9: //enter course assessment weightage
                    System.out.println("================= ENTER COURSE WEIGHTAGE =================");
                    db.printCourseCatalog();
                    System.out.println("Enter course code:");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    if(course == null){
                        System.out.println("Error! Course isn't in our records!");
                        break;
                    }
                    courseMg.setAssessment(course);
                    break;
                case 10: //enter coursework mark
                    db.printCourseCatalog();
                    System.out.println("Enter course: ");
                    courseCode = sc.nextLine();
                    System.out.println("Enter student's matriculation number: ");
                    matricNumber = sc.nextLine();
                    course = db.getCourse(courseCode);
                    student = db.getStudent(matricNumber);
                    if(course == null || student == null){
                        System.out.println("Student or course entered is not in our records!");
                    }
                    else {
                        results = courseMg.getAssessment(course);
                        if(course.studentRegistered(student)){
                            for (i = 0; i < results.size(); i++){
                                marks = 101; //just for it to satisfy the while statement
                                while(marks > 100 || marks < 0){
                                    System.out.println("Enter results the following component: " + results.get(i).getAssessmentName());
                                    marks = sc.nextDouble();
                                    sc.nextLine();
                                    courseMg.setResults(results.get(i), student, marks);
                                }
                            }
                            if(i == 0) System.out.println("Error! Course Weightage is not set yet!");
                        } else {
                            System.out.println("Student is not registered in this course!");
                        }
                    }
                    break;
                case 11: //save data
                    saveData(fileName, db);
                    System.out.println("============== DATA SAVED ==============");
                    break;
                case 12: //print course stats
                    db.printCourseCatalog();
                    System.out.println("Enter course code:");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    if(course == null){
                        System.out.println("Error! Course is not in our records!");
                        break;
                    }
                    courseMg.printCourseStats(course);
                    break;
                case 13: //print student transcript
                    db.printStudentCatalog();
                    System.out.println("Enter student's matriculation number: ");
                    matricNumber = sc.nextLine();
                    student = db.getStudent(matricNumber);
                    if(student == null){
                        System.out.println("Error! Student is not in our records!");
                        break;
                    }
                    studMg.printTranscript(student);
                    break;
                case 14:
                    db.printCourseCatalog();
                    break;
                case 15:
                    db.printStudentCatalog();
                    break;
                case 16: //exit
                    cont = false;
                    saveData(fileName, db);
                    System.out.println("Exit....");
                    return;

                case 17: //modify Session
                    db.printCourseCatalog();
                    System.out.println("Enter the course code you want to modify sessions: ");
                    courseCode = sc.nextLine();
                    course = db.getCourse(courseCode);
                    // course exists
                    if(course != null){
                        //print out the course's session
                        course.printIndexList();
                        //tell user to select the session to modify and get tbe session
                        System.out.println("Enter the Session Type and Group ID to be removed (LAB/TUT/LEC CE1)");
                        type = sc.next();
                        group = sc.next();
                        //get the session from database
                        session = course.getSession(group, type);
                        if (session != null){
                            course.modifySession(session);
                        }else{
                            System.out.println("Session does not exist!");
                        }
                    }
                    //course doesn't exist
                    else{
                        System.out.println("Error! Course doesn't exist!");
                    }
                    break;
                default:
                    System.out.println("Invalid choice, please try again!");
            }
        System.out.println("Press any key to continue...");
        sc.nextLine();
        }

    }

    public static Database loadData(String filename) {
		Database db = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			db = (Database) in.readObject();
			in.close();
		} catch (IOException ex) {
            // ex.printStackTrace();
            System.out.println("=================================================================================================");
            System.out.println("================== ERROR! NO DATA LOADED (ignore if this is your first loadup) ==================");
            System.out.println("=================================================================================================");
		} catch (ClassNotFoundException ex) {
            System.out.println("=================================================================================================");
            System.out.println("============= ERROR! CLASS NOT FOUND! Make sure you have all the required classes! ==============");
            System.out.println("=================================================================================================");
        }
		// print out the size
		//System.out.println(" Details Size: " + pDetails.size());
        //System.out.println();
        if(db != null){
            return db;
        }
        return (new Database());
	}

	public static void saveData(String filename, Database db) {
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(db);
			out.close();
		//	System.out.println("Object Persisted");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
    }
}