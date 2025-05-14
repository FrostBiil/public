package dtu.group5.frontend.controller;

import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.WorkedHoursService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

// Made by Matthias(s245759)
public class WorkedHoursController {
  private final WorkedHoursService workedHoursService;

  // Made by Matthias(s245759)
  public WorkedHoursController(WorkedHoursService workedHoursService) {
    this.workedHoursService = workedHoursService;
  }

  // Made by Matthias(s245759)
  public List<ProjectActivity> listUnregisteredActivitiesToday(String initials) {
    return workedHoursService.listUnregisteredActivitiesToday(initials);
  }

  // Made by Matthias(s245759)
  public Optional<String> registerWorkedHours(int projectNumber, String activityTitle, double hours) {
    return workedHoursService.registerWorkedHours(projectNumber, activityTitle, hours);
  }

  // Made by Matthias(s245759)
  public Optional<String> editWorkedHours(int projectNumber, String activityTitle, String initials, String fieldName, double hours, Date date) {
    return workedHoursService.editWorkedHours(projectNumber, activityTitle, initials, fieldName, hours, date);
  }

  // Made by Matthias(s245759)
  public Optional<String> deleteWorkedHours(int projectNumber, String activityTitle, Date date) {
    return workedHoursService.deleteWorkedHours(projectNumber, activityTitle, date);
  }

  // Made by Matthias(s245759)
  public List<String[]> getWorkedHoursByCoworker(String initials, Date date) {
    return workedHoursService.getWorkedHoursByCoworker(initials, date);
  }
}
