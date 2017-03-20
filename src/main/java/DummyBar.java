/**
 * Created by kai-tait on 20/03/2017.
 */
public class DummyBar {
    private Double someDouble;
    private String innerString;
    
    public DummyBar(String innerString, Double someDouble){
        this.someDouble = someDouble;
        this.innerString = innerString;
    }
    
    public DummyBar(Double someDouble, String innerString){
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
