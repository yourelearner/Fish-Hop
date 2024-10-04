import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class GamePanel extends JPanel implements Runnable {
	

    private Game game;
    
    
    public GamePanel() {
        game = new Game();
        new Thread(this).start();
    }

    public void update() {
        game.update();
        repaint();
    }

   public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2D = (Graphics2D) g;
        
        
        for (Render r : game.getRenders())
            if (r.transform != null)
                g2D.drawImage(r.image, r.transform, null);
        
            else
                g.drawImage(r.image, r.x, r.y, null);
        

        g2D.setColor(Color.BLACK);

        if (!game.started) {
             
        	g2D.setFont(new Font("Impact", Font.PLAIN, 15));
        	g2D.drawString("Credits to:" , 200, 155);
        	
        	g2D.setFont(new Font("Impact", Font.PLAIN, 15));
        	g2D.drawString("Leonard Gatpolintan" , 170, 180);
        	g2D.drawString("Angelie Larioque" , 183, 200);
        	g2D.drawString("Sebastian Raule" , 185, 220);
        	g2D.drawString("Bryan Bergonia" , 188, 240);
        	
        	g2D.setColor(Color.YELLOW);

        	
        	g2D.setFont(new Font("Impact", Font.BOLD, 70));
        	g2D.drawString("FISH HOP", 120, 105);
        	
            g2D.setFont(new Font("Impact", Font.PLAIN, 25));
            g2D.drawString("PRESS  SPACE  TO  START", 133, 300);
        } else {
            g2D.setFont(new Font("Impact", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(game.score), 10, 465);
        }

        g2D.setColor(Color.YELLOW);
        
        if (game.gameover) {
        	g2D.setFont(new Font("Impact", Font.BOLD, 55));
        	g2D.drawString("GAME OVER", 120, 225);
            g2D.setFont(new Font("Impact", Font.PLAIN, 20));
            g2D.drawString("FISH DIED, PRESS R TO RESTART", 127, 257);
        }
        
        g2D.setColor(Color.BLACK);

        if (!game.started) {
        	g2D.setFont(new Font("Impact", Font.BOLD, 70));
        	g2D.drawString("FISH HOP", 120, 110);
            g2D.setFont(new Font("Impact", Font.PLAIN, 25));
            g2D.drawString("PRESS  SPACE  TO  START", 133, 303);
        } else {
            g2D.setFont(new Font("Impact", Font.PLAIN, 24));
            g2D.drawString(Integer.toString(game.score), 10, 465);
        }
        
        if (game.gameover) {
        	g2D.setFont(new Font("Impact", Font.BOLD, 55));
        	g2D.drawString("GAME OVER", 120, 230);
            g2D.setFont(new Font("Impact", Font.PLAIN, 20));
            g2D.drawString("FISH DIED, PRESS R TO RESTART", 127, 260);
        }
    }

    public void run() {
        try {
            while (true) {
                update();
                Thread.sleep(25);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
