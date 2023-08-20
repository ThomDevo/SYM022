package com.sym022.sym022.beans;

import com.sym022.sym022.entities.DmEntity;
import com.sym022.sym022.services.DmService;
import com.sym022.sym022.utilities.FilterOfTable;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ManagedBean
@SessionScoped
public class DmBean extends FilterOfTable<DmEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private DmEntity dm = new DmEntity();
    private DmService dmService = new DmService();
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*--- Getters and Setters ---*/

    public DmEntity getDm() {
        return dm;
    }

    public void setDm(DmEntity dm) {
        this.dm = dm;
    }

    public DmService getDmService() {
        return dmService;
    }

    public void setDmService(DmService dmService) {
        this.dmService = dmService;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    public AuditTrailBean getAuditTrailBean() {
        return auditTrailBean;
    }

    public void setAuditTrailBean(AuditTrailBean auditTrailBean) {
        this.auditTrailBean = auditTrailBean;
    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }
}
