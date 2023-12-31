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
    private String messageErrorVsDateMissing = "hidden";
    private String messageErrorHeightCm = "hidden";
    private String messageErrorHeightNull = "hidden";
    private String messageErrorHeightUnitNull = "hidden";
    private String messageErrorHeightInches = "hidden";
    private String messageErrorWeightNull = "hidden";
    private String messageErrorWeightUnitNull = "hidden";
    private String messageErrorWeightKg = "hidden";
    private String messageErrorWeightPounds = "hidden";
    private String messageErrorSbpGtDbp = "hidden";
    private String messageErrorDbp = "hidden";
    private String messageErrorSbp = "hidden";
    private String messageErrorHr = "hidden";
    private String messageErrorRr = "hidden";
    private String messageErrorTempNull = "hidden";
    private String messageErrorTempUnitNull = "hidden";
    private String messageErrorTempC = "hidden";
    private String messageErrorTempF = "hidden";
    private String messageErrorTempRoute = "hidden";
    private String messageErrorOxy = "hidden";
    private String messageErrorPerf = "hidden";
    private String buttonSuccess = "false";
    @Inject
    private ConnectionBean connectionBean;
    @Inject
    private AuditTrailBean auditTrailBean;
    @Inject
    private EventBean eventBean;

    /*--- Method ---*/

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelForm(){
        String redirect = "/VIEW/home";
        initFromVs();
        eventBean.deleteEvent();
        return redirect;
    }

    /**
     * Method to return on the homepage
     * @return homepage
     */
    public String cancelUpdateForm(){
        String redirect = "/VIEW/home";
        initFromVs();
        return redirect;
    }

    /**
     * Method to reset the form for Add/Update a VS
     */
    public void initFromVs(){
        Date now = new Date();
        this.vs.setVsYn(false);
        this.vs.setVsNd("");
        this.vs.setVsDate(null);
        this.vs.setHeightNd(false);
        this.vs.setHeight(null);
        this.vs.setHeightU(null);
        this.vs.setWeightNd(false);
        this.vs.setWeight(null);
        this.vs.setWeightU(null);
        this.vs.setBpNd(false);
        this.vs.setSbp(0);
        this.vs.setDbp(0);
        this.vs.setHrNd(false);
        this.vs.setHr(0);
        this.vs.setRrNd(false);
        this.vs.setRr(0);
        this.vs.setTempNd(false);
        this.vs.setTemp(null);
        this.vs.setTempU(TempU.C);
        this.vs.setTempRoute(null);
        this.vs.setOxysatNd(false);
        this.vs.setOxysat(0);
        initErrorMessageFormVS();
    }

    /**
     * Method to reset all error messages
     */
    public void initErrorMessageFormVS(){
        this.messageErrorVisitDate = "hidden";
        this.messageErrorVisitNd = "hidden";
        this.messageErrorVisitNdFalse = "hidden";
        this.messageErrorVsDateMissing = "hidden";
        this.messageErrorHeightCm = "hidden";
        this.messageErrorHeightNull = "hidden";
        this.messageErrorHeightUnitNull = "hidden";
        this.messageErrorHeightInches = "hidden";
        this.messageErrorWeightNull = "hidden";
        this.messageErrorWeightUnitNull = "hidden";
        this.messageErrorWeightKg = "hidden";
        this.messageErrorWeightPounds = "hidden";
        this.messageErrorSbpGtDbp = "hidden";
        this.messageErrorDbp = "hidden";
        this.messageErrorSbp = "hidden";
        this.messageErrorHr = "hidden";
        this.messageErrorTempNull = "hidden";
        this.messageErrorTempUnitNull = "hidden";
        this.messageErrorRr = "hidden";
        this.messageErrorTempC = "hidden";
        this.messageErrorTempF = "hidden";
        this.messageErrorTempRoute = "hidden";
        this.messageErrorOxy = "hidden";
        this.messageErrorPerf = "hidden";
        this.buttonSuccess = "false";
    }

    /**
     * Method to test the date in front end
     * @return messageErrorVisitDate hidden or not and button create/update deactivate or not
     */
    public String testDateNull(){

        String redirect = "null";
        if(vs.getVsDate() == null){
            this.messageErrorVsDateMissing = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVsDateMissing = "hidden";
            this.messageErrorPerf = "hidden";
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
        if(vs.getVsDate() == null){
            testDateNull();
        }else{
            String vsDate = simpleDateFormat.format(vs.getVsDate());
            int resultVsDate = vsDate.compareTo(String.valueOf(now));
            if(resultVsDate > 0){
                this.messageErrorVisitDate = "";
                this.messageErrorPerf = "hidden";
                this.buttonSuccess = "true";
            }else{
                this.messageErrorVisitDate = "hidden";
                this.messageErrorPerf = "hidden";
                this.buttonSuccess = "false";
            }
        }

        return redirect;
    }

    /**
     * Method to test the InputVsNd in front end
     * @return messageErrorVisitNd hidden or not and button create/update deactivate or not
     */
    public String testInputVsNd(){
        String redirect = "null";
        if(!vs.getVsYn() && (vs.getVsNd() == null || Objects.equals(vs.getVsNd(), ""))){
            this.messageErrorVisitNd = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorVisitNd = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputHeight in front end
     * @return messageErrorHeightNull hidden or not and button create/update deactivate or not
     */
    public String testInputHeight(){
        String redirect = "null";
        if(vs.getHeightNd() && (vs.getHeight() == null || vs.getHeight() == 0)){
            this.messageErrorHeightNull = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorHeightNull = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputHeightUnit in front end
     * @return messageErrorHeightUnitNull hidden or not and button create/update deactivate or not
     */
    public String testInputHeightUnit(){
        String redirect = "null";
        if(vs.getHeightNd() && vs.getHeightU() == null){
            this.messageErrorHeightUnitNull = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorHeightUnitNull = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Heights and Cm in front end
     * @return messageErrorHeightCm hidden or not and button create/update deactivate or not
     */
    public String testRangeHeightCm(){
        String redirect = "null";
        if(vs.getHeightNd() && (vs.getHeight() == null || vs.getHeight() == 0)){
            testInputHeight();
        }else if(vs.getHeightNd() && vs.getHeightU() == null){
            testInputHeightUnit();
        }else if(vs.getHeight() < 40.0 && vs.getHeightU() == HeightU.CM || vs.getHeight() > 280.0 && vs.getHeightU() == HeightU.CM){
            this.messageErrorHeightCm = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";

        }else{
            this.messageErrorHeightCm = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Heights and Inches in front end
     * @return messageErrorHeightInches hidden or not and button create/update deactivate or not
     */
    public String testRangeHeightInches(){
        String redirect = "null";
        if(vs.getHeightNd() && (vs.getHeight() == null || vs.getHeight() == 0)){
            testInputHeight();
        }else if(vs.getHeightNd() && vs.getHeightU() == null){
            testInputHeightUnit();
        }else if(vs.getHeight() < 15.7 && vs.getHeightU() == HeightU.INCHES || vs.getHeight() > 110.2 && vs.getHeightU() == HeightU.INCHES){
            this.messageErrorHeightInches = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorHeightInches = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputWeight in front end
     * @return messageErrorHeightNull hidden or not and button create/update deactivate or not
     */
    public String testInputWeight(){
        String redirect = "null";
        if(vs.getWeightNd() && (vs.getWeight() == null || vs.getWeight() == 0)){
            this.messageErrorWeightNull = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorWeightNull = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputWeightUnit in front end
     * @return messageErrorWeightUnitNull hidden or not and button create/update deactivate or not
     */
    public String testInputWeightUnit(){
        String redirect = "null";
        if(vs.getWeightNd() && vs.getWeightU() == null){
            this.messageErrorWeightUnitNull = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorWeightUnitNull = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Weight and KG in front end
     * @return messageErrorWeightKg hidden or not and button create/update deactivate or not
     */
    public String testRangeWeightKg(){
        String redirect = "null";
        if(vs.getWeightNd() && (vs.getWeight() == null || vs.getWeight() == 0)){
            testInputWeight();
        }else if(vs.getWeightNd() && vs.getWeightU() == null){
            testInputWeightUnit();
        }else if(vs.getWeight() < 20.0 && vs.getWeightU() == WeightU.KG || vs.getWeight() > 650.0 && vs.getWeightU() == WeightU.KG){
            this.messageErrorWeightKg = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorWeightKg = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Weight and Pounds in front end
     * @return messageErrorWeightPounds hidden or not and button create/update deactivate or not
     */
    public String testRangeWeightPounds(){
        String redirect = "null";
        if(vs.getWeightNd() && (vs.getWeight() == null || vs.getWeight() == 0)){
            testInputWeight();
        }else if(vs.getWeightNd() && vs.getWeightU() == null){
            testInputWeightUnit();
        }else if(vs.getWeight() < 44.0 && vs.getWeightU() == WeightU.POUNDS || vs.getWeight() > 1435.0 && vs.getWeightU() == WeightU.POUNDS){
            this.messageErrorWeightPounds = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorWeightPounds = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the DBP and SBP in front end
     * @return messageErrorSbpGtDbp hidden or not and button create/update deactivate or not
     */
    public String testDbpGtnSbp(){
        String redirect = "null";
        if(vs.getSbp()<vs.getDbp()){
            this.messageErrorSbpGtDbp = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorSbpGtDbp = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the DBP > 10 in front end
     * @return messageErrorDbp hidden or not and button create/update deactivate or not
     */
    public String testDbp(){
        String redirect = "null";
        if(vs.getDbp()<10){
            this.messageErrorDbp = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorDbp = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the SBP < 250 in front end
     * @return messageErrorSbp hidden or not and button create/update deactivate or not
     */
    public String testSbp(){
        String redirect = "null";
        if(vs.getSbp()>250){
            this.messageErrorSbp = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorSbp = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the HR < 10 or HR > 240 in front end
     * @return messageErrorHr hidden or not and button create/update deactivate or not
     */
    public String testHr(){
        String redirect = "null";
        if(vs.getHr()<10 || vs.getHr()>240){
            this.messageErrorHr = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorHr = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the RR < 5 in front end
     * @return messageErrorRr hidden or not and button create/update deactivate or not
     */
    public String testRr(){
        String redirect = "null";
        if(vs.getRr()<5){
            this.messageErrorRr = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorRr = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputTemp in front end
     * @return messageErrorTempNull hidden or not and button create/update deactivate or not
     */
    public String testInputTemp(){
        String redirect = "null";
        if(vs.getTempNd() && (vs.getTemp() == null || vs.getTemp() == 0)){
            this.messageErrorTempNull = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTempNull = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputTempUnit in front end
     * @return messageErrorTempUnitNull hidden or not and button create/update deactivate or not
     */
    public String testInputTempUnit(){
        String redirect = "null";
        if(vs.getTempNd() && vs.getTempU() == null){
            this.messageErrorTempUnitNull = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTempUnitNull = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Temperature and °C in front end
     * @return messageErrorTempC hidden or not and button create/update deactivate or not
     */
    public String testRangeTempC(){
        String redirect = "null";
        if(vs.getTempNd() && (vs.getTemp() == null || vs.getTemp() == 0)){
            testInputTemp();
        }else if(vs.getTempNd() && vs.getTempU() == null){
            testInputTempUnit();
        }if(vs.getTemp() < 30 && vs.getTempU() == TempU.C || vs.getTemp() > 45 && vs.getTempU() == TempU.C){
            this.messageErrorTempC = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTempC = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Temperature and °F in front end
     * @return messageErrorTempF hidden or not and button create/update deactivate or not
     */
    public String testRangeTempF(){
        String redirect = "null";
        if(vs.getTempNd() && (vs.getTemp() == null || vs.getTemp() == 0)){
            testInputTemp();
        }else if(vs.getTempNd() && vs.getTempU() == null){
            testInputTempUnit();
        }if(vs.getTemp() < 86 && vs.getTempU() == TempU.F || vs.getTemp() > 113 && vs.getTempU() == TempU.F){
            this.messageErrorTempF = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTempF = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the InputTempUnit in front end
     * @return messageErrorTempUnitNull hidden or not and button create/update deactivate or not
     */
    public String testInputTempRoute(){
        String redirect = "null";
        if(vs.getTempNd() && vs.getTempRoute() == null){
            this.messageErrorTempRoute = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorTempRoute = "hidden";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "false";
        }
        return redirect;
    }

    /**
     * Method to test the Oxy < 50 in front end
     * @return messageErrorOxy hidden or not and button create/update deactivate or not
     */
    public String testOxy(){
        String redirect = "null";
        if(vs.getOxysat()<50 || vs.getOxysat()> 100){
            this.messageErrorOxy = "";
            this.messageErrorPerf = "hidden";
            this.buttonSuccess = "true";
        }else{
            this.messageErrorOxy = "hidden";
            this.messageErrorPerf = "hidden";
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
     * Method to find a VS based on the IdEvent
     * @param idEvent
     */
    public String findEvent(int idEvent){
        String redirect = "/VIEW/updateVs";
        EntityManager em = EMF.getEM();
        try{
            vs = vsService.findVsByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
    }

    /**
     * Method to find a VS based on the IdEvent
     * @param idEvent
     */
    public String findEventQuery(int idEvent){
        String redirect = "/VIEW/consultQueryVs";
        EntityManager em = EMF.getEM();
        try{
            vs = vsService.findVsByIdEvent(idEvent,em);
        }catch(Exception e){
            ProcessUtils.debug(e.getMessage());
        }finally {
            em.close();
        }
        return redirect;
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
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(vs.getVsYn() && vs.getVsDate() != null){
            String vsDate = simpleDateFormat.format(vs.getVsDate());
            int resultVsDate = vsDate.compareTo(String.valueOf(now));
            if(resultVsDate > 0){
                initErrorMessageFormVS();
                this.messageErrorVisitDate = "";
                redirect = "null";
                return redirect;
            }else if(!vs.getVsYn() && Objects.equals(vs.getVsNd(), "")){
                initErrorMessageFormVS();
                this.messageErrorVisitNd = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && (vs.getHeight() == null || vs.getHeight() == 0)) {
                initErrorMessageFormVS();
                this.messageErrorHeightNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && vs.getHeightU() == null){
                initErrorMessageFormVS();
                this.messageErrorHeightUnitNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && vs.getHeight() < 40.0 && vs.getHeightU() == HeightU.CM || vs.getHeightNd() && vs.getHeight() > 280.0 && vs.getHeightU() == HeightU.CM){
                initErrorMessageFormVS();
                this.messageErrorHeightCm = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && vs.getHeight() < 15.7 && vs.getHeightU() == HeightU.INCHES || vs.getHeightNd() && vs.getHeight() > 110.2 && vs.getHeightU() == HeightU.INCHES){
                initErrorMessageFormVS();
                this.messageErrorHeightInches = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && (vs.getWeight() == null || vs.getWeight() == 0)) {
                initErrorMessageFormVS();
                this.messageErrorWeightNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && vs.getWeightU() == null){
                initErrorMessageFormVS();
                this.messageErrorWeightUnitNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && vs.getWeight() < 20.0 && vs.getWeightU() == WeightU.KG || vs.getWeightNd() && vs.getWeight() > 650.0 && vs.getWeightU() == WeightU.KG){
                initErrorMessageFormVS();
                this.messageErrorWeightKg = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && vs.getWeight() < 44.0 && vs.getWeightU() == WeightU.POUNDS || vs.getWeightNd() && vs.getWeight() > 1435.0 && vs.getWeightU() == WeightU.POUNDS){
                initErrorMessageFormVS();
                this.messageErrorWeightPounds = "";
                redirect = "null";
                return redirect;
            }else if(vs.getBpNd() && vs.getDbp()>vs.getSbp()){
                initErrorMessageFormVS();
                this.messageErrorSbpGtDbp = "";
                redirect = "null";
                return redirect;
            }else if(vs.getBpNd() && vs.getDbp()<10){
                initErrorMessageFormVS();
                this.messageErrorDbp = "";
                redirect = "null";
                return redirect;
            }else if(vs.getBpNd() && vs.getSbp()>250){
                initErrorMessageFormVS();
                this.messageErrorSbp = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHrNd() && vs.getHr()<10 || vs.getHrNd() && vs.getHr()>240){
                initErrorMessageFormVS();
                this.messageErrorHr = "";
                redirect = "null";
                return redirect;
            }else if(vs.getRrNd() && vs.getRr()<5){
                initErrorMessageFormVS();
                this.messageErrorRr = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && (vs.getTemp() == null || vs.getTemp() == 0)) {
                initErrorMessageFormVS();
                this.messageErrorTempNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTempU() == null){
                initErrorMessageFormVS();
                this.messageErrorTempUnitNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTemp() < 30 && vs.getTempU() == TempU.C || vs.getTempNd() && vs.getTemp() > 45 && vs.getTempU() == TempU.C){
                initErrorMessageFormVS();
                this.messageErrorTempC = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTemp() < 86 && vs.getTempU() == TempU.F || vs.getTempNd() && vs.getTemp() > 113 && vs.getTempU() == TempU.F){
                initErrorMessageFormVS();
                this.messageErrorTempF = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTempRoute() == null){
                initErrorMessageFormVS();
                this.messageErrorTempRoute = "";
                redirect = "null";
                return redirect;
            }else if(vs.getOxysatNd() && (vs.getOxysat()<50 || vs.getOxysat()> 100)) {
                initErrorMessageFormVS();
                this.messageErrorOxy = "";
                redirect = "null";
                return redirect;
            }else if(vs.isVsYn() && !vs.isHeightNd() && !vs.isWeightNd() && !vs.isBpNd() && !vs.isHrNd() && !vs.isRrNd() && !vs.isTempNd() && !vs.isOxysatNd()){
                initErrorMessageFormVS();
                this.messageErrorPerf = "";
                redirect = "null";
                return redirect;
            }else{
                try{
                    vs.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);

                    if(this.vs.isVsYn()){
                        this.vs.setVsNd("");
                    }

                    if(!this.vs.isVsYn()){
                        this.vs.setVsDate(null);
                        this.vs.setHeightNd(false);
                        this.vs.setWeightNd(false);
                        this.vs.setBpNd(false);
                        this.vs.setHrNd(false);
                        this.vs.setRrNd(false);
                        this.vs.setOxysatNd(false);
                    }

                    if(!this.vs.getHeightNd()){
                        this.vs.setHeight(null);
                        this.vs.setHeightU(HeightU.CM);
                    }
                    if(!this.vs.getWeightNd()){
                        this.vs.setWeight(null);
                        this.vs.setWeightU(WeightU.KG);
                    }
                    if(!this.vs.getBpNd()){
                        this.vs.setSbp(0);
                        this.vs.setDbp(0);
                    }
                    if(!this.vs.getHrNd()){
                        this.vs.setHr(0);
                    }
                    if(!this.vs.getRrNd()){
                        this.vs.setRr(0);
                    }
                    if(!this.vs.getTempNd()){
                        this.vs.setTemp(null);
                        this.vs.setTempU(TempU.C);
                        this.vs.setTempRoute(TempRoute.UNKNOWN);
                    }
                    if(!this.vs.getOxysatNd()){
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
        }else{
        if(!vs.getVsYn() && Objects.equals(vs.getVsNd(), "")){
            initErrorMessageFormVS();
            this.messageErrorVisitNd = "";
            redirect = "null";
        }else if(vs.getVsYn() && vs.getVsDate() == null){
            initErrorMessageFormVS();
            this.messageErrorVsDateMissing = "";
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
                    this.vs.setHeightU(null);
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
     * Method to update a VS in the DB
     * @return a VS
     */
    public String submitFormUpdateVs(){
        EntityManager em = EMF.getEM();
        String redirect = "/VIEW/home";
        EntityTransaction transaction = em.getTransaction();
        VsService vsService = new VsService();
        EventService eventService = new EventService();
        AuditTrailService auditTrailService = new AuditTrailService();
        LocalDate now = LocalDate.now();
        String isoDatePattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(isoDatePattern);
        if(vs.getVsYn() && vs.getVsDate() != null){
            String vsDate = simpleDateFormat.format(vs.getVsDate());
            int resultVsDate = vsDate.compareTo(String.valueOf(now));
            if(resultVsDate > 0){
                initErrorMessageFormVS();
                this.messageErrorVisitDate = "";
                redirect = "null";
                return redirect;
            }else if(!vs.getVsYn() && Objects.equals(vs.getVsNd(), "")){
                initErrorMessageFormVS();
                this.messageErrorVisitNd = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && (vs.getHeight() == null || vs.getHeight() == 0)) {
                initErrorMessageFormVS();
                this.messageErrorHeightNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && vs.getHeightU() == null){
                initErrorMessageFormVS();
                this.messageErrorHeightUnitNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && vs.getHeight() < 40.0 && vs.getHeightU() == HeightU.CM || vs.getHeightNd() && vs.getHeight() > 280.0 && vs.getHeightU() == HeightU.CM){
                initErrorMessageFormVS();
                this.messageErrorHeightCm = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHeightNd() && vs.getHeight() < 15.7 && vs.getHeightU() == HeightU.INCHES || vs.getHeightNd() && vs.getHeight() > 110.2 && vs.getHeightU() == HeightU.INCHES){
                initErrorMessageFormVS();
                this.messageErrorHeightInches = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && (vs.getWeight() == null || vs.getWeight() == 0)) {
                initErrorMessageFormVS();
                this.messageErrorWeightNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && vs.getWeightU() == null){
                initErrorMessageFormVS();
                this.messageErrorWeightUnitNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && vs.getWeight() < 20.0 && vs.getWeightU() == WeightU.KG || vs.getWeightNd() && vs.getWeight() > 650.0 && vs.getWeightU() == WeightU.KG){
                initErrorMessageFormVS();
                this.messageErrorWeightKg = "";
                redirect = "null";
                return redirect;
            }else if(vs.getWeightNd() && vs.getWeight() < 44.0 && vs.getWeightU() == WeightU.POUNDS || vs.getWeightNd() && vs.getWeight() > 1435.0 && vs.getWeightU() == WeightU.POUNDS){
                initErrorMessageFormVS();
                this.messageErrorWeightPounds = "";
                redirect = "null";
                return redirect;
            }else if(vs.getBpNd() && vs.getDbp()>vs.getSbp()){
                initErrorMessageFormVS();
                this.messageErrorSbpGtDbp = "";
                redirect = "null";
                return redirect;
            }else if(vs.getBpNd() && vs.getDbp()<10){
                initErrorMessageFormVS();
                this.messageErrorDbp = "";
                redirect = "null";
                return redirect;
            }else if(vs.getBpNd() && vs.getSbp()>250){
                initErrorMessageFormVS();
                this.messageErrorSbp = "";
                redirect = "null";
                return redirect;
            }else if(vs.getHrNd() && vs.getHr()<10 || vs.getHrNd() && vs.getHr()>240){
                initErrorMessageFormVS();
                this.messageErrorHr = "";
                redirect = "null";
                return redirect;
            }else if(vs.getRrNd() && vs.getRr()<5){
                initErrorMessageFormVS();
                this.messageErrorRr = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && (vs.getTemp() == null || vs.getTemp() == 0)) {
                initErrorMessageFormVS();
                this.messageErrorTempNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTempU() == null){
                initErrorMessageFormVS();
                this.messageErrorTempUnitNull = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTemp() < 30 && vs.getTempU() == TempU.C || vs.getTempNd() && vs.getTemp() > 45 && vs.getTempU() == TempU.C){
                initErrorMessageFormVS();
                this.messageErrorTempC = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTemp() < 86 && vs.getTempU() == TempU.F || vs.getTempNd() && vs.getTemp() > 113 && vs.getTempU() == TempU.F){
                initErrorMessageFormVS();
                this.messageErrorTempF = "";
                redirect = "null";
                return redirect;
            }else if(vs.getTempNd() && vs.getTempRoute() == null){
                initErrorMessageFormVS();
                this.messageErrorTempRoute = "";
                redirect = "null";
                return redirect;
            }else if(vs.getOxysatNd() && (vs.getOxysat()<50 || vs.getOxysat()> 100)) {
                initErrorMessageFormVS();
                this.messageErrorOxy = "";
                redirect = "null";
                return redirect;
            }else if(vs.isVsYn() && !vs.isHeightNd() && !vs.isWeightNd() && !vs.isBpNd() && !vs.isHrNd() && !vs.isRrNd() && !vs.isTempNd() && !vs.isOxysatNd()){
                initErrorMessageFormVS();
                this.messageErrorPerf = "";
                redirect = "null";
                return redirect;
            }else{
                try{
                    vs.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    eventBean.getEvent().setMonitored(false);

                    if(this.vs.isVsYn()){
                        this.vs.setVsNd("");
                    }

                    if(!this.vs.isVsYn()){
                        this.vs.setVsDate(null);
                        this.vs.setHeightNd(false);
                        this.vs.setWeightNd(false);
                        this.vs.setBpNd(false);
                        this.vs.setHrNd(false);
                        this.vs.setRrNd(false);
                        this.vs.setOxysatNd(false);
                    }

                    if(!this.vs.getHeightNd()){
                        this.vs.setHeight(null);
                        this.vs.setHeightU(HeightU.CM);
                    }
                    if(!this.vs.getWeightNd()){
                        this.vs.setWeight(null);
                        this.vs.setWeightU(WeightU.KG);
                    }
                    if(!this.vs.getBpNd()){
                        this.vs.setSbp(0);
                        this.vs.setDbp(0);
                    }
                    if(!this.vs.getHrNd()){
                        this.vs.setHr(0);
                    }
                    if(!this.vs.getRrNd()){
                        this.vs.setRr(0);
                    }
                    if(!this.vs.getTempNd()){
                        this.vs.setTemp(null);
                        this.vs.setTempU(TempU.C);
                        this.vs.setTempRoute(TempRoute.UNKNOWN);
                    }
                    if(!this.vs.getOxysatNd()){
                        this.vs.setOxysat(0);
                    }
                    transaction.begin();
                    eventService.updateEvent(eventBean.getEvent(),em);
                    auditTrailService.addAuditTrail(auditTrailBean.getAuditTrail(),em);
                    vsService.updateVs(vs,em);
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
                this.messageErrorVsDateMissing = "";
                redirect = "null";
            }else{
                try{
                    vs.setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setUserByIdUser(connectionBean.getUser());
                    auditTrailBean.getAuditTrail().setEventByIdEvent(eventBean.getEvent());
                    auditTrailBean.getAuditTrail().setAuditTrailDatetime(new Date());
                    eventBean.getEvent().setCompleted(true);
                    eventBean.getEvent().setMonitored(false);

                    if(!vs.getVsYn()){;
                        this.vs.setVsDate(null);
                        this.vs.setHeightNd(false);
                        this.vs.setHeight(0.0);
                        this.vs.setHeightU(null);
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

    public String getMessageErrorVsDateMissing() {
        return messageErrorVsDateMissing;
    }

    public void setMessageErrorVsDateMissing(String messageErrorVsDateMissing) {
        this.messageErrorVsDateMissing = messageErrorVsDateMissing;
    }

    public String getMessageErrorHeightCm() {
        return messageErrorHeightCm;
    }

    public void setMessageErrorHeightCm(String messageErrorHeightCm) {
        this.messageErrorHeightCm = messageErrorHeightCm;
    }

    public String getMessageErrorHeightInches() {
        return messageErrorHeightInches;
    }

    public void setMessageErrorHeightInches(String messageErrorHeightInches) {
        this.messageErrorHeightInches = messageErrorHeightInches;
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

    public String getButtonSuccess() {
        return buttonSuccess;
    }

    public void setButtonSuccess(String buttonSuccess) {
        this.buttonSuccess = buttonSuccess;
    }

    public String getMessageErrorHeightNull() {
        return messageErrorHeightNull;
    }

    public void setMessageErrorHeightNull(String messageErrorHeightNull) {
        this.messageErrorHeightNull = messageErrorHeightNull;
    }

    public String getMessageErrorHeightUnitNull() {
        return messageErrorHeightUnitNull;
    }

    public void setMessageErrorHeightUnitNull(String messageErrorHeightUnitNull) {
        this.messageErrorHeightUnitNull = messageErrorHeightUnitNull;
    }

    public String getMessageErrorWeightNull() {
        return messageErrorWeightNull;
    }

    public void setMessageErrorWeightNull(String messageErrorWeightNull) {
        this.messageErrorWeightNull = messageErrorWeightNull;
    }

    public String getMessageErrorWeightUnitNull() {
        return messageErrorWeightUnitNull;
    }

    public void setMessageErrorWeightUnitNull(String messageErrorWeightUnitNull) {
        this.messageErrorWeightUnitNull = messageErrorWeightUnitNull;
    }

    public String getMessageErrorTempNull() {
        return messageErrorTempNull;
    }

    public void setMessageErrorTempNull(String messageErrorTempNull) {
        this.messageErrorTempNull = messageErrorTempNull;
    }

    public String getMessageErrorTempUnitNull() {
        return messageErrorTempUnitNull;
    }

    public void setMessageErrorTempUnitNull(String messageErrorTempUnitNull) {
        this.messageErrorTempUnitNull = messageErrorTempUnitNull;
    }

    public String getMessageErrorTempRoute() {
        return messageErrorTempRoute;
    }

    public void setMessageErrorTempRoute(String messageErrorTempRoute) {
        this.messageErrorTempRoute = messageErrorTempRoute;
    }

    public String getMessageErrorPerf() {
        return messageErrorPerf;
    }

    public void setMessageErrorPerf(String messageErrorPerf) {
        this.messageErrorPerf = messageErrorPerf;
    }
}
