
package objects;

import No.Name.Game.v3.Game;
import texture.Texture;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author Janus
 */
public class Block extends Objekt{
     Texture tex = Game.getInstance();
     private int type;
    public Block(float x, float y, int type, ObjektId id){
        super(x, y, id);
        this.type = type;
    }

    @Override
    public void tick(LinkedList<Objekt> objekt) {
       
    }

    @Override
    public void render(Graphics g) {
      if(type == 0)
          g.drawImage(tex.spriteSheetImage[0], (int)x, (int)y, null);
       if(type == 1)
          g.drawImage(tex.spriteSheetImage[1], (int)x, (int)y, null);
    }
    @Override
    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, 32, 32);
    }
}

