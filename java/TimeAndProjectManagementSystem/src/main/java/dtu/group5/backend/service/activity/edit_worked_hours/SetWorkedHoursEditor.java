package dtu.group5.backend.service.activity.edit_worked_hours;

import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.ProjectActivity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SetWorkedHoursEditor implements WorkedHoursEditor {

    @Override
    public boolean supports(String fieldName) {
        return "workedHours".equalsIgnoreCase(fieldName);
    }

    @Override
    public Optional<String> edit(ProjectActivity activity, Coworker coworker, Date date, double hours) {
        if (activity == null) return Optional.of("Activity cannot be null");
        if (coworker == null) return Optional.of("Coworker cannot be null");
        if (date == null) return Optional.of("Date cannot be null");
        if (hours < 0) return Optional.of("Worked hours must be non-negative");

        Map<Coworker, Map<Date, Double>> workedMap = activity.getWorkedHoursPerCoworker();
      Map<Date, Double> dailyMap = workedMap.computeIfAbsent(coworker, k -> new HashMap<>());

      if (hours == 0) {
            dailyMap.remove(date);
            if (dailyMap.isEmpty()) {
                workedMap.remove(coworker);
                activity.getAssignedCoworkers().remove(coworker);
            }
        } else {
            dailyMap.put(date, hours);
            if (!activity.getAssignedCoworkers().contains(coworker)) {
                activity.addAssignedCoworker(coworker);
            }
        }

        return Optional.empty();
    }
}
