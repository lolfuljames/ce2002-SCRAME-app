import java.util.*;

public class Course{
    //Record of Session (Tut, Lab and Lecture) under a course
    private ArrayList<Session> indexList = new ArrayList<Session>();
    //Record of Assessment (Exam and Coursework) under a course
    //index 0 is equivalent to exam marks, the rest are coursemarks
    private ArrayList<Assessment> results = new ArrayList<Assessment>(); 

    //Other relevant information of a course
    private String courseName;
    private String courseCode;
    private int AU;
    private String courseCoordinator;

    
    //default constructor
    public Course(){}

    public Course(String courseName, String courseCode, int AU, String courseCoordinator){
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.AU = AU;
        this.courseCoordinator = courseCoordinator;
    }

    //accessers and modifiers
    public String getCourseName(){
        return this.courseName;
    }

    public String getCourseCode(){
        return this.courseCode;
    }

    public int getAU(){
        return this.AU;
    }

    public String getCourseCoordinator(){
        return this.courseCoordinator;
    }

    //Add in new Session (Lab, Tut, LT)
    //need to check for whethere there's existing session already
    public boolean addSession(){
        boolean success = false;

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter session type: (lec/tut/lab)");
        String type = sc.next();
        System.out.println("Enter session group : (SEP1/CE3/SEA2)");
        String group = sc.next();
        System.out.println("Enter session timing: (Mon 15:00 - 17:00/ Fri 09:00 - 11:00)");
        String dayTime = sc.next();
        System.out.println("Enter session location: (LT19a/TRx44/SWLAB3)");
        String location = sc.next();
        System.out.println("Enter session tutor name: ");
        String tutorName = sc.next();
        System.out.println("Enter session's max capacity: ");
        int maxCapacity = sc.nextInt();
        if(sessionExist(group) >= 0){
            System.out.println("This session is already in!");
        }else{
            success = indexList.add(new Session(type, group, dayTime, location, tutorName, maxCapacity, 0));
        }
        return success;
    }

    //Remove session
    public boolean removeSession(){
        boolean success;
        Scanner sc = new Scanner(System.in);
        String group;
        int index;

        printIndexList();
        System.out.println("Which session do you want to remove?");
        group = sc.next();
        index = sessionExist(group);
        if(index >= 0){
            success = indexList.remove(indexList.get(index)); //if removed then return true
            return success;
        }else{
            return false; //else false
        }
    }

    //print session catalogue
    public void printIndexList(){
        int i;

        System.out.println("Sessions for " + this.courseName + " " + this.courseCode);
        for(i = 0; i < indexList.size(); i++){
            System.out.println(indexList.get(i).getGroup() + " " + indexList.get(i).getType() + " " +indexList.get(i).dayTime());
        }
    }

    //check if session exist according to group name first
    //may need to add more though
    public int sessionExist(String sessionGroup){
        int i;
        int index = -1;

        for(i = 0; i < indexList.size(); i++){  //return index if found, return -1 if not found
            if(indexList.get(i).getGroup() == sessionGroup){
                index = i;
                break;
            }
        }
        return index;
    }

    //Set Course Assessment Information
    public int setAssessment(){
        String name;
        int weightage;
        int totalWeightage; //total weightage must be equal to 100
        Scanner sc = new Scanner(System.in);
        while(totalWeightage!=100){
            System.out.println("Enter assessment type: (Finals, Quiz, Lab Report)");
            name = sc.next();
            System.out.println("Enter assessment weightage: (SEP1/CE3/SEA2)");
            weightage = sc.nextInt();
            if(weightage + totalWeightage > 100){
                System.out.println("Invalid weightage! Should not exceed a total of 100!");
            }
            else{
                System.out.println("Confirm entry of \"" + name + "\" weightage: " + weightage + "? (Y/N)");
                confirm = sc.next();
                if(confirm == "Y"){
                    totalWeightage += weightage;
                    results.add(new Assessment(name,weightage));
                    System.out.println("Results component \"" + name + "\" is added with a weightage of " + weightage);
                }
                else{
                    System.out.println("Results component not added.");
                }
            }
        }
        System.out.println("Results weightage completed....");
    }
}