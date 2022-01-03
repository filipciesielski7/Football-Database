package com.example.application.components;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

public class ConfirmDialogComponent {
    private ConfirmDialog confirmDialog;
    private String objectInfo;

    public ConfirmDialogComponent(String objectInfo) {
        this.confirmDialog = new ConfirmDialog();
        this.objectInfo = objectInfo;
    }

    public ConfirmDialog getConfirmDialog() {
        return confirmDialog;
    }

    public void setConfirmDialog(ConfirmDialog confirmDialog) {
        this.confirmDialog = confirmDialog;
    }

    public String getObjectInfo() {
        return objectInfo;
    }

    public void setObjectInfo(String objectInfo) {
        this.objectInfo = objectInfo;
    }

    public ConfirmDialog getDeleteConfirmDialog(){
        this.confirmDialog.setHeader("Delete");
        this.confirmDialog.setText("Are you sure you want to permanently delete " + this.objectInfo + "?");
        this.confirmDialog.setCancelable(true);
        this.confirmDialog.setConfirmText("Delete");
        this.confirmDialog.setConfirmButtonTheme("error primary");

        return confirmDialog;
    }

    public ConfirmDialog getModifyConfirmDialog(){
        this.confirmDialog.setHeader("Modify");
        this.confirmDialog.setText("Are you sure you want to permanently modify already existing " + this.objectInfo + "?");
        this.confirmDialog.setCancelable(true);
        this.confirmDialog.setConfirmText("Modify");
//        this.confirmDialog.setConfirmButtonTheme("error primary");

        return confirmDialog;
    }
}
