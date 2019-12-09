package Builder;

//抽象Builder类
public abstract class Builder {
    public abstract Computer create();
    public abstract void builderCPU(String cpu);
    public abstract void builderRAW(String raw);
    public abstract void builderMainBorad(String mainborad);
    public abstract void builderSSD(String ssd);
    public abstract void builderHHD(String hhd);
}
