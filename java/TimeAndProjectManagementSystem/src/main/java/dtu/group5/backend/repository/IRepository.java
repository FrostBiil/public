package dtu.group5.backend.repository;

import dtu.group5.backend.model.IModel;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

// Made by Mattias (s245759)
public interface IRepository<K, T extends IModel> {
  Optional<T> get(K key);
  List<T> getList();
  boolean add(T type);
  void save();
  boolean contains(K key);
  Collection<T> getAll();
  boolean remove(K key);
  void clear();
}
