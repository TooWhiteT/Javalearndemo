package Builder;

//指挥者类   用来指挥整体流程  先装什么再装什么
public class Direcror {
    Builder builder = null;

    public Direcror(Builder builder) {
        this.builder = builder;
    }
    public Computer CreateComputer(String cpu,String raw,String mainborad,String ssd,String hhd){
        //规定流程
        builder.builderCPU(cpu);
        builder.builderRAW(raw);
        builder.builderMainBorad(mainborad);
        builder.builderSSD(ssd);
        builder.builderHHD(hhd);

        return builder.create();
    }
}
class Test{
    public static void main(String[] args) {
        Builder mySelfBuilder = new MySelfBuilder();
        Direcror direcror = new Direcror(mySelfBuilder);
        //组装电脑
        direcror.CreateComputer("i9-7900K","金士顿 16G 双通道","技嘉Z390-x","三星 512G 固态","西部 500G 机械硬盘");
    }
}
