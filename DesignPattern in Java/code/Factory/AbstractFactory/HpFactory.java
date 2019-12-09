package Factory.AbstractFactory;

public class HpFactory extends ComputerFactory {
    public DesktopComputer createDesktopComputer(String brand) {
        return new HpDesktopComputer(brand);
    }

    public NotebookComputer createNotebookComputer(String brand) {
        return new HpNotebookComputer(brand);
    }
}
