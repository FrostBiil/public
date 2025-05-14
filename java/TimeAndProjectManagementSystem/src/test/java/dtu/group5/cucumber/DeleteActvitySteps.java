package dtu.group5.cucumber;

import dtu.group5.backend.model.BaseActivity;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import java.util.Map;
import java.util.Optional;

public class DeleteActvitySteps extends BaseStepDefinition {

    @When("an activity is deleted")
    public void anActivityIsDeleted(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();

       String title = row.get("Title");

        BaseActivity activity = services.getActivityService().get(title).orElse(null);

        Optional<String> error = ServiceHolder.getInstance().getActivityService().removeActivity(activity);
        error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
    }
}
