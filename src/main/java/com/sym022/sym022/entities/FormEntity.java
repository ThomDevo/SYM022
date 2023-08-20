package com.sym022.sym022.entities;

import com.sym022.sym022.enums.FormLabel;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@NamedQueries(value = {
        @NamedQuery(name = "Form.selectFormAll", query = "SELECT fo FROM FormEntity fo ORDER BY fo.formNum ASC"),
        @NamedQuery(name = "Form.selectFormById", query = "SELECT fo FROM FormEntity fo WHERE fo.idForm = :idForm"),
        @NamedQuery(name = "Form.selectFormByNum", query = "SELECT fo FROM FormEntity fo WHERE fo.formNum = :formNum"),
        @NamedQuery(name = "Form.selectFormByLabel", query = "SELECT fo FROM FormEntity fo WHERE fo.formLabel = :formLabel"),
        @NamedQuery(name = "Form.isFormNumExist", query = "SELECT COUNT(fo) FROM FormEntity fo WHERE fo.formNum = :formNum"),
        @NamedQuery(name = "Form.isFormLabelExist", query = "SELECT COUNT(fo) FROM FormEntity fo WHERE fo.formLabel = :formLabel")
})


@Entity
@Table(name = "form", schema = "sym022")
public class FormEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_form", nullable = false)
    private int idForm;

    @Basic
    @Range(min=1,max= 99)
    @NotNull
    @Column(name = "form_num", nullable = false)
    private int formNum;

    @Basic
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "form_label", nullable = false)
    private FormLabel formLabel;

    @OneToMany(mappedBy = "formByIdForm")
    private List<EventEntity> eventsByIdForm;

    /*--- Getters and Setters ---*/

    public int getIdForm() {
        return idForm;
    }

    public void setIdForm(int idForm) {
        this.idForm = idForm;
    }

    public int getFormNum() {
        return formNum;
    }

    public void setFormNum(int formNum) {
        this.formNum = formNum;
    }

    public FormLabel getFormLabel() {
        return formLabel;
    }

    public void setFormLabel(FormLabel formLabel) {
        this.formLabel = formLabel;
    }

    public List<EventEntity> getEventsByIdForm() {
        return eventsByIdForm;
    }

    public void setEventsByIdForm(List<EventEntity> eventsByIdForm) {
        this.eventsByIdForm = eventsByIdForm;
    }

    /*--- HashCode and Equal ---*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormEntity that = (FormEntity) o;
        return idForm == that.idForm;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idForm);
    }
}
