package com.sym022.sym022.beans;

import com.sym022.sym022.entities.SubjectEntity;
import com.sym022.sym022.services.SubjectService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.FilterOfTable;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class SubjectBean extends FilterOfTable<SubjectEntity> implements Serializable {

    /*--- Variable declaration ---*/
    private SubjectEntity subject = new SubjectEntity();
    private SubjectService subjectService = new SubjectService();
    private String messageErrorSubjectNum = "hidden";
    private List<SubjectEntity> allSubject;
    private List<SubjectEntity> allSubjectSelected;
    private List<SubjectEntity> allSubjectsBySite;
    @Inject
    private ConnectionBean connectionBean;

    /*---Method---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormSubjects();
        return redirect;
    }

    /**
     * Method to filter all ACTIVE subjects on the subjectNum
     */
    public void researchFilterSubject(){
        EntityManager em = EMF.getEM();
        try{
            filterOfTable = subjectService.findSubjectPermitted(connectionBean.getUser().getIdUser(),this.filter,em);
            ProcessUtils.debug(this.filter);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            em.close();
        }

    }

    /**
     * Method to filter all ACTIVE subjects on the authorized sites
     */
    public void initAllEditorSubject(){
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        SubjectService subjectService = new SubjectService();;
        try{
            transaction.begin();
            this.allSubject = subjectService.findSubjectPermitted(connectionBean.getUser().getIdUser(),this.filter,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
    }

    public String CalculateNumSubject(){
        String numSubject= "";
        int siteNumber;
        int sizeSubject;
        int total;
        EntityManager em = EMF.getEM();
        EntityTransaction transaction = em.getTransaction();
        SubjectService subjectService = new SubjectService();;
        try{
            transaction.begin();
            this.allSubjectsBySite = subjectService.findSubjectPermittedBySite(connectionBean.getUser().getIdUser(), this.subject.getSiteByIdSite().getIdSite(),this.filter,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        sizeSubject = this.allSubjectsBySite.size() + 1;
        siteNumber = subject.getSiteByIdSite().getSiteNum() * 100;
        total = siteNumber + sizeSubject;
        numSubject = String.valueOf(total);
        return numSubject;
    }

    /**
     * Method to have I18n messages in Back-end
     * @param summary
     * @param detail
     */
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Method to reset the form to add or update a subject
     */
    public void initFormSubjects(){
        this.subject.setSubjectNum(0);
        this.subject.setSubjectStatus(true);
        this.messageErrorSubjectNum = "hidden";
    }

    public String submitFormAddSubject(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        SubjectService subjectService = new SubjectService();
        try{
            subject.setSubjectStatus(true);
            subject.setSubjectNum(Integer.parseInt(CalculateNumSubject()));
            transaction.begin();
            subjectService.addSubject(subject,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the addSubject method: "+ e);
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addSubject = bundle.getString("subject");
        String add = bundle.getString("add");

        addMessage(addSubject+" "+subject.getSubjectNum()+" "+add,"Confirmation");
        initFormSubjects();
        return redirect;
    }

    public String submitFormUpdateSubject(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        SubjectService subjectService = new SubjectService();
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addSubject = bundle.getString("subject");
        String update = bundle.getString("update");

        try{
            transaction.begin();
            subjectService.updateSubject(subject,em);
            transaction.commit();
        }catch(Exception e){
            ProcessUtils.debug(" I'm in the catch of the UpdateSubject method: "+ e);
        }finally{
            if(transaction.isActive()){
                transaction.rollback();
            }
            em.close();
        }
        addMessage(addSubject+" "+subject.getSubjectNum()+" "+update,"Confirmation");
        initFormSubjects();
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public SubjectService getSubjectService() {
        return subjectService;
    }

    public void setSubjectService(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    public String getMessageErrorSubjectNum() {
        return messageErrorSubjectNum;
    }

    public void setMessageErrorSubjectNum(String messageErrorSubjectNum) {
        this.messageErrorSubjectNum = messageErrorSubjectNum;
    }

    public List<SubjectEntity> getAllSubject() {
        return allSubject;
    }

    public void setAllSubject(List<SubjectEntity> allSubject) {
        this.allSubject = allSubject;
    }

    public List<SubjectEntity> getAllSubjectSelected() {
        return allSubjectSelected;
    }

    public void setAllSubjectSelected(List<SubjectEntity> allSubjectSelected) {
        this.allSubjectSelected = allSubjectSelected;
    }

    public List<SubjectEntity> getAllSubjectsBySite() {
        return allSubjectsBySite;
    }

    public void setAllSubjectsBySite(List<SubjectEntity> allSubjectsBySite) {
        this.allSubjectsBySite = allSubjectsBySite;
    }

    public ConnectionBean getConnectionBean() {
        return connectionBean;
    }

    public void setConnectionBean(ConnectionBean connectionBean) {
        this.connectionBean = connectionBean;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
}
