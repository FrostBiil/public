package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditCoworkerSteps extends BaseStepDefinition {
  @When("the coworker {string} field {string} is changed to {string}")
  public void theCoworkerFieldIsChangedTo(String arg0, String arg1, String arg2) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(arg0);
    assertTrue(coworkerOpt.isPresent(), "Coworker not found: " + arg0);
    Coworker coworker = coworkerOpt.get();
    Map<String, Object> data = Map.of(arg1, arg2);
    Optional<String> error = services.getCoworkerService().editCoworker(coworker, data);
    error.ifPresent(errorMessage::setErrorMessage);
  }

  @Then("the coworker {string} has name {string}")
  public void theCoworkerHasName(String arg0, String arg1) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(arg0);
    assertTrue(coworkerOpt.isPresent(), "Coworker not found: " + arg0);
    String actualName = coworkerOpt.get().getName();
    assertEquals(arg1, actualName, "Incorrect name");
  }

  @When("the coworker {string} field {string} is changed to {int}")
  public void theCoworkerFieldIsChangedTo(String arg0, String arg1, int arg2) {
    Optional<Coworker> coworkerOpt = services.getCoworkerService().get(arg0);
    assertTrue(coworkerOpt.isPresent(), "Coworker not found: " + arg0);
    Coworker coworker = coworkerOpt.get();
    Map<String, Object> data = Map.of(arg1, arg2);
    Optional<String> error = services.getCoworkerService().editCoworker(coworker, data);
    error.ifPresent(errorMessage::setErrorMessage);
  }
}
