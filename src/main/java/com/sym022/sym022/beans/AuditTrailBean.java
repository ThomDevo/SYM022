package com.sym022.sym022.beans;

import com.sym022.sym022.entities.AuditTrailEntity;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.utilities.FilterOfTable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ManagedBean
@SessionScoped
public class AuditTrailBean extends FilterOfTable<AuditTrailEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private AuditTrailEntity auditTrail = new AuditTrailEntity();
    private AuditTrailService auditTrailService = new AuditTrailService();

    /*--- Getters and Setters ---*/

    public AuditTrailEntity getAuditTrail() {
        return auditTrail;
    }

    public void setAuditTrail(AuditTrailEntity auditTrail) {
        this.auditTrail = auditTrail;
    }

    public AuditTrailService getAuditTrailService() {
        return auditTrailService;
    }

    public void setAuditTrailService(AuditTrailService auditTrailService) {
        this.auditTrailService = auditTrailService;
    }
}
