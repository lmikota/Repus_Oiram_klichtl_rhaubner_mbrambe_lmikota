package htl.steyr.repus_oiram_klichtl_rhaubner_mbrambe_lmikota.Data;

public class Dialog {

    private int dialogID;
    private String dialog;

    public Dialog(int dialogID, String dialog) {
        setDialogID(dialogID);
        setDialog(dialog);
    }

    /* ---------------------------------------------- Getter & Setter ----------------------------------------------  */

    public int getDialogID() {
        return dialogID;
    }

    public void setDialogID(int dialogID) {
        this.dialogID = dialogID;
    }

    public String getDialog() {
        return dialog;
    }

    public void setDialog(String dialog) {
        this.dialog = dialog;
    }
}