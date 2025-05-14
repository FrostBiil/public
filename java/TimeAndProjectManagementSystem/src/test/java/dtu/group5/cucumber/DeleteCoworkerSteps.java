package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

public class DeleteCoworkerSteps extends BaseStepDefinition {

    @When("a coworker is deleted")
    public void aCoworkerIsDeleted(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();

        String initials = row.get("Initials");

        Coworker coworker = services.getCoworkerService().get(initials).orElse(null);

        Optional<String> error = ServiceHolder.getInstance().getCoworkerService().removeCoworker(coworker);
        error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }
}
