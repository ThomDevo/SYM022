package com.sym022.sym022.beans;

import com.sym022.sym022.entities.AuditTrailEntity;
import com.sym022.sym022.entities.EventEntity;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class AuditTrailBean extends FilterOfTable<AuditTrailEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private AuditTrailEntity auditTrail = new AuditTrailEntity();
    private AuditTrailService auditTrailService = new AuditTrailService();
    private List<AuditTrailEntity> allAuditTrails;
    @Inject
    private ConnectionBean connectionBean;

    /*---Method---*/

    /**
     * Method to find all permitted auditTrails
     */
    public void researchFilterAllAuditTrails(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = auditTrailService.findAuditTrailByFilterAndOrderDesc(connectionBean.getUser().getIdUser(),this.filter,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
    }

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

    public List<AuditTrailEntity> getAllAuditTrails() {
        return allAuditTrails;
    }

    public void setAllAuditTrails(List<AuditTrailEntity> allAuditTrails) {
        this.allAuditTrails = allAuditTrails;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }
}
