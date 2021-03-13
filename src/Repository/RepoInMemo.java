package Repository;

import Domain.Agent;
import Domain.Client;

import java.util.ArrayList;

public class RepoInMemo<Public> implements IRepo {

    private ArrayList<Object> memo;


    public RepoInMemo()
    {
        this.memo = new ArrayList<Object>();
    }

    @SuppressWarnings("rawtypes")
    public Boolean saveObject(Object o){
        memo.add(o);
        return true;
    }

    private int find_object(int id)
    {
        for(int i = 0;i<memo.size();i++)
        {
            if(!(memo.get(i) instanceof Client))
                return -1;
            else if(((Client) memo.get(i)).getId() == id)
                return i;
        }

        return -1;
    }

    @SuppressWarnings("rawtypes")
    public Boolean deleteObject(int id){

        if(find_object(id)!=-1) {
            memo.remove(find_object(id));
            return true;
        }
        return false;
    }


    public Boolean updateObject(int id, Object o)
    {
        int index = find_object(id);
        if(index != -1)
        {
            memo.remove(index);
            memo.add(index, o);
            return true;
        }

        return false;
    }


    public ArrayList<Object> getAll(){

        return this.memo;
    }

    public Boolean existObject(int id)
    {
        int index = find_object(id);
        if(index != -1)
            return true;
        else return false;
    }
    public Object findobject(int id)
    {
        int index = find_object(id);
        if(id!=-1)
        {
            return memo.get(find_object(id));
        }
        else return null;

    }
}
