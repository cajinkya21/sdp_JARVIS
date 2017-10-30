package jarvis.jarvis;

public class Contact {

    //private variables
    int _id;
    String _name;
    String _phone_number;
    String _email_id;
    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, String name, String _phone_number, String _email_id){
        this._id = id;
        this._name = name;
        this._phone_number = _phone_number;
        this._email_id = _email_id;
    }

    // constructor
    public Contact(String name, String _phone_number, String _email_id){
        this._name = name;
        this._phone_number = _phone_number;
        this._email_id = _email_id;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this._name;
    }

    // setting name
    public void setName(String name){
        this._name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }

    // getting phone number
    public String getEmailId(){
        return this._email_id;
    }

    // setting phone number
    public void setEmailId(String email_id){
        this._email_id = email_id;
    }
}

class Account_Pass {
    //private variables
    int _id;
    String _accname;
    String _accpass;
    String _accuname;
    // Empty constructor
    public Account_Pass(){

    }
    // constructor
    public Account_Pass(int id, String accuname, String _accpass, String _accname){
        this._id = id;
        this._accuname = accuname;
        this._accpass = _accpass;
        this._accname = _accname;
    }

    // constructor
    public Account_Pass( String accuname, String _accpass, String _accname){
        this._accuname = accuname;
        this._accpass = _accpass;
        this._accname = _accname;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getUName(){
        return this._accuname;
    }

    // setting name
    public void setUName(String name){
        this._accuname = name;
    }

    // getting phone number
    public String getname(){
        return this._accname;
    }

    // setting phone number
    public void setname(String name){
        this._accname = name;
    }

    // getting phone number
    public String getpass(){
        return this._accpass;
    }

    // setting phone number
    public void setpassd(String upass){
        this._accpass = upass;
    }
}

class Study {
    private static final String KEY_ID_STUDY = "id";
    private static final String KEY_SUBJECT = "subName";
    private static final String KEY_TOPIC = "topicName";
    private static final String Key_DESCTIPTION = "subDesc";
    //private variables
    int _id;
    String _subName;
    String _topicName;
    String _subDesc;
    // Empty constructor
    public Study(){

    }
    // constructor
    public Study(int id, String _subName, String _topicName, String _subDesc){
        this._id = id;
        this._subName = _subName;
        this._topicName = _topicName;
        this._subDesc = _subDesc;
    }
    // constructor
    public Study(String _subName, String _topicName, String _subDesc){
        this._subName = _subName;
        this._topicName = _topicName;
        this._subDesc = _subDesc;
    }



    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getSubName(){
        return this._subName;
    }

    // setting name
    public void setSubName(String name){
        this._subName = name;
    }

    // getting phone number
    public String getTopic(){ return this._topicName; }

    // setting phone number
    public void setTopic(String name){
        this._topicName = name;
    }

    public void setDesc(String name){this._subDesc = name;}

    public String getDesc(){return this._subDesc;}

}

class Results{

    public boolean status;
    public String ans1, ans2, ans3;
    public String getString(int id){
        if(id == 1)
            return ans1;
        if(id == 2)
            return ans2;
        if(id == 3)
            return ans3;
        return "default";
    }
}
