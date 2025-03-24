module htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.desktop;
    requires java.logging;

    // Nur existierende Packages exportieren!
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application;
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.gameElements;
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller;
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

    opens htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.application to javafx.fxml;
    opens htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Controller to javafx.fxml;
    opens htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data to com.google.gson;
}
