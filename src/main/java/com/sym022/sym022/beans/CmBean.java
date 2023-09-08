package com.sym022.sym022.beans;

import com.sym022.sym022.entities.CmEntity;
import com.sym022.sym022.enums.Cmdosu;
import com.sym022.sym022.enums.Cmfreq;
import com.sym022.sym022.enums.Cmindic;
import com.sym022.sym022.enums.Cmroute;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.CmService;
import com.sym022.sym022.services.EventService;
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
public class CmBean extends FilterOfTable <CmEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private CmEntity cm = new CmEntity();
    private CmService cmService = new CmService();
    private String messageErrorCmtermFalse = "hidden";
    private String messageErrorCmDate = "hidden";
    private String messageErrorCmendat = "hidden";
    private String messageErrorCmendatBeforcmstdat = "hidden";
    private String messageErrorIndicspIndicOther = "hidden";
    private String messageErrorCmdoseAndCmdosuUnknown = "hidden";
    private String messageErrorCmdosuspOther = "hidden";
    private String messageErrorCmroutespOther = "hidden";
    private String messageErrorCmfreqspOther = "hidden";
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
        initFormCm();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to reset the form to add or update an CM
     */
    public void initFormCm(){
        this.cm.setCmterm("");
        this.cm.setCmtermc("");
        this.cm.setCmstdat(null);
        this.cm.setCmong(false);
        this.cm.setCmendat(null);
        this.cm.setCmindic(Cmindic.NOT_REPORTED);
        this.cm.setCmindicsp("");
        this.cm.setCmdose(0.0);
        this.cm.setCmdosu(Cmdosu.UNKNOWN);
        this.cm.setCmdosusp("");
        this.cm.setCmroute(Cmroute.UNKNOWN);
        this.cm.setCmroutesp("");
        this.cm.setCmfreq(Cmfreq.UNKNOWN);
        this.cm.setCmfreqsp("");
        initErrorMessageFormCm();
    }

    /**
     * Method to reset all Error Messages in the form to add or update an CM
     */
    public void initErrorMessageFormCm(){
        this.messageErrorCmtermFalse = "hidden";
        this.messageErrorCmDate = "hidden";
        this.messageErrorCmendat = "hidden";
        this.messageErrorCmendatBeforcmstdat = "hidden";
        this.messageErrorIndicspIndicOther = "hidden";
        this.messageErrorCmdoseAndCmdosuUnknown = "hidden";
        this.messageErrorCmdosuspOther = "hidden";
        this.messageErrorCmroutespOther = "hidden";
        this.messageErrorCmfreqspOther = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the cmterm empty in front end
     * @return messageErrorCmtermFalse hidden or not and button create/update deactivate or not
     */
    public String testCmterm(){
        String redirect = "null";
        if(cm.getCmterm() == null || Objects.equals(cm.getCmterm(), "")){
            this.messageErrorCmtermFalse = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmtermFalse = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Cmstdat in front end
     * @return messageErrorCmDate hidden or not and button create/update deactivate or not
     */
    public String testDate(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String cmstdat = simpleDateFormat.format(cm.getCmstdat());
        int resultCmstdat = cmstdat.compareTo(String.valueOf(now));
        if(resultCmstdat > 0){
            this.messageErrorCmDate = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmDate = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Cmendat in front end
     * @return messageErrorCmendat hidden or not and button create/update deactivate or not
     */
    public String testDateCmendat(){
        LocalDate now = LocalDate.now();
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String cmendat = simpleDateFormat.format(cm.getCmstdat());
        int resultCmendat = cmendat.compareTo(String.valueOf(now));
        if(resultCmendat > 0){
            this.messageErrorCmendat = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmendat = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the CmendatBeforcmstdat in front end
     * @return messageErrorCmendatBeforcmstdat hidden or not and button create/update deactivate or not
     */
    public String testCmendatBeforcmstdat(){
        String redirect = "null";
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        String cmstdatDate = simpleDateFormat.format(cm.getCmstdat());
        String cmendatDate = simpleDateFormat.format(cm.getCmendat());
        int resultAendatDate = cmendatDate.compareTo(cmstdatDate);
        if(resultAendatDate < 0){
            this.messageErrorCmendatBeforcmstdat = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmendatBeforcmstdat = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the cmindicsp empty in front end
     * @return messageErrorIndicspIndicOther hidden or not and button create/update deactivate or not
     */
    public String testIndicspIndicOther(){
        String redirect = "null";
        if(cm.getCmindic() == Cmindic.OTHER && (cm.getCmindicsp() == null || Objects.equals(cm.getCmindicsp(), ""))){
            this.messageErrorIndicspIndicOther = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorIndicspIndicOther = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Cmdose equal ZERO when Cmdosu equal UNKNOWN in front end
     * @return messageErrorCmdoseAndCmdosuUnknown hidden or not and button create/update deactivate or not
     */
    public String testCmdoseAndCmdosuUnknown(){
        String redirect = "null";
        if(cm.getCmdosu() == Cmdosu.UNKNOWN && (cm.getCmdose() == null || cm.getCmdose() != 0)){
            this.messageErrorCmdoseAndCmdosuUnknown = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmdoseAndCmdosuUnknown = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Cmdosusp empty in front end
     * @return messageErrorCmdosuspOther hidden or not and button create/update deactivate or not
     */
    public String testCmdosuspOther(){
        String redirect = "null";
        if(cm.getCmdosu() == Cmdosu.OTHER && (cm.getCmdosusp() == null || Objects.equals(cm.getCmdosusp(), ""))){
            this.messageErrorCmdosuspOther = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmdosuspOther = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Cmroutesp empty in front end
     * @return messageErrorCmroutespOther hidden or not and button create/update deactivate or not
     */
    public String testCmroutespOther(){
        String redirect = "null";
        if(cm.getCmroute() == Cmroute.OTHER && (cm.getCmroutesp() == null || Objects.equals(cm.getCmroutesp(), ""))){
            this.messageErrorCmroutespOther = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmroutespOther = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Cmfreqsp empty in front end
     * @return messageErrorCmfreqspOther hidden or not and button create/update deactivate or not
     */
    public String testCmfreqspOther(){
        String redirect = "null";
        if(cm.getCmfreq() == Cmfreq.OTHER && (cm.getCmfreqsp() == null || Objects.equals(cm.getCmfreqsp(), ""))){
            this.messageErrorCmfreqspOther = "";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorCmfreqspOther = "hidden";
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
     * Method to add a CM in the DB
     * @return a CM
     */
    public String submitFormAddCm(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        CmService cmService = new CmService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(cm.getCmstdat() != null && cm.getCmendat() == null){
            String AddCmcmstdat = simpleDateFormat.format(cm.getCmstdat());
            int resultAddCmcmstdat = AddCmcmstdat.compareTo(String.valueOf(now));
            if(cm.getCmterm() == null || Objects.equals(cm.getCmterm(), "")){
                initErrorMessageFormCm();
                this.messageErrorCmtermFalse = "";
                redirect = "null";
                return redirect;
            }else if(resultAddCmcmstdat > 0){
                initErrorMessageFormCm();
                this.messageErrorCmDate = "";
                redirect = "null";
                return redirect;
            }else if(cm.getCmindic() == Cmindic.OTHER && (cm.getCmindicsp() == null || Objects.equals(cm.getCmindicsp(), ""))){
                initErrorMessageFormCm();
                this.messageErrorIndicspIndicOther = "";
                redirect = "null";
                return redirect;
            }else if(cm.getCmdosu() == Cmdosu.UNKNOWN && (cm.getCmdose() == null || cm.getCmdose() != 0)){
                initErrorMessageFormCm();
                this.messageErrorCmdoseAndCmdosuUnknown = "";
                redirect = "null";
                return redirect;
            }else if(cm.getCmdosu() == Cmdosu.OTHER && (cm.getCmdosusp() == null || Objects.equals(cm.getCmdosusp(), ""))){
                initErrorMessageFormCm();
                this.messageErrorCmdosuspOther = "";
                redirect = "null";
                return redirect;
            }else if(cm.getCmroute() == Cmroute.OTHER && (cm.getCmroutesp() == null || Objects.equals(cm.getCmroutesp(), ""))){
                initErrorMessageFormCm();
                this.messageErrorCmroutespOther = "";
                redirect = "null";
                return redirect;
            }else{
                try{
                    cm.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    cm.setCmtermc("");

                    if(cm.getCmong()){
                        this.cm.setCmendat(null);
                    }

                    if(cm.getCmindic() != Cmindic.OTHER){
                        this.cm.setCmindicsp("");
                    }

                    if(cm.getCmdosu() != Cmdosu.OTHER){
                        this.cm.setCmdosusp("");
                    }

                    if(cm.getCmroute() != Cmroute.OTHER){
                        this.cm.setCmroutesp("");
                    }

                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    cmService.addCm(cm, em);
                    transaction.commit();

                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addDM method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                        FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String addCm = bundle.getString("cm");
                String add = bundle.getString("add");
                String forThe = bundle.getString("for");
                String addSubject = bundle.getString("subject");

                addMessage(addCm+" "+add+" "+forThe+" "+addSubject+" "+cm.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                initFormCm();
            }

        }else if(cm.getCmstdat() != null && cm.getCmendat() != null){
                String AddCmcmstdat = simpleDateFormat.format(cm.getCmstdat());
                int resultAddCmcmstdat = AddCmcmstdat.compareTo(String.valueOf(now));
                String AddCmcmendat = simpleDateFormat.format(cm.getCmstdat());
                int resultAddCmCmendat = AddCmcmendat.compareTo(String.valueOf(now));
                int resultAddCmendBeforeStDate = AddCmcmendat.compareTo(AddCmcmstdat);
                if(cm.getCmterm() == null || Objects.equals(cm.getCmterm(), "")){
                    initErrorMessageFormCm();
                    this.messageErrorCmtermFalse = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAddCmcmstdat > 0){
                    initErrorMessageFormCm();
                    this.messageErrorCmDate = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAddCmCmendat > 0){
                    initErrorMessageFormCm();
                    this.messageErrorCmendat = "";
                    redirect = "null";
                    return redirect;
                }else if(resultAddCmendBeforeStDate < 0){
                    initErrorMessageFormCm();
                    this.messageErrorCmendatBeforcmstdat = "";
                    redirect = "null";
                    return redirect;
                }else if(cm.getCmindic() == Cmindic.OTHER && (cm.getCmindicsp() == null || Objects.equals(cm.getCmindicsp(), ""))){
                    initErrorMessageFormCm();
                    this.messageErrorIndicspIndicOther = "";
                    redirect = "null";
                    return redirect;
                }else if(cm.getCmdosu() == Cmdosu.UNKNOWN && (cm.getCmdose() == null || cm.getCmdose() != 0)){
                    initErrorMessageFormCm();
                    this.messageErrorCmdoseAndCmdosuUnknown = "";
                    redirect = "null";
                    return redirect;
                }else if(cm.getCmdosu() == Cmdosu.OTHER && (cm.getCmdosusp() == null || Objects.equals(cm.getCmdosusp(), ""))){
                    initErrorMessageFormCm();
                    this.messageErrorCmdosuspOther = "";
                    redirect = "null";
                    return redirect;
                }else if(cm.getCmroute() == Cmroute.OTHER && (cm.getCmroutesp() == null || Objects.equals(cm.getCmroutesp(), ""))){
                    initErrorMessageFormCm();
                    this.messageErrorCmroutespOther = "";
                    redirect = "null";
                    return redirect;
                }else{
                try{
                    cm.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    cm.setCmtermc("");

                    if(cm.getCmindic() != Cmindic.OTHER){
                        this.cm.setCmindicsp("");
                    }

                    if(cm.getCmdosu() != Cmdosu.OTHER){
                        this.cm.setCmdosusp("");
                    }

                    if(cm.getCmroute() != Cmroute.OTHER){
                        this.cm.setCmroutesp("");
                    }

                        transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    cmService.addCm(cm, em);
                    transaction.commit();

                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addDM method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                        FacesContext.getCurrentInstance().getViewRoot().getLocale());
                String addCm = bundle.getString("cm");
                String add = bundle.getString("add");
                String forThe = bundle.getString("for");
                String addSubject = bundle.getString("subject");

                addMessage(addCm+" "+add+" "+forThe+" "+addSubject+" "+cm.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
                initFormCm();
            }
        }
        return redirect;
    }

    /*--- Getters and Setters ---*/

    public CmEntity getCm() {
        return cm;
    }

    public void setCm(CmEntity cm) {
        this.cm = cm;
    }

    public CmService getCmService() {
        return cmService;
    }

    public void setCmService(CmService cmService) {
        this.cmService = cmService;
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

    public String getMessageErrorCmtermFalse() {
        return messageErrorCmtermFalse;
    }

    public void setMessageErrorCmtermFalse(String messageErrorCmtermFalse) {
        this.messageErrorCmtermFalse = messageErrorCmtermFalse;
    }

    public String getMessageErrorCmDate() {
        return messageErrorCmDate;
    }

    public void setMessageErrorCmDate(String messageErrorCmDate) {
        this.messageErrorCmDate = messageErrorCmDate;
    }

    public String getMessageErrorCmendat() {
        return messageErrorCmendat;
    }

    public void setMessageErrorCmendat(String messageErrorCmendat) {
        this.messageErrorCmendat = messageErrorCmendat;
    }

    public String getMessageErrorCmendatBeforcmstdat() {
        return messageErrorCmendatBeforcmstdat;
    }

    public void setMessageErrorCmendatBeforcmstdat(String messageErrorCmendatBeforcmstdat) {
        this.messageErrorCmendatBeforcmstdat = messageErrorCmendatBeforcmstdat;
    }

    public String getMessageErrorIndicspIndicOther() {
        return messageErrorIndicspIndicOther;
    }

    public void setMessageErrorIndicspIndicOther(String messageErrorIndicspIndicOther) {
        this.messageErrorIndicspIndicOther = messageErrorIndicspIndicOther;
    }

    public String getMessageErrorCmdoseAndCmdosuUnknown() {
        return messageErrorCmdoseAndCmdosuUnknown;
    }

    public void setMessageErrorCmdoseAndCmdosuUnknown(String messageErrorCmdoseAndCmdosuUnknown) {
        this.messageErrorCmdoseAndCmdosuUnknown = messageErrorCmdoseAndCmdosuUnknown;
    }

    public String getMessageErrorCmdosuspOther() {
        return messageErrorCmdosuspOther;
    }

    public void setMessageErrorCmdosuspOther(String messageErrorCmdosuspOther) {
        this.messageErrorCmdosuspOther = messageErrorCmdosuspOther;
    }

    public String getMessageErrorCmroutespOther() {
        return messageErrorCmroutespOther;
    }

    public void setMessageErrorCmroutespOther(String messageErrorCmroutespOther) {
        this.messageErrorCmroutespOther = messageErrorCmroutespOther;
    }

    public String getMessageErrorCmfreqspOther() {
        return messageErrorCmfreqspOther;
    }

    public void setMessageErrorCmfreqspOther(String messageErrorCmfreqspOther) {
        this.messageErrorCmfreqspOther = messageErrorCmfreqspOther;
    }

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }
}
