import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData3 extends MapData {
  public static final int TYPE_FIRE = 5;
  private boolean[][] blackOut;//黒塗り判定用配列

  MapData3(int x,int y){
    super(x,y);
    mapImages[TYPE_FIRE] = new Image(mapImageFiles[5]);

    putFire();
    setImageViews();
  }

  public void putFire(){

    for(int y=0;y<height;y++){
      for(int x=0;x<width;x++){
        if(getMap(x,y)==TYPE_WALL){
          maps[y][x]=TYPE_FIRE;
        }
      }
    }
  }


  @Override
  public void setBlackOut(int x,int y){}
  @Override
  public void fillBlack(){}


}
