import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
*@author Group1
*/
public class MapData2 extends MapData {
    public static final int TYPE_BLACK = 4;
    private boolean[][] blackOut;

    MapData2(int x,int y){
        super(x,y);
        blackOut = new boolean[y][x];
        mapImages[TYPE_BLACK] = new Image(mapImageFiles[TYPE_BLACK]);
        for(int i=0;i<NUMBER;i++){
            blackOut[itemList[i][1]][itemList[i][0]]=true;
        }
        blackOut[13][19]=true;
        setImageViews();
        MusicPlay.playSp2();
    }
    @Override
    public void setMapImageFiles(){
        mapImageFiles[0] =   "png/space2/SPACE2.jpg";
        mapImageFiles[1] =   "png/space2/WALL2.jpg";
        mapImageFiles[2] =   "png/space2/ITEM2.jpg";
        mapImageFiles[3] =   "png/space2/goal2.jpg";
        mapImageFiles[4] =   "png/space2/YUKI.jpg";
        mapImageFiles[5] =   "png/FIRE.png";

    }


    @Override
    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                try{
                    if(!(blackOut[y][x])){
                        mapImageViews[y][x] = new ImageView(mapImages[TYPE_BLACK]);
                        continue;
                    }
                    mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
                }catch(NullPointerException e){
                    continue;
                }
            }
        }
    }

    @Override
    public void setBlackOut(int x,int y){
        blackOut[y][x]=true;
    }

    /**
    *blackOut[y][x]に格納されているすべての値をfalseにする。
    */
    public void fillBlack(){
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                blackOut[y][x]=false;
            }
        }
    }


}
