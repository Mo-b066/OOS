package A32;

class Human {
    private String name;
    private int age;
    Human(String name, int age){
        this.name = name;
        this.age = age;
    }
    String getName(){
        return name;
    }
    int getAge(){
        return age;
    }
    public String toString(){
        return "Human: "+name+ "(age: "+age+")";
    }
}