package be.vdab.training.domain;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by wvdbrand on 6/09/2017.
 */
public class DatabaseEntity implements Serializable{
//    private UUID id;
    private Integer id;
    private static UUID staticUUID = UUID.randomUUID();

    private static int intId;

    public DatabaseEntity() {
//        this.id = UUID.randomUUID();
//        this.id = staticUUID;

        this.id = intId++;
    }

//    public UUID getId() {
//        return id;
//    }
//
//    public void setId(UUID id) {
//        this.id = id;
//    }
//

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseEntity)) return false;

        DatabaseEntity databaseEntity = (DatabaseEntity) o;

        return id.equals(databaseEntity.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
