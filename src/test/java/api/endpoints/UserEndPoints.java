package api.endpoints;

import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

import api.payload.User;
import api.utilities.ExtentReportManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import static api.endpoints.Routes.*;

public class UserEndPoints {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static Response createUser(User payload) {
        Response response = given()
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(payload)
            .when()
            .post(post_url);

        logApiInteraction("POST", post_url, payload, response);
        return response;
    }

    public static Response readUser(String username) {
        Response response = given()
            .pathParam("username", username)
            .when()
            .get(get_url);

        logApiInteraction("GET", get_url.replace("{username}", username), null, response);
        return response;
    }

    public static Response updateUser(String username, User payload) {
        Response response = given()
            .pathParam("username", username)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
            .body(payload)
            .when()
            .put(put_url);

        logApiInteraction("PUT", put_url.replace("{username}", username), payload, response);
        return response;
    }

    public static Response deleteUser(String username) {
        Response response = given()
            .pathParam("username", username)
            .when()
            .delete(delete_url);

        logApiInteraction("DELETE", delete_url.replace("{username}", username), null, response);
        return response;
    }

    private static void logApiInteraction(String method, String endpoint, Object requestBody, Response response) {
        ExtentReportManager.info("<b>Request:</b> " + method + " " + endpoint);

        if (requestBody != null) {
            ExtentReportManager.info("<b>Request Body</b><pre>" + toPrettyJson(requestBody) + "</pre>");
        }

        ExtentReportManager.info("<b>Response Status:</b> " + response.getStatusCode());
        ExtentReportManager.info("<b>Response Body</b><pre>" + response.asPrettyString() + "</pre>");
    }

    private static String toPrettyJson(Object value) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (Exception exception) {
            return String.valueOf(value);
        }
    }
}