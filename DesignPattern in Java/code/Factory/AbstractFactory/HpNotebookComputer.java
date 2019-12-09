package Factory.AbstractFactory;

//具体产品    惠普笔记本
public class HpNotebookComputer extends NotebookComputer {
    private String brand;

    public HpNotebookComputer(String brand) {
        this.brand = brand;
    }

    public void start() {
        System.out.println("惠普"+brand+"笔记本启动中。。。");
    }
}
