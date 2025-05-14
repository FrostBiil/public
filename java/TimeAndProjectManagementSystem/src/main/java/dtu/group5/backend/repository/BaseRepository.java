package dtu.group5.backend.repository;

import dtu.group5.backend.model.IModel;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// Made by Mattias (s245759)
public abstract class BaseRepository<K, T extends IModel> implements IRepository<K, T> {
  protected final Map<K, T> repository = new ConcurrentHashMap<>();

  // Made by Mattias (s245759)
  public BaseRepository() {}

  // Made by Mattias (s245759)
  @Override
  public Optional<T> get(K key) {
    return Optional.ofNullable(repository.get(key));
  }

  // Made by Mattias (s245759)
  @Override
  public List<T> getList() {
    return this.repository.values().stream().toList();
  }

  // Made by Mattias (s245759)
  @Override
  public boolean contains(K key){
    return repository.containsKey(key);
  }

  // Made by Mattias (s245759)
  @Override
  public boolean remove(K key) {
    if (repository.containsKey(key)) {
      repository.remove(key);
      this.save();
      return true;
    }
    return false;
  }

  // Made by Mattias (s245759)
  @Override
  public void clear() {
    this.repository.clear();
    this.save();
  }

  // Made by Mattias (s245759)
  @Override
  public Collection<T> getAll() {
    return repository.values();
  }
}
