package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;

public class Mite extends Entity{
	public Mite(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale){
		super(model, position, rotX, rotY, rotZ, scale, false, "mite");
	}
	
	public void followPlayer(float[] playersPosition){
		if(this.position.x < playersPosition[0]){
			this.position.x = this.position.x+1;
		}else if(this.position.x > playersPosition[0]){
			this.position.x = this.position.x-1;
		}
		if(this.position.y < playersPosition[1]){
			this.position.y = this.position.y+1;
		}else if(this.position.x > playersPosition[1]){
			this.position.y = this.position.y-1;
		}
	}

}


/**
		for(int i=0; i<miteArrayPositions.size(); i++){ //LAST MESS   (MAKE SMOOTHER UPDATE WITH ADDING MULTIPLE CHECKS AT ONCE)
				if(miteModelSizes.get(i) == normalMapEntities.get(miteArrayPositions.get(i)).getMaxScale()){
					if(normalMapEntities.get(miteArrayPositions.get(i)).getPosition() != player.getPosition()){
						if(player.getX() > normalMapEntities.get(miteArrayPositions.get(i)).getX()+5){
							normalMapEntities.get(miteArrayPositions.get(i)).setPosition(new Vector3f(normalMapEntities.get(miteArrayPositions.get(i)).getX()+.3f, normalMapEntities.get(miteArrayPositions.get(i)).getY(), normalMapEntities.get(miteArrayPositions.get(i)).getZ()));
						}else if(player.getX() < normalMapEntities.get(miteArrayPositions.get(i)).getX()-5){
							normalMapEntities.get(miteArrayPositions.get(i)).setPosition(new Vector3f(normalMapEntities.get(miteArrayPositions.get(i)).getX()-.3f, normalMapEntities.get(miteArrayPositions.get(i)).getY(), normalMapEntities.get(miteArrayPositions.get(i)).getZ()));
						}
						if(player.getY() > normalMapEntities.get(miteArrayPositions.get(i)).getY()+5){
							normalMapEntities.get(miteArrayPositions.get(i)).setMaxY(normalMapEntities.get(miteArrayPositions.get(i)).getMaxY()+0.3);
							//normalMapEntities.get(miteArrayPositions.get(i)).setPosition(new Vector3f(normalMapEntities.get(miteArrayPositions.get(i)).getX(), normalMapEntities.get(miteArrayPositions.get(i)).getY()+.3f, normalMapEntities.get(miteArrayPositions.get(i)).getZ()));
						}else if(player.getY() < normalMapEntities.get(miteArrayPositions.get(i)).getY()-5){
							normalMapEntities.get(miteArrayPositions.get(i)).setMaxY(normalMapEntities.get(miteArrayPositions.get(i)).getMaxY()-0.3);
							//normalMapEntities.get(miteArrayPositions.get(i)).setPosition(new Vector3f(normalMapEntities.get(miteArrayPositions.get(i)).getX(), normalMapEntities.get(miteArrayPositions.get(i)).getY()-.3f, normalMapEntities.get(miteArrayPositions.get(i)).getZ()));
						}
						if(player.getZ() > normalMapEntities.get(miteArrayPositions.get(i)).getZ()+5){
							normalMapEntities.get(miteArrayPositions.get(i)).setPosition(new Vector3f(normalMapEntities.get(miteArrayPositions.get(i)).getX(), normalMapEntities.get(miteArrayPositions.get(i)).getY(), normalMapEntities.get(miteArrayPositions.get(i)).getZ()+.3f));
						}else if(player.getZ() < normalMapEntities.get(miteArrayPositions.get(i)).getZ()-5){
							normalMapEntities.get(miteArrayPositions.get(i)).setPosition(new Vector3f(normalMapEntities.get(miteArrayPositions.get(i)).getX(), normalMapEntities.get(miteArrayPositions.get(i)).getY(), normalMapEntities.get(miteArrayPositions.get(i)).getZ()-.3f));
						}
						miteBoxes.get(i).setBoxPos(normalMapEntities.get(miteArrayPositions.get(i)).getPosition());
					}
				}
				//ComeBack
				
*/

/**
 * 
 * 
 * 
 * 
 */
