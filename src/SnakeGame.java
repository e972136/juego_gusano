import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;




public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    int boardWidth;
    int boardHeight;

    int elementoSize = 25;

    Elemento snakeHead; 
    Elemento food;
    Random random;
    Timer gameLoop;
    int velocityX;
    int velocityY;
    ArrayList<Elemento> snakeBody;
    boolean gameOver = false;



    SnakeGame(int boardWidth, int boardHeight){
        addKeyListener(this);
        setFocusable(true);
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;        
        setPreferredSize(new Dimension(this.boardWidth,this.boardHeight));
        setBackground(Color.GREEN);
        
        random = new Random();

        snakeHead = new Elemento(5, 5, Color.BLUE, elementoSize);
        snakeBody = new ArrayList<>();

        food = new Elemento(15, 15, Color.RED, elementoSize);
        placeFood();

        velocityX = 0;
        velocityY = 0;

        gameLoop = new Timer(500,this);
        gameLoop.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        drawGrid(g);

        food.draw(g);
        snakeHead.draw(g);        
        for(Elemento e:snakeBody){
            e.draw(g);
        }

        //draw score
        g.setFont(new Font("Arial",Font.BOLD, 16));
        if(gameOver){
            g.setColor(Color.red);
            g.drawString("Game over "+ String.valueOf(snakeBody.size()),elementoSize - 16, elementoSize);
        }else{
            g.drawString("Score "+ String.valueOf(snakeBody.size()),elementoSize - 16, elementoSize);
        }

    }

    public void drawGrid(Graphics g){
        for(int i=0;i<boardWidth/elementoSize;i++){
            //vertical
            g.drawLine(i*elementoSize,0,i*elementoSize,boardHeight);
            //horizontal
            g.drawLine(0,i*elementoSize,boardWidth,i*elementoSize);
        }
    }


    public void placeFood(){
        food.x = random.nextInt(boardWidth/elementoSize);
        food.y = random.nextInt(boardHeight/elementoSize);
    }


    public void move(){
        //valida comida
        if(snakeHead.colision(food)){
            snakeBody.add(new Elemento(food.x, food.y, Color.BLUE, elementoSize));
            placeFood();
        }

        for(int i=snakeBody.size()-1;i>=0;i--){
            if(i==0){
                snakeBody.get(i)
                    .moverA(snakeHead);
            }else{
                snakeBody.get(i)
                    .moverA(snakeBody.get(i-1));
            }
        }

        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //se come solita
        for(Elemento e:snakeBody){
            if(e.colision(snakeHead)){
                gameOver = true;
            }
        }

        //pega con pared
        if(snakeHead.x*elementoSize<0 ||
            snakeHead.y*elementoSize<0 ||
            snakeHead.x*elementoSize> boardWidth ||
            snakeHead.y*elementoSize> boardHeight
        ){
            gameOver = true;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            gameLoop.stop();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1)
        {
            velocityX = 0;
            velocityY = -1;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1)
        {
            velocityX = 0;
            velocityY = 1;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1)
        {
            velocityX = -1;
            velocityY = 0;
        }
        if(e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1)
        {
            velocityX = 1;
            velocityY = 0;
        }
        
    }


    @Override
    public void keyTyped(KeyEvent e) {       
   //     System.out.println("b" + e);
    }


    @Override
    public void keyReleased(KeyEvent e) {    
     //   System.out.println("c "+ e);    
    }

}
