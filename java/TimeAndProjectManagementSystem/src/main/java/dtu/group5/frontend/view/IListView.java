package dtu.group5.frontend.view;

import java.util.List;

public interface IListView<T> extends IView {
  List<T> getList();
}
