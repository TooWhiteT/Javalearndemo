package Factory.AbstractFactory;

//具体工厂  联想工厂
public class LenovoFactory extends ComputerFactory{
    public DesktopComputer createDesktopComputer(String brand) {
        return new LenovoDesktopComputer(brand);
    }

    public NotebookComputer createNotebookComputer(String brand) {
        return new LenovoNotebookComputer(brand);
    }
}
