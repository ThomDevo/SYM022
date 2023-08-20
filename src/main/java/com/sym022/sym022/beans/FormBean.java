package com.sym022.sym022.beans;

import com.sym022.sym022.entities.FormEntity;
import com.sym022.sym022.services.FormService;
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
public class FormBean extends FilterOfTable<FormEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private FormEntity form = new FormEntity();
    private FormService formService = new FormService();
    private List<FormEntity> allForm;

    /*---Method---*/

    /**
     * Method o have all the forms in a select Menu
     */
    public void initAllEditorForm(){
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        FormService formService = new FormService();
        try{
            transaction.begin();
            this.allForm = formService.findFormAll(em);
            //ProcessUtils.debug(""+this.allForm.size());
            transaction.commit();
        }catch(Exception e){
            this.allForm = new ArrayList<>();
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    /*--- Getters and Setters ---*/

    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

    public FormService getFormService() {
        return formService;
    }

    public void setFormService(FormService formService) {
        this.formService = formService;
    }

    public List<FormEntity> getAllForm() {
        return allForm;
    }

    public void setAllForm(List<FormEntity> allForm) {
        this.allForm = allForm;
    }
}
