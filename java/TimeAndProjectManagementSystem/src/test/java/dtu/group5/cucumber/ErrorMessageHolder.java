package dtu.group5.cucumber;

public class ErrorMessageHolder {
  private static ErrorMessageHolder instance;

  private String errorMessage;

  private ErrorMessageHolder() {}

  public static ErrorMessageHolder getInstance() {
      if (instance == null) {
          instance = new ErrorMessageHolder();
      }
      return instance;
  }

  public String getErrorMessage() {
      return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
  }

  public void clear() {
      this.errorMessage = "";
  }
}
