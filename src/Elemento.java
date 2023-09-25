import java.awt.*;
public class Elemento {
    int x;
    int y;
    Color color;
    int size;
    public Elemento(int x, int y, Color color, int size)    {
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    public void draw(Graphics g){
        g.setColor(color);
        g.fill3DRect(x*size, y*size, size, size,true);
    }

    public boolean colision(Elemento otro){
        return (x == otro.x) && (y == otro.y); 
    }

    public void moverA(Elemento otro){
        x = otro.x;
        y = otro.y;
    }
}
