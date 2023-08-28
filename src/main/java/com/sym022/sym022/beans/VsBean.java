package com.sym022.sym022.beans;

import com.sym022.sym022.entities.VsEntity;
import com.sym022.sym022.enums.HeightU;
import com.sym022.sym022.enums.TempRoute;
import com.sym022.sym022.enums.TempU;
import com.sym022.sym022.enums.WeightU;
import com.sym022.sym022.services.AuditTrailService;
import com.sym022.sym022.services.EventService;
import com.sym022.sym022.services.VsService;
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
public class VsBean extends FilterOfTable<VsEntity> implements Serializable {

    /*--- Variable declaration ---*/

    private VsEntity vs = new VsEntity();
    private VsService vsService = new VsService();
    private String messageErrorVisitNd = "hidden";
    private String messageErrorVisitNdFalse = "hidden";
    private String messageErrorVisitDate = "hidden";
    private String messageErrorVisitDateMissing = "hidden";
    private String messageErrorHeightCm = "hidden";
    private String messageErrorHeightFeet = "hidden";
    private String messageErrorWeightKg = "hidden";
    private String messageErrorWeightPounds = "hidden";
    private String messageErrorSbpGtDbp = "hidden";
    private String messageErrorDbp = "hidden";
    private String messageErrorSbp = "hidden";
    private String messageErrorHr = "hidden";
    private String messageErrorRr = "hidden";
    private String messageErrorTempC = "hidden";
    private String messageErrorTempF = "hidden";
    private String messageErrorOxy = "hidden";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*--- Method ---*/

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
     * Method to reset the form for Add/Update a VS
     */
    public void initFromVs(){
        Date now = new Date();
        this.vs.setVsYn(false);
        this.vs.setVsNd("");
        this.vs.setVsDate(now);
        this.vs.setHeightNd(false);
        this.vs.setHeight(0.0);
        this.vs.setHeightU(HeightU.CM);
        this.vs.setWeightNd(false);
        this.vs.setWeight(0.0);
        this.vs.setWeightU(WeightU.KG);
        this.vs.setBpNd(false);
        this.vs.setSbp(0);
        this.vs.setDbp(0);
        this.vs.setHrNd(false);
        this.vs.setHr(0);
        this.vs.setRrNd(false);
        this.vs.setRr(0);
        this.vs.setTempNd(false);
        this.vs.setTemp(0.0);
        this.vs.setTempU(TempU.C);
        this.vs.setTempRoute(TempRoute.UNKNOWN);
        this.vs.setOxysatNd(false);
        this.vs.setOxysat(0);
    }

    public void initErrorMessageFormVS(){
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitNd = "hidden";
        this.messageErrorVisitNdFalse = "hidden";
        this.messageErrorVisitDateMissing = "hidden";
        this.messageErrorHeightCm = "hidden";
        this.messageErrorHeightFeet = "hidden";
        this.messageErrorWeightKg = "hidden";
        this.messageErrorWeightPounds = "hidden";
        this.messageErrorSbpGtDbp = "hidden";
        this.messageErrorDbp = "hidden";
        this.messageErrorSbp = "hidden";
        this.messageErrorHr = "hidden";
        this.messageErrorRr = "hidden";
        this.messageErrorTempC = "hidden";
        this.messageErrorTempF = "hidden";
        this.messageErrorOxy = "hidden";
    }

    /**
     * Method to add a VS in the DB
     * @return a VS
     */
    public String submitFormAddVs(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        VsService vsService = new VsService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(vs.getVsYn() && vs.getVsDate() != null){
            String vsDate = simpleDateFormat.format(vs.getVsDate());
            int resultVsDate = vsDate.compareTo(String.valueOf(now));
            if(resultVsDate > 0){
                initErrorMessageFormVS();
                this.messageErrorVisitDate = "";
                redirect = "null";
            }else if(!vs.getVsYn() && Objects.equals(vs.getVsNd(), "")){
                initErrorMessageFormVS();
                this.messageErrorVisitNd = "";
                redirect = "null";
            }else if(vs.getHeightNd() && vs.getHeight() < 40.0 && vs.getHeightU() == HeightU.CM || vs.getHeightNd() && vs.getHeight() > 280.0 && vs.getHeightU() == HeightU.CM){
                initErrorMessageFormVS();
                this.messageErrorHeightCm = "";
                redirect = "null";
            }else if(vs.getHeightNd() && vs.getHeight() < 15.7 && vs.getHeightU() == HeightU.INCHES || vs.getHeightNd() && vs.getHeight() > 110.2 && vs.getHeightU() == HeightU.INCHES){
                initErrorMessageFormVS();
                this.messageErrorHeightFeet = "";
                redirect = "null";
            }else if(vs.getWeightNd() && vs.getWeight() < 20.0 && vs.getWeightU() == WeightU.KG || vs.getWeightNd() && vs.getWeight() > 650.0 && vs.getWeightU() == WeightU.KG){
                initErrorMessageFormVS();
                this.messageErrorWeightKg = "";
                redirect = "null";
            }else if(vs.getWeightNd() && vs.getWeight() < 44.0 && vs.getWeightU() == WeightU.POUNDS || vs.getWeightNd() && vs.getWeight() > 1435.0 && vs.getWeightU() == WeightU.POUNDS){
                initErrorMessageFormVS();
                this.messageErrorWeightPounds = "";
                redirect = "null";
            }else if(vs.getBpNd() && vs.getDbp()>vs.getSbp()){
                initErrorMessageFormVS();
                this.messageErrorSbpGtDbp = "";
                redirect = "null";
            }else if(vs.getBpNd() && vs.getDbp()<10){
                initErrorMessageFormVS();
                this.messageErrorDbp = "";
                redirect = "null";
            }else if(vs.getBpNd() && vs.getSbp()>250){
                initErrorMessageFormVS();
                this.messageErrorSbp = "";
                redirect = "null";
            }else if(vs.getHrNd() && vs.getHr()<10 || vs.getHrNd() && vs.getHr()>240){
                initErrorMessageFormVS();
                this.messageErrorHr = "";
                redirect = "null";
            }else if(vs.getRrNd() && vs.getRr()<5){
                initErrorMessageFormVS();
                this.messageErrorRr = "";
                redirect = "null";
            }else if(vs.getTempNd() && vs.getTemp() < 30 && vs.getTempU() == TempU.C || vs.getTempNd() && vs.getTemp() > 45 && vs.getTempU() == TempU.C){
                initErrorMessageFormVS();
                this.messageErrorTempC = "";
                redirect = "null";
            }else if(vs.getTempNd() && vs.getTemp() < 86 && vs.getTempU() == TempU.F || vs.getTempNd() && vs.getTemp() > 113 && vs.getTempU() == TempU.F){
                initErrorMessageFormVS();
                this.messageErrorTempF = "";
                redirect = "null";
            }else if(vs.getOxysatNd() && vs.getOxysat()<50){
                initErrorMessageFormVS();
                this.messageErrorOxy = "";
                redirect = "null";
            }else{
                try{
                    vs.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    if(!vs.getHeightNd()){
                        vs.setHeight(null);
                        vs.setHeightU(HeightU.CM);
                    }
                    if(!vs.getWeightNd()){
                        vs.setWeight(null);
                        vs.setWeightU(WeightU.KG);
                    }
                    if(!vs.getBpNd()){
                        vs.setSbp(0);
                        vs.setDbp(0);
                    }
                    if(!vs.getHrNd()){
                        vs.setHr(0);
                    }
                    if(!vs.getRrNd()){
                        vs.setRr(0);
                    }
                    if(!vs.getTempNd()){
                        vs.setTemp(null);
                        vs.setTempU(TempU.C);
                        vs.setTempRoute(TempRoute.UNKNOWN);
                    }
                    if(!vs.getOxysatNd()){
                        vs.setOxysat(0);
                    }
                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    vsService.addVs(vs,em);
                    transaction.commit();

                }catch(Exception e){
                    ProcessUtils.debug(" I'm in the catch of the addVS method: "+ e);

                }finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    em.close();
                }
                getRessourceBundle();

            }
        }else{
        if(!vs.getVsYn() && Objects.equals(vs.getVsNd(), "")){
            initErrorMessageFormVS();
            this.messageErrorVisitNd = "";
            redirect = "null";
        }else if(vs.getVsYn() && vs.getVsDate() == null){
            initErrorMessageFormVS();
            this.messageErrorVisitDateMissing = "";
            redirect = "null";
        }else{
            try{
                vs.setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                eventBean.getEvent().setCompleted(true);

                if(!vs.getVsYn()){;
                    this.vs.setVsDate(null);
                    this.vs.setHeightNd(false);
                    this.vs.setHeight(0.0);
                    this.vs.setHeightU(HeightU.CM);
                    this.vs.setWeightNd(false);
                    this.vs.setWeight(0.0);
                    this.vs.setWeightU(WeightU.KG);
                    this.vs.setBpNd(false);
                    this.vs.setSbp(0);
                    this.vs.setDbp(0);
                    this.vs.setHrNd(false);
                    this.vs.setHr(0);
                    this.vs.setRrNd(false);
                    this.vs.setRr(0);
                    this.vs.setTempNd(false);
                    this.vs.setTemp(0.0);
                    this.vs.setTempU(TempU.C);
                    this.vs.setTempRoute(TempRoute.UNKNOWN);
                    this.vs.setOxysatNd(false);
                    this.vs.setOxysat(0);
                }



                transaction.begin();
                eventService.updateEvent(eventBean.getEvent(),em);
                auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                vsService.addVs(vs,em);
                transaction.commit();

            }catch(Exception e){
                ProcessUtils.debug(" I'm in the catch of the addVS method: "+ e);

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
     * Method to add a popup
     */
    private void getRessourceBundle() {
        ResourceBundle bundle = ResourceBundle.getBundle("language.messages",
                FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String addVs = bundle.getString("vs");
        String add = bundle.getString("add");
        String forThe = bundle.getString("for");
        String addSubject = bundle.getString("subject");

        addMessage(addVs+" "+add+" "+forThe+" "+addSubject+" "+vs.getEventByIdEvent().getSubjectByIdSubject().getSubjectNum(),"Confirmation");
        initFromVs();
    }


    /*--- Getters and Setters ---*/

    public VsEntity getVs() {
        return vs;
    }

    public void setVs(VsEntity vs) {
        this.vs = vs;
    }

    public VsService getVsService() {
        return vsService;
    }

    public void setVsService(VsService vsService) {
        this.vsService = vsService;
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

    public String getMessageErrorVisitNd() {
        return messageErrorVisitNd;
    }

    public void setMessageErrorVisitNd(String messageErrorVisitNd) {
        this.messageErrorVisitNd = messageErrorVisitNd;
    }

    public String getMessageErrorVisitNdFalse() {
        return messageErrorVisitNdFalse;
    }

    public void setMessageErrorVisitNdFalse(String messageErrorVisitNdFalse) {
        this.messageErrorVisitNdFalse = messageErrorVisitNdFalse;
    }

    public String getMessageErrorVisitDate() {
        return messageErrorVisitDate;
    }

    public void setMessageErrorVisitDate(String messageErrorVisitDate) {
        this.messageErrorVisitDate = messageErrorVisitDate;
    }

    public String getMessageErrorVisitDateMissing() {
        return messageErrorVisitDateMissing;
    }

    public void setMessageErrorVisitDateMissing(String messageErrorVisitDateMissing) {
        this.messageErrorVisitDateMissing = messageErrorVisitDateMissing;
    }

    public String getMessageErrorHeightCm() {
        return messageErrorHeightCm;
    }

    public void setMessageErrorHeightCm(String messageErrorHeightCm) {
        this.messageErrorHeightCm = messageErrorHeightCm;
    }

    public String getMessageErrorHeightFeet() {
        return messageErrorHeightFeet;
    }

    public void setMessageErrorHeightFeet(String messageErrorHeightFeet) {
        this.messageErrorHeightFeet = messageErrorHeightFeet;
    }

    public String getMessageErrorWeightKg() {
        return messageErrorWeightKg;
    }

    public void setMessageErrorWeightKg(String messageErrorWeightKg) {
        this.messageErrorWeightKg = messageErrorWeightKg;
    }

    public String getMessageErrorWeightPounds() {
        return messageErrorWeightPounds;
    }

    public void setMessageErrorWeightPounds(String messageErrorWeightPounds) {
        this.messageErrorWeightPounds = messageErrorWeightPounds;
    }

    public String getMessageErrorSbpGtDbp() {
        return messageErrorSbpGtDbp;
    }

    public void setMessageErrorSbpGtDbp(String messageErrorSbpGtDbp) {
        this.messageErrorSbpGtDbp = messageErrorSbpGtDbp;
    }

    public String getMessageErrorDbp() {
        return messageErrorDbp;
    }

    public void setMessageErrorDbp(String messageErrorDbp) {
        this.messageErrorDbp = messageErrorDbp;
    }

    public String getMessageErrorSbp() {
        return messageErrorSbp;
    }

    public void setMessageErrorSbp(String messageErrorSbp) {
        this.messageErrorSbp = messageErrorSbp;
    }

    public String getMessageErrorHr() {
        return messageErrorHr;
    }

    public void setMessageErrorHr(String messageErrorHr) {
        this.messageErrorHr = messageErrorHr;
    }

    public String getMessageErrorRr() {
        return messageErrorRr;
    }

    public void setMessageErrorRr(String messageErrorRr) {
        this.messageErrorRr = messageErrorRr;
    }

    public String getMessageErrorTempC() {
        return messageErrorTempC;
    }

    public void setMessageErrorTempC(String messageErrorTempC) {
        this.messageErrorTempC = messageErrorTempC;
    }

    public String getMessageErrorTempF() {
        return messageErrorTempF;
    }

    public void setMessageErrorTempF(String messageErrorTempF) {
        this.messageErrorTempF = messageErrorTempF;
    }

    public String getMessageErrorOxy() {
        return messageErrorOxy;
    }

    public void setMessageErrorOxy(String messageErrorOxy) {
        this.messageErrorOxy = messageErrorOxy;
    }
}
