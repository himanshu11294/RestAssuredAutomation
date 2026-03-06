package api.test;

import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.*;
import api.payload.*;
import api.utilities.ExtentTestListener;
import io.restassured.response.Response;


@Listeners(ExtentTestListener.class)
public class UserTest {

    private static final Logger logger = LogManager.getLogger(UserTest.class);

    Faker faker;
    User payload;
@BeforeClass
    public void setupData() {
        faker = new Faker();
        payload = new User();
        payload.setId(faker.idNumber().hashCode());
        payload.setUsername(faker.name().username());
        payload.setFirstName(faker.name().firstName());
        payload.setLastName(faker.name().lastName());
        payload.setEmail(faker.internet().emailAddress());
        payload.setPassword(faker.internet().password(5, 10));
        payload.setPhone(faker.phoneNumber().cellPhone());
        payload.setUserStatus(1);   
    }
    @Test(priority = 0)
    public void testPostUser() {
    
        Response response= UserEndPoints.createUser(payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
       
    }   

    @Test(priority = 2)
    public void testReadUser() {
        logger.info("testuser #########.  ::::  {}", payload.getUsername());
        Response response = UserEndPoints.readUser(this.payload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

        @Test(priority = 1) 
    public void testUpdateUser() {
        payload.setFirstName(faker.name().firstName());
        payload.setLastName(faker.name().lastName());   
        payload.setEmail(faker.internet().emailAddress());
        Response response = UserEndPoints.updateUser(this.payload.getUsername(), payload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("After #########.  ::::  {}", this.payload.getUsername());
        Response response1 = UserEndPoints.readUser(this.payload.getUsername());
        response1.then().log().all();
    }
}
