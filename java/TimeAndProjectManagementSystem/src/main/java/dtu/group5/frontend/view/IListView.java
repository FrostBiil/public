package dtu.group5.frontend.view;

import java.util.List;

// Made By Matthias (s245759)
public interface IListView<T> extends IView {
  List<T> getList();
}
