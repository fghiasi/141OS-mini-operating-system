build: src/*.java
	@echo ----- creating a jar file with java -----
	javac --module-path $(PATH_TO_FX) --add-modules javafx.controls,javafx.fxml,javafx.graphics src/*.java
	jar cfm 141OS.jar src/MANIFEST.MF src/*.class

