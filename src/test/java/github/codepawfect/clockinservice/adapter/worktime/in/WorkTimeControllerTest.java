package github.codepawfect.clockinservice.adapter.worktime.in;

import static io.restassured.RestAssured.given;

import github.codepawfect.clockinservice.adapter.BaseIntegrationTest;
import github.codepawfect.clockinservice.adapter.auth.out.model.UserDocument;
import github.codepawfect.clockinservice.adapter.common.JwtUtils;
import github.codepawfect.clockinservice.adapter.worktime.in.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.worktime.out.repository.WorkTimeRepository;
import io.restassured.http.Cookie;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;

/** WorkTimeControllerTest is a test class for the WorkTimeController. */
class WorkTimeControllerTest extends BaseIntegrationTest {
  @Autowired private WorkTimeRepository workTimeRepository;
  @Autowired private JwtUtils jwtUtils;

  @Test()
  @DisplayName(
      "Should return 201 CREATED when create a new work time entry successfully via POST /api/worktimes")
  void createWorkTime() {
    CreateWorkTimeRequest request =
        new CreateWorkTimeRequest(LocalDate.now(), 8, "Work on ticket 767");
    UserDetails testUserDetails =
        new UserDocument("testUserId", "testUser", "encodedPassword", List.of("USER"));
    ResponseCookie authCookie = jwtUtils.generateJwtCookie(testUserDetails);
    Cookie authCookieRestAssured =
        new Cookie.Builder(authCookie.getName(), authCookie.getValue()).build();

    given()
        .cookie(authCookieRestAssured)
        .when()
        .contentType("application/json")
        .body(request)
        .post("/api/worktimes")
        .then()
        .statusCode(HttpStatus.CREATED.value());

    Assertions.assertThat(workTimeRepository.count()).isEqualTo(1);

    workTimeRepository.deleteAll();
  }

  @Test
  @DisplayName("Should return 200 OK when getting work times via GET /api/worktimes")
  void getWorkTimes() {}

  @Test
  @DisplayName("Should return 204 NO_CONTENT when delete work time via DELETE /api/worktimes/{id}")
  void deleteWorkTime() {}
}
