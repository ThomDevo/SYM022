package com.sym022.sym022.beans;

import com.sym022.sym022.entities.TeEntity;
import com.sym022.sym022.enums.*;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.services.TeService;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class TeBean extends FilterOfTable<TeEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private TeEntity te = new TeEntity();
    private TeService teService = new TeService();
    private String messageErrorTeNdFalse = "hidden";
    private String messageErrorVisitDate = "hidden";
    private String messageErrorTeDateMissing = "hidden";
    private String messageErrorOR = "hidden";
    private String buttonSuccess = "false";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*---Method---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFormTe();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateForm(){
        String redirect = "/VIEW/home";
        initFormTe();
        return redirect;
    }

    /**
     * Method to reset the form for Add/Update a VS
     */
    public void initFormTe(){
        Date now = new Date();
        this.te.setTeYn(false);
        this.te.setTeNd("");
        this.te.setTeDate(null);
        this.te.setTargetLesions(null);
        this.te.setNonTargetLesions(null);
        this.te.setNewLesions(false);
        this.te.setOverallResponse(null);
        initErrorMessageFormTE();
    }


    /**
     * Method to reset all error messages
     */
    public void initErrorMessageFormTE(){
        this.messageErrorVisitDate = "hidden";
        this.messageErrorTeNdFalse = "hidden";
        this.messageErrorOR = "hidden";
        this.messageErrorTeDateMissing = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the testInputTeNd in front end
     * @return messageErrorTeNd hidden or not and button create/update deactivate or not
     */
    public String testInputTeNd(){
        initErrorMessageFormTE();
        String redirect = "null";
        if(!te.getTeYn() && (te.getTeNd() == null || Objects.equals(te.getTeNd(), ""))){
            this.messageErrorTeNdFalse = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTeNdFalse = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date is null in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDateNull(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        if(te.getTeDate() == null){
            this.messageErrorTeDateMissing = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTeDateMissing = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDate(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);

        if(te.getTeDate() == null){
            testDateNull();
        }else{
            String teDate = simpleDateFormat.format(te.getTeDate());
            int resultTeDate = teDate.compareTo(String.valueOf(now));
            if(resultTeDate > 0){
                this.messageErrorVisitDate = "";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDate = "hidden";
                this.buttonSuccess = "false";
            }
        }

        return redirect;
    }

    /**
     * Method to test overallResponse in front end
     * @return all error messages are hidden or not and button create/update deactivate or not
     */
    public String testOverallResponse(){
        String redirect = "null";
        if((te.getTargetLesions() == TargetLesionsOverallResponse.PROGRESSIVE_DISEASE || te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE || te.getNewLesions()) && te.getOverallResponse() != TargetLesionsOverallResponse.PROGRESSIVE_DISEASE){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.COMPLETE_RESPONSE){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_ALL_EVALUATED){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_EVALUABLE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_EVALUABLE){
            this.messageErrorOR = "";
            this.buttonSuccess = "true";
            return redirect;
        } else{
            this.messageErrorOR = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
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
     * Method to find a TE based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateTe";
        EntityManager em = EMF.getEM();
        try{
            te = teService.findTeByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to find a TE based on the IdEvent
     * @param idEvent
     */
    public String findEventQuery(int idEvent){
        String redirect = "/VIEW/consultQueryTe";
        EntityManager em = EMF.getEM();
        try{
            te = teService.findTeByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to add a popup
     */
    private void getRessourceBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addTe = bundle.getString("te");
        String add = bundle.getString("add");
        String forThe = bundle.getString("for");
        String addSubject = bundle.getString("subject");

        addMessage(addTe+" "+add+" "+forThe+" "+addSubject+" "+te.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        initFormTe();
    }

    /**
     * Method to add a popup
     */
    private void getRessourceBundleUpdate() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addTe = bundle.getString("te");
        String update = bundle.getString("update");
        String forThe = bundle.getString("for");
        String addSubject = bundle.getString("subject");

        addMessage(addTe+" "+update+" "+forThe+" "+addSubject+" "+te.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        initFormTe();
    }

    /**
     * Method to add a Te in the DB
     * @return a TE
     */
    public String submitFormAddTe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        TeService teService = new TeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if (te.getTargetLesions() == null || te.getNonTargetLesions() == null || te.getOverallResponse() == null){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTeYn() && te.getTeDate() != null){
            String teDate = simpleDateFormat.format(te.getTeDate());
            int resultTeDate = teDate.compareTo(String.valueOf(now));
            if(resultTeDate > 0){
                initErrorMessageFormTE();
                this.messageErrorVisitDate = "";
                redirect = null;
                return redirect;
            }else if((te.getTargetLesions() == TargetLesionsOverallResponse.PROGRESSIVE_DISEASE || te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE || te.getNewLesions()) && te.getOverallResponse() != TargetLesionsOverallResponse.PROGRESSIVE_DISEASE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.COMPLETE_RESPONSE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            } else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_ALL_EVALUATED){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_EVALUABLE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_EVALUABLE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else{
                try{
                    te.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);

                    if(te.getTeYn()){
                        this.te.setTeNd("");
                    }

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    teService.addTe(te,em);
                    transaction.commit();

                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addTE method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                getRessourceBundle();
            }
        }else{if(!te.getTeYn() && Objects.equals(te.getTeNd(),"")){
            initErrorMessageFormTE();
            this.messageErrorTeNdFalse = "";
            redirect = "null";
            return redirect;
        }else if(te.getTeYn() && te.getTeDate() == null){
            initErrorMessageFormTE();
            this.messageErrorTeDateMissing = "";
            redirect = "null";
            return redirect;
        }else if((te.getTargetLesions() == TargetLesionsOverallResponse.PROGRESSIVE_DISEASE || te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE || te.getNewLesions()) && te.getOverallResponse() != TargetLesionsOverallResponse.PROGRESSIVE_DISEASE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.COMPLETE_RESPONSE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_ALL_EVALUATED){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_EVALUABLE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_EVALUABLE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else{
            try{
                te.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);

                if(!te.getTeYn()){;
                    this.te.setTeDate(null);
                    this.te.setTargetLesions(TargetLesionsOverallResponse.NOT_ALL_EVALUATED);
                    this.te.setNonTargetLesions(NonTargetLesions.NOT_ALL_EVALUATED);
                    this.te.setNewLesions(false);
                    this.te.setOverallResponse(TargetLesionsOverallResponse.NOT_ALL_EVALUATED);
                }



                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                teService.addTe(te,em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addTE method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            getRessourceBundle();
        }

        }
        return redirect;
    }

    /**
     * Method to add a Te in the DB
     * @return a TE
     */
    public String submitFormUpdateTe(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        TeService teService = new TeService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if (te.getTargetLesions() == null || te.getNonTargetLesions() == null || te.getOverallResponse() == null){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTeYn() && te.getTeDate() != null){
            String teDate = simpleDateFormat.format(te.getTeDate());
            int resultTeDate = teDate.compareTo(String.valueOf(now));
            if(resultTeDate > 0){
                initErrorMessageFormTE();
                this.messageErrorVisitDate = "";
                redirect = null;
                return redirect;
            }else if((te.getTargetLesions() == TargetLesionsOverallResponse.PROGRESSIVE_DISEASE || te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE || te.getNewLesions()) && te.getOverallResponse() != TargetLesionsOverallResponse.PROGRESSIVE_DISEASE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.COMPLETE_RESPONSE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            } else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_ALL_EVALUATED){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_EVALUABLE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_EVALUABLE){
                initErrorMessageFormTE();
                this.messageErrorOR = "";
                redirect = null;
                return redirect;
            }else{
                try{
                    te.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);

                    if(te.getTeYn()){
                        this.te.setTeNd("");
                    }

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    teService.updateTe(te,em);
                    transaction.commit();

                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addTE method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                getRessourceBundleUpdate();
            }
        }else{if(!te.getTeYn() && Objects.equals(te.getTeNd(),"")){
            initErrorMessageFormTE();
            this.messageErrorTeNdFalse = "";
            redirect = "null";
            return redirect;
        }else if(te.getTeYn() && te.getTeDate() == null){
            initErrorMessageFormTE();
            this.messageErrorTeDateMissing = "";
            redirect = "null";
            return redirect;
        }else if((te.getTargetLesions() == TargetLesionsOverallResponse.PROGRESSIVE_DISEASE || te.getNonTargetLesions() == NonTargetLesions.PROGRESSIVE_DISEASE || te.getNewLesions()) && te.getOverallResponse() != TargetLesionsOverallResponse.PROGRESSIVE_DISEASE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() == NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.COMPLETE_RESPONSE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.COMPLETE_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.COMPLETE_RESPONSE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.PARTIAL_RESPONSE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.PARTIAL_RESPONSE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.STABLE_DISEASE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.STABLE_DISEASE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_ALL_EVALUATED && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_ALL_EVALUATED){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else if(te.getTargetLesions() == TargetLesionsOverallResponse.NOT_EVALUABLE && te.getNonTargetLesions() != NonTargetLesions.PROGRESSIVE_DISEASE && !te.getNewLesions() && te.getOverallResponse() != TargetLesionsOverallResponse.NOT_EVALUABLE){
            initErrorMessageFormTE();
            this.messageErrorOR = "";
            redirect = null;
            return redirect;
        }else{
            try{
                te.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);

                if(!te.getTeYn()){;
                    this.te.setTeDate(null);
                    this.te.setTargetLesions(TargetLesionsOverallResponse.NOT_ALL_EVALUATED);
                    this.te.setNonTargetLesions(NonTargetLesions.NOT_ALL_EVALUATED);
                    this.te.setNewLesions(false);
                    this.te.setOverallResponse(TargetLesionsOverallResponse.NOT_ALL_EVALUATED);
                }



                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                teService.updateTe(te,em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addTE method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            getRessourceBundleUpdate();
        }

        }
        return redirect;
    }


    /*--- Getters and Setters ---*/

    public TeEntity getTe() {
        return te;
    }

    public void setTe(TeEntity te) {
        this.te = te;
    }

    public TeService getTeService() {
        return teService;
    }

    public void setTeService(TeService teService) {
        this.teService = teService;
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

    public String getMessageErrorTeNdFalse() {
        return messageErrorTeNdFalse;
    }

    public void setMessageErrorTeNdFalse(String messageErrorTeNdFalse) {
        this.messageErrorTeNdFalse = messageErrorTeNdFalse;
    }

    public String getMessageErrorVisitDate() {
        return messageErrorVisitDate;
    }

    public void setMessageErrorVisitDate(String messageErrorVisitDate) {
        this.messageErrorVisitDate = messageErrorVisitDate;
    }

    public String getMessageErrorTeDateMissing() {
        return messageErrorTeDateMissing;
    }

    public void setMessageErrorTeDateMissing(String messageErrorTeDateMissing) {
        this.messageErrorTeDateMissing = messageErrorTeDateMissing;
    }

    public String getMessageErrorOR() {
        return messageErrorOR;
    }

    public void setMessageErrorOR(String messageErrorOR) {
        this.messageErrorOR = messageErrorOR;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }
}
