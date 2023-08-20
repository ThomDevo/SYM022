package com.sym022.sym022.converterCustom;

import com.sym022.sym022.entities.FormEntity;
import com.sym022.sym022.services.FormService;
import com.sym022.sym022.utilities.EMF;
import com.sym022.sym022.utilities.ProcessUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.EntityManager;

@FacesConverter("FormConverter")
public class FormConverter implements Converter {
    /**
     * <p><span class="changed_modified_2_3">Convert</span> the specified string value, which is associated with
     * the specified {@link UIComponent}, into a model data object that
     * is appropriate for being stored during the <em class="changed_modified_2_3">Process Validations</em>
     * phase of the request processing lifecycle.</p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object
     *                  value is associated
     * @param value     String value to be converted (may be <code>null</code>)
     * @return <code>null</code> if the value to convert is <code>null</code>,
     * otherwise the result of the conversion
     * @throws ConverterException   if conversion cannot be successfully
     *                              performed
     * @throws NullPointerException if <code>context</code> or
     *                              <code>component</code> is <code>null</code>
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.equals("0") || value.equals("")) {
            return null;
        }
        EntityManager em = EMF.getEM();
        FormService formService = new FormService();
        FormEntity form = null;
        try {
            form = formService.findFormById(Integer.parseInt(value), em);
        } catch (Exception e) {
            ProcessUtils.debug(""+e);
        } finally {
            em.close();
        }
        return form;
    }

    /**
     * <p>Convert the specified model object value, which is associated with
     * the specified {@link UIComponent}, into a String that is suitable
     * for being included in the response generated during the
     * <em>Render Response</em> phase of the request processing
     * lifeycle.</p>
     *
     * @param context   {@link FacesContext} for the request being processed
     * @param component {@link UIComponent} with which this model object
     *                  value is associated
     * @param value     Model object value to be converted
     *                  (may be <code>null</code>)
     * @return a zero-length String if value is <code>null</code>,
     * otherwise the result of the conversion
     * @throws ConverterException   if conversion cannot be successfully
     *                              performed
     * @throws NullPointerException if <code>context</code> or
     *                              <code>component</code> is <code>null</code>
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value == null){
            return "0";
        }
        FormEntity form = (FormEntity) value;
        return String.valueOf(form.getIdForm());
    }
}
