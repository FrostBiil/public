package dtu.group5.cucumber;

import dtu.group5.backend.model.Coworker;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import static dtu.group5.backend.util.DateUtil.parseDate;

public class CreateFixedActivitySteps extends BaseStepDefinition {
    @When("a new fixed activity is created")
    public void createFixedActivity(DataTable dataTable) {
        Map<String, String> row = dataTable.asMaps().getFirst();
        String title = row.get("Title");
        boolean finished = Boolean.parseBoolean(row.get("Finished"));
        String description = row.get("Description");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = parseDate(row.get("StartDate"));
            endDate = parseDate(row.get("EndDate"));
        } catch (Exception e) {
            ErrorMessageHolder.getInstance().setErrorMessage(e.getMessage());
        }

        Coworker coworker = null;
        try {
            Optional<Coworker> coworkerOpt = services.getCoworkerService().get(row.get("Initials"));
            if (coworkerOpt.isPresent()) {
                coworker = coworkerOpt.get();
            }
        } catch (Exception e) {
            ErrorMessageHolder.getInstance().setErrorMessage(e.getMessage());
        }

        try {
            Optional<String> error = services.getActivityService().createFixedActivity(title, finished, description, startDate, endDate, coworker);
            error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
        } catch (Exception e) {
            ErrorMessageHolder.getInstance().setErrorMessage(e.getMessage());
        }
    }

    @When("new fixed activities is created")
    public void createFixedActivities(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String title = row.get("Title");
            boolean finished = Boolean.parseBoolean(row.get("Finished"));
            String description = row.get("Description");
            Date startDate = parseDate(row.get("StartDate"));
            Date endDate = parseDate(row.get("EndDate"));
            Optional<Coworker> coworker = services.getCoworkerService().get(row.get("Initials"));
            if (coworker.isPresent()) {
                try {
                    Optional<String> error = services.getActivityService().createFixedActivity(title, finished, description, startDate, endDate, coworker.get());
                    error.ifPresent(ErrorMessageHolder.getInstance()::setErrorMessage);
                } catch (Exception e) {
                    ErrorMessageHolder.getInstance().setErrorMessage(e.getMessage());
                }
            } else {
                throw new IllegalArgumentException("Coworker not found: " + row.get("Initials"));
            }
        }
    }
}
