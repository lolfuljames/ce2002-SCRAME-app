import java.util.*;
import java.io.*;

public class Course implements Serializable{
    //Record of Session (Tut, Lab and Lecture) under a course.
    private ArrayList<Session> indexList = new ArrayList<Session>();
    //Record of Assessment (Exam and Coursework) under a course.
    //index 0 is equivalent to exam marks, the rest are coursemarks.
    private ArrayList<Assessment> results = new ArrayList<Assessment>();  //arrayList of hashmaps.

    //Other relevant information of a course.
    private String courseName;
    private String courseCode;
    private int AU;
    private String courseCoordinator;

    /**
     * Constructor for Course object, instantiating the following attributes
     * @param courseName Name of the course object being constructed, for instance "Object Oriented Design and Programming".
     * @param courseCode Course code of the course object being constructed, for instance CE2002
     * @param AU Academic Units allocated to the course object being constructed.
     * @param courseCoordinator Name of the overall coordinator of the course object being created. 
     */
    public Course(String courseName, String courseCode, int AU, String courseCoordinator){
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.AU = AU;
        this.courseCoordinator = courseCoordinator;
    }

    /**
     * A method to get the course name of the calling Course object. For example, Object Oriented Design and Programming.
     * @return The name of the calling Course object.
     */
    public String getCourseName(){
        return this.courseName;
    }
    /**
     * A method to get the course code of the calling Course objcect, for example CE2002.
     * @return The course code of the calling Course object.
     */
    public String getCourseCode(){
        return this.courseCode;
    }
    /**
     * A method to get the AU assigned to the calling Course object
     * @return The AU of the calling Course object.
     */
    public int getAU(){
        return this.AU;
    }

    /**
     * A method to return the name of the overall coordinator of the calling Course object.
     * @return The name of the coordinator of the calling Course object
     */
    public String getCourseCoordinator(){
        return this.courseCoordinator;
    }
    /**
     * A method to add in new Session under the calling Course object. 
     * @return <i>false</i> if the new Session object is not added,
     * return <i>true</i> if the new Session object is added
     * @see Session for Session object usage.
     * @see #indexList
     */
    public boolean addSession(){
        boolean success = false;
        ScrameApp.printSpaces();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter session type: (LEC/TUT/LAB)");
        String type = sc.nextLine();
        System.out.println();
        System.out.println("Enter session group : (SEP1/CE3/SEA2)");
        String group = sc.nextLine();
        System.out.println();
        System.out.println("Enter session timing: (Mon 15:00 - 17:00/ Fri 09:00 - 11:00)");
        String dayTime = sc.nextLine();
        System.out.println();
        System.out.println("Enter session location: (LT19a/TRx44/SWLAB3)");
        String location = sc.nextLine();
        System.out.println();
        System.out.println("Enter session tutor name: ");
        String tutorName = sc.nextLine();
        System.out.println();
        System.out.println("Enter session's max capacity: ");
        int maxCapacity = sc.nextInt();
        System.out.println();
        sc.nextLine(); //capture \n
        if(getSession(group, type) != null){
            System.out.println("This session is already in!");
        }else if(maxCapacity < 1){
            System.out.println("Please enter a valid capacity!");
        }else{
            success = indexList.add(new Session(type, group, dayTime, location, tutorName, maxCapacity, 0));
        }
        return success;
    }

    //Modifying Session
    /**
     * A method to modify information regarding a {@link Session} in a particular {@link Course}.
     * <ul>
     *  <li>Session Type</li>
     *  <li>Session Group ID</li>
     *  <li>Session Timing</li>
     *  <li>Session Location</li>
     *  <li>Tutor's Name</li>
     *  <li>Max capacity of session</li>
     * </ul>
     * @param session The target session to be modified.
     * @return <i>true</i> if modified successfully, <i>false</i> otherwise.
     */
    public boolean modifySession(Session session){
        boolean success = false;
        char conti = 'Y';
        int choice;
        Scanner sc = new Scanner(System.in);
        do{
            System.out.println("Select the field of Session to edit: ");
            System.out.println("1.Type");
            System.out.println("2.Group ID");
            System.out.println("3.Timing");
            System.out.println("4.Location");
            System.out.println("5.Tutor Name");
            System.out.println("6.Max Capacity");
            choice = sc.nextInt();
            sc.nextLine();
            switch(choice){
                case 1:
                    System.out.println("Enter new session type: (LEC/TUT/LAB)");
                    String type = sc.nextLine();
                    session.setType(type);
                    success = true;
                    break;
                case 2:
                    System.out.println("Enter new session group : (SEP1/CE3/SEA2)");
                    String group = sc.nextLine();
                    session.setGroup(group);
                    success = true;
                    break;
                case 3:
                    System.out.println("Enter new session timing: (Mon 15:00 - 17:00/ Fri 09:00 - 11:00)");
                    String dayTime = sc.nextLine();
                    session.setDayTime(dayTime);
                    success = true;
                    break;
                case 4:
                    System.out.println("Enter new session location: (LT19a/TRx44/SWLAB3)");
                    String location = sc.nextLine();
                    session.setLocation(location);
                    success = true;
                    break;
                case 5:
                    System.out.println("Enter new session tutor name: ");
                    String tutorName = sc.nextLine();
                    session.setTutorName(tutorName);
                    success = true;
                    break;
                case 6:
                    System.out.println("Enter session's new max capacity: ");
                    int maxCapacity = sc.nextInt();
                    if(session.getNumberRegistered()> maxCapacity){
                        System.out.println("Please enter a valid capacity!");
                        return false;
                    }
                    session.setMaxCapacity(maxCapacity);
                    sc.nextLine();
                    success = true;
                    break;
                default: 
                    System.out.println("Please select a valid field!");
            }
            System.out.println("Modified session: "+ session.toString());
            System.out.println("Do you wish to continue modifying the session (Y/N)");
            conti = sc.nextLine().toUpperCase().charAt(0);
        }while(conti == 'Y');
    return success;     
        
    }

    //print session catalogue
    /**
     * A method to print all {@link Session} contained in this course.
     */
    public void printIndexList(){
        int i;

        System.out.println("Sessions for " + this.courseName + " " + this.courseCode);
        for(i = 0; i < indexList.size(); i++){
            System.out.println(indexList.get(i));
        }
    }

    /**
     * A method to set the Assessment information of the calling Course object.
     * @param assessment Assessment component of this Course object. 
     * @return
     */
    public int setAssessment(Assessment assessment){
        results.add(assessment);
        return 0;
    }

    //return 0 if added successfully, -1 if full, -2 if student is inside -3 if group does not exist
    public int registerStudent(Student student, String group, String type){
        int result = -3;
        for(int i = 0; i < indexList.size();  i++){
            if(indexList.get(i).getGroup().equals(group) && indexList.get(i).getType().equals(type)){
                result = indexList.get(i).addStudent(student);
                //result is -1 if full, -2 if student is already inside, 0 if success
            }
        }
        return result;
    }

    public int deregisterStudent(Student student){
        int sessionCount = 0;
        for(int i = 0; i < indexList.size(); i++){
            ArrayList<Student> students = indexList.get(i).getStudentRegistered();
            for(int j = 0; j < students.size(); j++){
                if(students.get(j).equals(student)){
                    sessionCount++;
                    students.remove(student);
                    indexList.get(i).setNumberRegistered(indexList.get(i).getNumberRegistered()-1);
                }
            }

            for(int z = 0; z < results.size(); z++){
                results.get(z).removeAssessmentResult(student);
            }
        }
        return sessionCount;
    }

    public void printSessions(){
        for (int i = 0; i < indexList.size(); i++){
            System.out.println(indexList.get(i) + " | Vacancy: " 
            + indexList.get(i).getVacancy() + "/" + indexList.get(i).getMaxCapacity());
        }
    }

    public ArrayList<Assessment> getAssessment(){
        return this.results;
    }

    public String toString(){
        String codeFormat = "|| %1$6s :";
        String nameFormat = " %2$-30s ";
        String tutorFormat = "| %3$-30s | ";
        String AUFormat = "%4$-1s ||";
        String format = codeFormat.concat(nameFormat).concat(tutorFormat).concat(AUFormat);
        return String.format(format, this.courseCode, this.courseName, "Coordinator: " + this.courseCoordinator, "AU: " + this.AU);
    }

    public ArrayList<Session> getAllSession(){
        return this.indexList;
    }

    public boolean studentRegistered(Student student){
        for(int i = 0; i < indexList.size(); i++){
            if(indexList.get(i).getStudentRegistered().contains(student)) return true;
        }
        return false;
    }

    public void clearAssessments(){
        results.clear();
    }

    public void enterResults(Assessment assessment, Student student, double marks){
        assessment.storeAssessmentResult(student, marks);
    }
}