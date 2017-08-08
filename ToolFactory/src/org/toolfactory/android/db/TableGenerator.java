package org.toolfactory.android.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @since 06 April 2016
 * @author Arjun Prasad
 */
public class TableGenerator {

    public static final String FIELD_TYPE_TEXT="TEXT";
    public static final String FIELD_TYPE_INTEGER="INTEGER";
    public static final String FIELD_TYPE_NUMERIC="NUMERIC";
    public static final String REAL="REAL";
    public static final String DATE="DATE";
    public static final String TIMESTAMP="TIMESTAMP";

    private SQLiteDatabase db;
    private String tableName;
    private List<String> fields;
    private List<String> types;
    private HashMap<String,List<Constraint>>mapOfConstraint,mapOfForeignKeys;

    private StringBuilder builder;

    private TableGenerator(){
        mapOfConstraint=new HashMap<String,List<Constraint>>();
        mapOfForeignKeys=new HashMap<String,List<Constraint>>();
        builder=new StringBuilder();
    }

    public static TableGenerator use(SQLiteDatabase db){
        TableGenerator generator=new TableGenerator();
        generator.db=db;
        return generator;
    }

    public TableGenerator name(String tableName){
        this.tableName=tableName;
        return this;
    }

    public TableGenerator fields(String[] fields){
        this.fields= Arrays.asList(fields);
        return this;
    }

    public TableGenerator types(String[] types){
        this.types=Arrays.asList(types);
        return this;
    }

    public TableGenerator addConstraint(Constraint constraint){
        if(constraint!=null){
            if(mapOfConstraint.containsKey(constraint.getOnField())){
                mapOfConstraint.get(constraint.getOnField()).add(constraint);
            }else{
                ArrayList<Constraint>list=new ArrayList<Constraint>();
                list.add(constraint);
                mapOfConstraint.put(constraint.getOnField(),list);
            }
        }
        return this;
    }

    public TableGenerator create() throws IllegalArgumentException{
        builder.append("CREATE TABLE");
        if(tableName==null)
            throw new IllegalArgumentException("Table Name can not be null");

        if(!tableName.isEmpty()){
            builder.append(" ");
            builder.append(tableName);
        }
        builder.append("( ");
        if(fields!=null && fields.size()>0 && types!=null && types.size()>0 && fields.size()==types.size()){
            for(int i=0;i<fields.size();i++){
                builder.append(" ");
                builder.append(fields.get(i));
                builder.append(" ");
                builder.append(types.get(i));

                if(mapOfConstraint.size()>0){
                    if(mapOfConstraint.containsKey(fields.get(i))){
                        for(Constraint constraint : mapOfConstraint.get(fields.get(i))){
                            if(!constraint.getType().equalsIgnoreCase(Constraint.CONSTRAINT_TYPE_FOREIGN)){
                                builder.append(" ");
                                builder.append(constraint.sql());
                            }else{
                                if(mapOfForeignKeys.containsKey(constraint.getOnField())){
                                    mapOfForeignKeys.get(constraint.getOnField()).add(constraint);
                                }else{
                                    ArrayList<Constraint>list=new ArrayList<Constraint>();
                                    list.add(constraint);
                                    mapOfForeignKeys.put(constraint.getOnField(),list);
                                }
                            }
                        }
                    }
                }
                if((i+1) != fields.size())
                    builder.append(",");
            }
            for(String fieldName : mapOfForeignKeys.keySet()){
                List<Constraint>constraintList=mapOfForeignKeys.get(fieldName);
                for(int i=0;i<constraintList.size();i++){
                    Constraint constraint=constraintList.get(i);
                    builder.append(" , ");
                    builder.append(constraint.sql());
                    builder.append(" ");
                }
            }
        }else{
            if(fields==null || fields.size()==0)
                throw new IllegalArgumentException("Can not create a Table with empty fields");
            if(types==null || types.size()==0)
                throw new IllegalArgumentException("Can not create a Table with empty data types");
            if(fields.size()!= types.size())
                throw new IllegalStateException("Can not create a Table with mismatched fields and data types");
        }
        builder.append(" )");
//        Final Statement
//        if(db!=null)
//            db.execSQL(builder.toString());
        return this;
    }

    public String getQuery(){
        return builder.toString();
    }


}
