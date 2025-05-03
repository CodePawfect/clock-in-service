package github.codepawfect.clockinservice.adapter.in.worktime;

import static io.restassured.RestAssured.given;

import github.codepawfect.clockinservice.adapter.BaseIntegrationTest;
import github.codepawfect.clockinservice.shared.JwtUtils;
import github.codepawfect.clockinservice.adapter.driven.worktime.model.CreateWorkTimeRequest;
import github.codepawfect.clockinservice.adapter.driving.worktime.repository.WorkTimeRepository;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

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

    given()
        .cookie(getDefaultUserAuthToken())
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
