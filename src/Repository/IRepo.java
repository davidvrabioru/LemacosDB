package Repository;

import java.util.ArrayList;

public interface IRepo {

    Boolean saveObject(Object o);
    Boolean deleteObject(int id);
    Boolean updateObject(int id, Object o);
    ArrayList<Object> getAll();
    Boolean existObject(int id);
    Object findobject(int id);

}
