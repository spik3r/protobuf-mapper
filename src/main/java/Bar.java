
public class Bar {
    private Double someDouble = 123.456;
    private String innerString = "another String";
    
    public Bar(){}
    
    public Bar(Double someDouble, String innerString){
        this.someDouble = someDouble;
        this.innerString = innerString;
    }
    
    public Double getSomeDouble()
    {
        return someDouble;
    }
    
    public String getInnerString()
    {
        return innerString;
    }
}
