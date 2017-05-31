package campusme.campusme;

/**
 * Created by Ru55ianMafia on 10/21/2016.
 */
public class User {

    private String username, schoolEmail, password, confirmPassword, state, school;

    public User(String username, String schoolEmail, String password, String confirmPassword, String state, String school) {
        this.username = username;
        this.schoolEmail = schoolEmail;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.state = state;
        this.school = school;
    }

    public String getUsername(){return username;}

    public String getSchoolEmail(){return schoolEmail;}

    public String getPassword(){return password;}

    public String getConfirmPassword(){return confirmPassword;}

    public String getState(){return state;}

    public String getSchool(){return state;}

    public void setUsername(String username){this.username=username;}

    public void setSchoolEmail(String schoolEmail){this.schoolEmail=schoolEmail;}
}




