package dtu.group5.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

public class CreateCoworkerSteps extends BaseStepDefinition {
    @When("a new coworker is created")
    public void aNewCoworkerIsCreated(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();

        String initials = row.get("Initials");
        String name = row.get("Name");

        Optional<String> error = ServiceHolder.getInstance().getCoworkerService().create(initials, name);
        error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }
}
