package lubl;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {


    String baseUrl = "https://reqres.in",
            singleUserUrl= "/api/users/2",
            userNotFoundUrl="/api/users/23",
            singleResourceUrl="/api/unknown/2",
            createUrl="/api/users",
            deleteUrl="/api/users/2",
            registerUrl="/api/register",
            expectedResponse="{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://reqres.in/img/faces/2-image.jpg\"},\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}",
            expectedResponse2="{\"data\":{\"id\":2,\"name\":\"fuchsia rose\",\"year\":2001,\"color\":\"#C74375\",\"pantone_value\":\"17-2031\"},\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}",
            body = "{\"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"}",
            body2 ="{\"email\": \"sydney@fife\"}",
            name="morpheus",
            job="leader",
            errormassege="Missing password";

    @BeforeAll
    static void setUp() {
        baseURI="https://reqres.in";}


    @Test
    public void getSingleUser() {
        Response response = get(singleUserUrl)
                .then()
                .statusCode(200)
                .extract().response();
        assertEquals(expectedResponse, response.asString());
    }

    @Test
    void userNotFound() {
        given()
                .when()
                .get(userNotFoundUrl)
                .then()
                .statusCode(404)
                .body(is("{}"));
    }

    @Test
    public void getSingleResource() {
        Response response = get(singleResourceUrl)
                .then()
                .statusCode(200)
                .extract().response();
        assertEquals(expectedResponse2, response.asString());
    }

    @Test
    void createUser() {
        given()
                .body(body)
                .contentType(JSON)
                .when()
                .post(createUrl)
                .then()
                .statusCode(201)
                .body("name", is(name))
                .body("job", is(job));
    }

    @Test
    void deleteUser() {
        given()
                .when()
                .delete(deleteUrl)
                .then()
                .statusCode(204);
    }

    @Test
    void registerUserUnsuccesfull() {
        given()
                .body(body2)
                .contentType(JSON)
                .when()
                .post(registerUrl)
                .then()
                .statusCode(400)
                .body("error", is(errormassege));
    }
}
