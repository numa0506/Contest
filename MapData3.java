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
  @Override
  public void setMapImageFiles(){
    mapImageFiles[0] =   "png/space3/SPACE3.jpg";
    mapImageFiles[1] =   "png/space3/WALL3.jpg";
    mapImageFiles[2] =   "png/space3/ITEM1.jpg";   //　追加
    mapImageFiles[3] =   "png/space3/goal1.jpg";
    mapImageFiles[4] =   "png/black.png";//黒塗り画像
    mapImageFiles[5] =   "png/space3/WALL3.jpg";

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
