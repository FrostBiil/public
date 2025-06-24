package dtu.group5.frontend.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Controller<T> {
    public Optional<String> handleCreate(T t);
    public Optional<String> handleEdit(T t, Map<String, Object> fieldsToChange);
    public Optional<String> handleDelete(T t);
    public List<T> getList();
}
