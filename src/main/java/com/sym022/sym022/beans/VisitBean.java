package com.sym022.sym022.beans;

import com.sym022.sym022.entities.VisitEntity;
import com.sym022.sym022.services.VisitService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ManagedBean
@SessionScoped
public class VisitBean extends FilterOfTable<VisitEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private VisitEntity visit = new VisitEntity();
    private VisitService visitService = new VisitService();
    private List<VisitEntity> allVisit;

    /*---Method---*/

    /**
     * Method to have all the visits in a select Menu
     */
    public void initAllEditorVisit(){
        EntityManager em = EMF.getEM();
        VisitService visitService = new VisitService();
        EntityTransaction transaction = em.getTransaction();
        try{
            transaction.begin();
            this.allVisit = visitService.findVisitAll(em);
            //ProcessUtils.debug(""+this.allVisit.size());
            transaction.commit();
        }catch(Exception e){
            this.allVisit = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /*--- Getters and Setters ---*/

    public VisitEntity getVisit() {
        return visit;
    }

    public void setVisit(VisitEntity visit) {
        this.visit = visit;
    }

    public VisitService getVisitService() {
        return visitService;
    }

    public void setVisitService(VisitService visitService) {
        this.visitService = visitService;
    }

    public List<VisitEntity> getAllVisit() {
        return allVisit;
    }

    public void setAllVisit(List<VisitEntity> allVisit) {
        this.allVisit = allVisit;
    }
}
