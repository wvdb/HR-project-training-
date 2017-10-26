package be.vdab.training.domain;

import java.util.Date;

/**
 * Created by wvdbrand on 6/09/2017.
 */
public class Project extends DatabaseEntity {
    private String name;
    private Date projectStartDate;
    private Date projectEndDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(Date projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public Date getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(Date projectEndDate) {
        this.projectEndDate = projectEndDate;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", projectStartDate=" + projectStartDate +
                ", projectEndDate=" + projectEndDate +
                '}';
    }
}
