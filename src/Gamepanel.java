import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Gamepanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH =600;
    static final int SCREEN_HEIGHT =600;
    static final int UNIT_SIZE=20;
    static final int GAME_UNITS=(SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY=75;
    final int x[]=new int[GAME_UNITS];
    final int y[]=new int[GAME_UNITS];

    int bodyparts =6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction='R';
    boolean running =false;
    Timer timer;
    Random random;

    Gamepanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();

    }
    public void startGame(){
    newApple();
    running=true;
    timer=new Timer(DELAY,this);
    timer.start();
    }
    public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
    }
    public void draw(Graphics g){
        if (running){
    g.setColor(Color.red);
    g.fillOval(appleX,appleY,UNIT_SIZE,UNIT_SIZE);

    for (int i=0;i<bodyparts;i++){
        if (i==0){
            g.setColor(Color.white);
            g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
        }
        else {
            g.setColor(Color.green);
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillRect(x[i],y[i],UNIT_SIZE,UNIT_SIZE);
        }
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free",Font.BOLD,25));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());
    }
    }else {
       gameover(g); }
    }

    public void newApple(){
    //generates new apple randomly
        appleX=random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY=random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }
    public void move(){
    for (int i=bodyparts;i>0;i--){
        x[i]=x[i-1];
        y[i]=y[i-1];
        }
    switch (direction){
        case 'U'://moves snake up
            y[0]=y[0]-UNIT_SIZE;
            break;
        case 'D'://moves snake down
            y[0]=y[0]+UNIT_SIZE;
            break;
        case 'L'://moves snake left
            x[0]=x[0]-UNIT_SIZE;
            break;
        case 'R'://moves snake right
            x[0]=x[0]+UNIT_SIZE;
            break;
        }
    }
    public void checkApple(){
    if ((x[0]==appleX) && (y[0]==appleY)){
        bodyparts++;
        applesEaten++;
        newApple();
        }
    }
    public void checkCollisions(){
        //checks if head collides with body
        for (int i=bodyparts;i>0;i--){
            if ((x[0] ==x[i]) && (y[0]==y[i])){
                running=false;
            }
        }
        //checks if head touches left boarder
        if(x[0]<0){
            running=false;
        }
        //checks if head touches right boarder
        if (x[0]>SCREEN_WIDTH){
            running=false;
        }
        //checks if head touches top boarder
        if (y[0]<0){
            running=false;
        }
        //checks if head touches bottom boarder
        if (y[0]>SCREEN_HEIGHT){
            running=false;
        }
        if (!running){
            timer.stop();
        }
    }
    public void gameover(Graphics g){
        //score text
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free",Font.BOLD,25));
        FontMetrics metrics1=getFontMetrics(g.getFont());
        g.drawString("Score:"+applesEaten,(SCREEN_WIDTH-metrics1.stringWidth("Score:"+applesEaten))/2,g.getFont().getSize());

    //Game over text
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free",Font.BOLD,75));
        FontMetrics metrics=getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH-metrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
           // super.keyPressed(e);
            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if (direction!='R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction!='L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction!='D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction!='U'){
                        direction='D';
                    }
                    break;
            }
        }
    }
}
