module htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota to javafx.fxml;
    exports htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota;
}