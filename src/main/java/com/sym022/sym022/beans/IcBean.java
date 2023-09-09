package com.sym022.sym022.beans;

import com.sym022.sym022.entities.IcEntity;
import com.sym022.sym022.entities.UserSiteEntity;
import com.sym022.sym022.enums.IeNotMet;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.services.IcService;
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
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Named
@ManagedBean
@SessionScoped
public class IcBean extends FilterOfTable<IcEntity> implements Serializable {

    /*--- Variable declaration ---*/
    private IcEntity ic = new IcEntity();
    private IcService icService = new IcService();
    private String messageErrorIcDate = "hidden";
    private String messageErrorIeNotMeet = "hidden";
    private String messageErrorIeNotMeetNa = "hidden";
    private String messageErrorVsProt = "hidden";
    private String buttonSuccess = "false";
    private String messageErrorVisitDateMissing = "false";
    private List<IcEntity> icAll;
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
        initFormIc();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateForm(){
        String redirect = "/VIEW/home";
        initFormIc();
        return redirect;
    }

    /**
     * Method to reset the form to add or update an IC
     */
    public void initFormIc(){
        Date now = new Date();
        this.ic.setIcDate(now);
        this.ic.setProtVers("");
        this.ic.setEligYn(true);
        this.ic.setIeNotMet(null);
        initErrorMessageFormIc();
    }

    /**
     * Method to reset all Error Messages in the form to add or update an IC
     */
    public void initErrorMessageFormIc(){
        this.messageErrorIcDate = "hidden";
        this.messageErrorIeNotMeet = "hidden";
        this.messageErrorIeNotMeetNa = "hidden";
        this.messageErrorVsProt = "hidden";
        this.messageErrorVisitDateMissing = "hidden";
        this.buttonSuccess = "false";
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
     * Method to test the date is null in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDateNull(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        if(ic.getIcDate() == null){
            this.messageErrorVisitDateMissing = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVisitDateMissing = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the date in front end
     * @return messageErrorIcDate hidden or not and button create/update deactivate or not
     */
    public String testDate(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String icDate = simpleDateFormat.format(ic.getIcDate());
        int resultIsDate = icDate.compareTo(String.valueOf(now));
        if(resultIsDate > 0){
            this.messageErrorIcDate = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorIcDate = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the ProtVers in front end
     * @return messageErrorVsProt hidden or not and button create/update deactivate or not
     */
    public String testVsProtoEmpty(){
        String redirect = "null";
        if(ic.getProtVers() == null || Objects.equals(ic.getProtVers(), "")){
            this.messageErrorVsProt = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVsProt = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the ProtVers in front end
     * @return messageErrorIeNotMeetNa hidden or not and button create/update deactivate or not
     */
    public String testIeNotMeetNa(){
        String redirect = "null";
        if(!ic.getEligYn() && ic.getIeNotMet() == IeNotMet.NA){
            this.messageErrorIeNotMeetNa = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorIeNotMeetNa = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to find a Dov based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateIc";
        EntityManager em = EMF.getEM();
        try{
            ic = icService.findIcByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to create an IC and an auditTrail in the DB
     * @return an IC and a AuditTrail
     */
    public String submitFormAddIc(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        IcService icService = new IcService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String icDate = simpleDateFormat.format(ic.getIcDate());
        int resultIcDate = icDate.compareTo(String.valueOf(now));
        //ProcessUtils.debug(""+ resultIcDate);

        if(resultIcDate > 0){
            initErrorMessageFormIc();
            this.messageErrorIcDate = "";
            redirect = "null";
            return redirect;
        }else if(Objects.equals(ic.getProtVers(), "")){
            initErrorMessageFormIc();
            this.messageErrorVsProt = "";
            redirect = "null";
            return redirect;
        }else if(!ic.getEligYn() && ic.getIeNotMet() == IeNotMet.NA){
            initErrorMessageFormIc();
            this.messageErrorIeNotMeetNa = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                ic.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);

                if(ic.getEligYn()){
                    ic.setIeNotMet(IeNotMet.NA);
                }

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                icService.addIc(ic, em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addIC method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addIc = bundle.getString("ic");
            String add = bundle.getString("add");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addIc+" "+add+" "+forThe+" "+addSubject+" "+ic.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            initFormIc();
        }
        return redirect;
    }

    /**
     * Method to create an IC and an auditTrail in the DB
     * @return an IC and a AuditTrail
     */
    public String submitFormUpdateIc(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        IcService icService = new IcService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String icDate = simpleDateFormat.format(ic.getIcDate());
        int resultIcDate = icDate.compareTo(String.valueOf(now));
        //ProcessUtils.debug(""+ resultIcDate);

        if(resultIcDate > 0){
            initErrorMessageFormIc();
            this.messageErrorIcDate = "";
            redirect = "null";
            return redirect;
        }else if(Objects.equals(ic.getProtVers(), "")){
            initErrorMessageFormIc();
            this.messageErrorVsProt = "";
            redirect = "null";
            return redirect;
        }else if(!ic.getEligYn() && ic.getIeNotMet() == IeNotMet.NA){
            initErrorMessageFormIc();
            this.messageErrorIeNotMeetNa = "";
            redirect = "null";
            return redirect;
        }else{
            try{
                ic.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);
                eventBean.getEvent().setMonitored(false);

                if(ic.getEligYn()){
                    ic.setIeNotMet(IeNotMet.NA);
                }

                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                icService.updateIc(ic, em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addIC method: "+ e);

            }finally {
                if(transaction.isActive()){
                    transaction.rollback();
                }
                em.close();
            }
            ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                    FacesContext.getCurrentInstance().getViewRoot().getLocale());
            String addIc = bundle.getString("ic");
            String update = bundle.getString("update");
            String forThe = bundle.getString("for");
            String addSubject = bundle.getString("subject");

            addMessage(addIc+" "+update+" "+forThe+" "+addSubject+" "+ic.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
            initFormIc();
        }
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public IcEntity getIc() {
        return ic;
    }

    public void setIc(IcEntity ic) {
        this.ic = ic;
    }

    public IcService getIcService() {
        return icService;
    }

    public void setIcService(IcService icService) {
        this.icService = icService;
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

    public String getMessageErrorIcDate() {
        return messageErrorIcDate;
    }

    public void setMessageErrorIcDate(String messageErrorIcDate) {
        this.messageErrorIcDate = messageErrorIcDate;
    }

    public String getMessageErrorIeNotMeet() {
        return messageErrorIeNotMeet;
    }

    public void setMessageErrorIeNotMeet(String messageErrorIeNotMeet) {
        this.messageErrorIeNotMeet = messageErrorIeNotMeet;
    }

    public String getMessageErrorIeNotMeetNa() {
        return messageErrorIeNotMeetNa;
    }

    public void setMessageErrorIeNotMeetNa(String messageErrorIeNotMeetNa) {
        this.messageErrorIeNotMeetNa = messageErrorIeNotMeetNa;
    }

    public String getMessageErrorVsProt() {
        return messageErrorVsProt;
    }

    public void setMessageErrorVsProt(String messageErrorVsProt) {
        this.messageErrorVsProt = messageErrorVsProt;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public List<IcEntity> getIcAll() {
        return icAll;
    }

    public void setIcAll(List<IcEntity> icAll) {
        this.icAll = icAll;
    }

    public String getMessageErrorVisitDateMissing() {
        return messageErrorVisitDateMissing;
    }

    public void setMessageErrorVisitDateMissing(String messageErrorVisitDateMissing) {
        this.messageErrorVisitDateMissing = messageErrorVisitDateMissing;
    }
}
