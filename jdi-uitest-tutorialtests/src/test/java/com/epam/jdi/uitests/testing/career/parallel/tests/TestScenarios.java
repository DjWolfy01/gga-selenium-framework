package com.epam.jdi.uitests.testing.career.parallel.tests;

import com.epam.jdi.uitests.testing.career.page_objects.entities.Attendee;
import com.epam.jdi.uitests.testing.career.page_objects.site.EpamSiteParallel;
import com.epam.web.matcher.testng.Check;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import static com.epam.jdi.uitests.testing.career.page_objects.enums.HeaderMenu.CAREERS;
import static com.epam.jdi.uitests.testing.career.page_objects.enums.Preconditions.HOME_PAGE;
import static com.epam.jdi.uitests.web.WebSettings.getDriverFactory;
import static com.epam.jdi.uitests.web.selenium.driver.DriverTypes.CHROME;
import static com.epam.jdi.uitests.web.selenium.elements.composite.Site.Init;
import static java.lang.Thread.currentThread;

/**
 * Created by Roman_Iovlev on 12/17/2015.
 */
public class TestScenarios {
    private final static transient ReentrantLock rLock = new ReentrantLock();
    private static Map<Long, String> threadsDrivers = new HashMap<>();

    private static String registerNewdriver(long threadId) {
        String driverName = getDriverFactory().registerDriver(CHROME);
        threadsDrivers.put(threadId, driverName);
        return driverName;
    }

    public synchronized static String getDriverName() {
        long threadId = currentThread().getId();
        return threadsDrivers.containsKey(threadId)
                ? threadsDrivers.get(threadId)
                : registerNewdriver(threadId);
    }

    protected static void sendCVTest(String testName) {
        Attendee attendee = new Attendee(testName + " Thread: " + currentThread().getId());
        attendee.cv = null;
        EpamSiteParallel site = Init(EpamSiteParallel.class, getDriverName());
        site.isInState(HOME_PAGE);
        site.headerMenu.select(CAREERS);
        site.careerPage.checkOpened();
        site.careerPage.jobFilter.search(attendee.filter);

        site.jobListingPage.checkOpened();
        new Check("Table is not empty").isFalse(site.jobListingPage.jobsList::isEmpty);
        site.jobListingPage.getJobRowByName("Senior QA Automation Engineer");

        site.jobDescriptionPage.addCVForm.submit(attendee);
        new Check("Captcha").contains(() -> site.jobDescriptionPage.captcha.getAttribute("class"), "form-field-error");
    }
}
