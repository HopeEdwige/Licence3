
public interface Iterator <T> 
{	
    public void goLeft();
    public void goRight();
    public void goUp();
    public void goRoot();
    public boolean isEmpty();
    public NodeType nodeType();
    public void remove();
    public void clear();
    public T getValue();
    public void addValue(T v);
    public void setValue(T v);
    public void switchValue(int i);
}