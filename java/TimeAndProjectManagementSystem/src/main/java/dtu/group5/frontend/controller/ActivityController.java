package dtu.group5.frontend.controller;

import dtu.group5.backend.model.BaseActivity;
import dtu.group5.backend.model.Coworker;
import dtu.group5.backend.model.ProjectActivity;
import dtu.group5.backend.service.ActivityService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ActivityController implements Controller<BaseActivity> {
    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @Override
    public Optional<String> handleCreate(BaseActivity activity) {
        return this.activityService.create(activity);
    }

    @Override
    public Optional<String> handleEdit(BaseActivity activity, Map<String, Object> fieldsToChange) {
        return this.activityService.editActivity(activity, fieldsToChange);
    }

    @Override
    public Optional<String> handleDelete(BaseActivity activity) {
        return this.activityService.removeActivity(activity);
    }

    public List<Coworker> listAvailableCoworkers(ProjectActivity activity) {
        return this.activityService.listAvailableCoworkers(activity);
    }

    @Override
    public List<BaseActivity> getList() {
       return activityService.getList();
    }
}
