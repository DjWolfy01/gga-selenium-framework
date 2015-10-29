package com.epam.career.page_objects.entities;

/**
 * Created by Roman_Iovlev on 10/22/2015.
 */
public class Attendee {
    public JobSearchFilter filter = new JobSearchFilter();

    public String name = "Roman";
    public String lastName = "Iovlev";
    public String email = "roman_iovlev@epam.com";
    public String country = "Russian Federation";
    public String city = "Saint-Petersburg";
    public String cv = "D:\\AutomationProjects\\GGA UI Framework\\OLD-gga-selenium-framework\\epam-career-tutorial-tests\\src\\test\\resources\\cv.txt";
    public String comment = "I WANT TO WORK IN EPAM!!!";

    @Override
    public String toString() {
        return name + " " + lastName;
    }
}
