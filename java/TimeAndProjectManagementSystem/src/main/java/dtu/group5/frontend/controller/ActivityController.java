package dtu.group5.frontend.controller;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.ActivityService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

// Made by Elias (s241121)
public class ActivityController implements Controller<BaseActivity> {
    private final ActivityService activityService;

    // Made by Elias (s241121)
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // Made by Matthias(s245759)
    @Override
    public Optional<String> handleCreate(BaseActivity activity) {
        return this.activityService.create(activity);
    }

    // Made by Matthias(s245759)
    @Override
    public Optional<String> handleEdit(BaseActivity activity, Map<String, Object> fieldsToChange) {
        return this.activityService.editActivity(activity, fieldsToChange);
    }

    // Made by Elias (s241121)
    @Override
    public Optional<String> handleDelete(BaseActivity activity) {
        return this.activityService.removeActivity(activity);
    }

    // Made by Elias (s241121)
    public List<Coworker> listAvailableCoworkers(ProjectActivity activity) {
        return this.activityService.listAvailableCoworkers(activity);
    }

    // Made by Elias (s241121)
    @Override
    public List<BaseActivity> getList() {
       return activityService.getList();
    }
}
