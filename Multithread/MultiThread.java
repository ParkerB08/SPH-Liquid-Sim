package Multithread;

import Render.Render;

public class MultiThread implements Runnable{

    @Override
    public void run(){
        Render.render();
    }
}
