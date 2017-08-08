package org.toolfactory.android.db;

/**
 * @since 06 April 2016
 * @author Arjun Prasad
 */
public class Constraint {

    public static final String CONSTRAINT_TYPE_PRIMARY="PRIMARY KEY";
    public static final String CONSTRAINT_TYPE_FOREIGN="FOREIGN KEY";
    public static final String CONSTRAINT_TYPE_AUTOINCREMENT="AUTOINCREMENT";
    public static final String CONSTRAINT_TYPE_NOT_NULL="NOT_NULL";
    public static final String CONSTRAINT_TYPE_DEFAULT="DEFAULT";

    private String onField="";
    private String type="";
    private String extra="";
    private String referringTable="";
    private String toField="";

    private StringBuilder builder;

    private Constraint(){
        builder=new StringBuilder();
    }

    public static Constraint onField(String onField){
        Constraint constraint=new Constraint();
        constraint.onField=onField;
        return constraint;
    }

    public Constraint type(String type){
        this.type=type;
        return this;
    }

    public Constraint extra(String extra){
        this.extra=extra;
        return this;
    }

    public Constraint references(String referringTable){
        this.referringTable=referringTable;
        return this;
    }

    public Constraint toField(String toField){
        this.toField=toField;
        return this;
    }

    public String sql(){
        return builder.toString();
    }

    public Constraint create(){
        builder.append(" ");
        builder.append(type);
        if(!extra.isEmpty()){
            builder.append(" ");
            builder.append(extra);
        }
        if(!referringTable.isEmpty()){
            builder.append(" ( ");
            builder.append(onField);
            builder.append(" ) ");
            builder.append(referringTable);
        }
        if(!toField.isEmpty()){
            builder.append(" ( ");
            builder.append(toField);
            builder.append(" ) ");
        }
        builder.append(" ");
        return this;
    }

    public String getOnField() {
        return onField;
    }

    public String getType() {
        return type;
    }

    public String getExtra() {
        return extra;
    }

    public String getReferringTable() {
        return referringTable;
    }

    public String getToField() {
        return toField;
    }
}
