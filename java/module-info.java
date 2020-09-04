module DopeMathers.AlkoholTest {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires sisu.guice;
	requires java.desktop;

    opens DopeMathers.AlkoholTest to javafx.fxml;
    exports DopeMathers.AlkoholTest;
}