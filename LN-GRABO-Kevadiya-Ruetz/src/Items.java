import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Items {

	public SimpleStringProperty name;

	public SimpleDoubleProperty preis;
	public SimpleIntegerProperty anzahl;

	public String getName() {
		return this.name.get();
	}

	public double getPreis() {
		return this.preis.get();
	}

	public int getAnzahl() {
		return this.anzahl.get();
	}

	public void addItem() {
		this.anzahl.set(getAnzahl() + 1);

	}

	public void decItem() {
		if (this.anzahl.get() > 0) {
			this.anzahl.set(getAnzahl() - 1);
		}

	}

	public void clear() {
		this.anzahl.set(getAnzahl() - getAnzahl());
	}

	public Items(String name, double preis) {
		this.name = new SimpleStringProperty(name);
		this.preis = new SimpleDoubleProperty(preis);
		this.anzahl = new SimpleIntegerProperty(0);
	}

	public void printItem() {
		System.out.println(this.name + " = " + this.preis);
	}

}
