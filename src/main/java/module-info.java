module htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.desktop;

    opens htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota to javafx.fxml;
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application;
}