package be.vdab.training.domain;

/**
 * Created by wvdbrand on 6/09/2017.
 */
public class Department extends DatabaseEntity {
    private String departmentName;
    private String departmentAddress;
    private Manager departmentManager;

    private Department(DepartmentBuilder builder) {
        this.departmentName = builder.nestedDepartmentName;
        this.departmentAddress = builder.nestedDepartmentAddress;
        this.departmentManager = builder.nestedDepartmentManager;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentAddress() {
        return departmentAddress;
    }

    public void setDepartmentAddress(String departmentAddress) {
        this.departmentAddress = departmentAddress;
    }

    public static class DepartmentBuilder {
        private String nestedDepartmentName;
        private String nestedDepartmentAddress;
        private Manager nestedDepartmentManager;

        public DepartmentBuilder departmentName(String departmentName) {
            this.nestedDepartmentName = departmentName;
            return this;
        }

        public DepartmentBuilder departmentAddress(String nestedDepartmentAddress) {
            this.nestedDepartmentAddress = nestedDepartmentAddress;
            return this;
        }

        public DepartmentBuilder departmentManager(Manager nestedDepartmentManager) {
            this.nestedDepartmentManager = nestedDepartmentManager;
            return this;
        }

        public Department build() {
            return new Department(this);
        }
    }
}
