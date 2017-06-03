
public interface Iterator <T> {
	public void goForward();
	public void goBackward();
	public void restart();
	public boolean isOnFlag();
	public void remove();
	public T getValue();
	public T nextValue();
	public void addLeft(T v);
	public void addRight(T v);
	public void setValue(T v);
	public String toString();
}
